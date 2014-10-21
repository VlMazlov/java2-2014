package ru.fizteh.java2.vlmazlov.storage.core.io;

import ru.fizteh.java2.vlmazlov.storage.api.Storeable;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTable;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTableProvider;
import ru.fizteh.java2.vlmazlov.storage.utils.*;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@SuppressWarnings("ALL")
public class StoreableTableFileManager {

    private static final int FILES_QUANTITY = 16;
    private static final int DIRECTORIES_QUANTITY = 16;
    
    private static File getFileForKey(String key, StoreableTable table, StoreableTableProvider provider) {
        String dirName = Math.abs(key.getBytes()[0]) % DIRECTORIES_QUANTITY + ".dir";
        String fileName = Math.abs(key.getBytes()[0]) / FILES_QUANTITY % FILES_QUANTITY + ".dat";

        File tableDir = getTableDir(table, provider);
        File directory = new File(tableDir, dirName);

        return new File(directory, fileName);
    }

    private static File getTableDir(StoreableTable table, StoreableTableProvider provider) {
        return new File(provider.getRoot(), table.getName());
    }

    private static File getTableDir(String name, StoreableTableProvider provider) {
        return new File(provider.getRoot(), name);
    }

    private static int getDirNum(String key) {
        return Math.abs(key.getBytes()[0]) % DIRECTORIES_QUANTITY;
    }

    private static int getFileNum(String key) {
        return Math.abs(key.getBytes()[0]) / FILES_QUANTITY % FILES_QUANTITY;
    }

    private static void writeSizeFile(int size, File sizeFile) throws IOException {
        try(PrintWriter writer = new PrintWriter(sizeFile)) {
            writer.print(size);
        }
    }

    public static void writeSize(StoreableTable table, StoreableTableProvider provider) throws IOException {
        File tableDir = getTableDir(table, provider);
        File sizeFile = new File(tableDir, "size.tsv");

        sizeFile.createNewFile();

        writeSizeFile(table.size(), sizeFile);
    }

    public static void writeSignature(StoreableTable table, StoreableTableProvider provider) throws IOException {

        File tableDir = getTableDir(table, provider);
        File signatureFile = new File(tableDir, "signature.tsv");

        signatureFile.createNewFile();

        try (PrintWriter writer = new PrintWriter(signatureFile)) {
            for (int i = 0; i < table.getColumnsCount(); ++i) {
                Class<?> clazz = table.getColumnType(i);
                writer.print(TypeName.getNameByClass(clazz) + " ");
            }
        }
    }

    public static List<Class<?>> getTableSignature(String name, StoreableTableProvider provider)
    throws ValidityCheckFailedException, IOException {

        File tableDir = getTableDir(name, provider);
        
        ValidityChecker.checkMultiStoreableTableRoot(tableDir);

        File signatureFile = new File(tableDir, "signature.tsv");
        List<Class<?>> signature = new ArrayList<Class<?>>();

        try (Scanner scanner = new Scanner(signatureFile)) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                Class<?> columnType = TypeName.getClassByName(type.trim());

                if (columnType == null) {
                    throw new UnsupportedDataTypeException("Unsupported column type: " + type);
                }

                signature.add(columnType);
            }

            ValidityChecker.checkStoreableTableSignature(signature);

            return signature;
        }
    }

    private static int countTableSize(String name, StoreableTableProvider provider, StoreableTable table)
    throws IOException, ValidityCheckFailedException {

        File tableDir = getTableDir(name, provider);
        int size = 0;

        for (int i = 0; i < DIRECTORIES_QUANTITY; ++i) {
            for (int j = 0; j < FILES_QUANTITY; ++j) {
                File directory = new File(tableDir, i + ".dir");
                File file = new File(directory, j + ".dat");

                if (!file.exists()) {
                    continue;
                }

                StoreableTableFileReader reader = new StoreableTableFileReader(file, table, provider);

                String key = reader.nextKey();
                while (key != null) {
                    ++size;
                    key = reader.nextKey();
                }
            }
        }

        writeSizeFile(size, new File(tableDir, "size.tsv"));

        return size;
    }

    public static int getTableSize(String name, StoreableTableProvider provider, StoreableTable table)
    throws ValidityCheckFailedException, IOException {

        File tableDir = getTableDir(table, provider);  

        //ValidityChecker.checkMultiStoreableTableRoot(tableDir);

        File sizeFile = new File(tableDir, "size.tsv");
        
        if (!sizeFile.exists()) {
            return countTableSize(name, provider, table);
        }

        ValidityChecker.checkTableSize(sizeFile);

        try (Scanner scanner = new Scanner(sizeFile)) {
            return scanner.nextInt();
        }
    }

    public static Map<String, Storeable> readFileForKey(String key,
        StoreableTable table, StoreableTableProvider provider) 
    throws IOException, ValidityCheckFailedException {

        File fileForKey = getFileForKey(key, table, provider);

        StoreableTableFileReader reader = new StoreableTableFileReader(fileForKey, table, provider);

        return reader.getData();
    }

    public static void modifyMultipleFiles(Map<String, Storeable> changed, Set<String> deleted,
        StoreableTable table, StoreableTableProvider provider) throws IOException, ValidityCheckFailedException {

        Map<String, String>[][] changedInFile = new HashMap[DIRECTORIES_QUANTITY][FILES_QUANTITY];
        Set<String>[][] deletedInFile = new HashSet[DIRECTORIES_QUANTITY][FILES_QUANTITY];

        for (int i = 0; i < DIRECTORIES_QUANTITY; ++i) {
            for (int j = 0; j < FILES_QUANTITY; ++j) {
                changedInFile[i][j] = new HashMap();
                deletedInFile[i][j] = new HashSet();
            }
        }

        for (Map.Entry<String, Storeable> entry : changed.entrySet()) {
            String key = entry.getKey();

            changedInFile[getDirNum(key)][getFileNum(key)].put(key, provider.serialize(table, entry.getValue()));
        }   

        for (String key : deleted) {
            deletedInFile[getDirNum(key)][getFileNum(key)].add(key);
        }

        File tableDir = getTableDir(table, provider);

        for (int i = 0; i < DIRECTORIES_QUANTITY; ++i) {
            for (int j = 0; j < FILES_QUANTITY; ++j) {
                File directory = new File(tableDir, i + ".dir");
                File file = new File(directory, j + ".dat");

                modifySingleFile(directory, file, changedInFile[i][j], deletedInFile[i][j], table, provider);
            }
        }

        dumpGarbage(tableDir);
    }

    private static void dumpGarbage(File tableDir) {
        for (File directory : tableDir.listFiles()) {
            if (!directory.isDirectory()) {
                continue;
            }
            for (File file : directory.listFiles()) {
                if (file.length() == 0) {
                    file.delete();
                }
            }

            if (directory.listFiles().length == 0) {
                directory.delete();
            }
        }
    }

    private static void modifySingleFile(File directory, File file, Map<String, String> changed, Set<String> deleted,
        StoreableTable table, StoreableTableProvider provider)
    throws IOException, ValidityCheckFailedException {

        if ((changed.isEmpty()) && (deleted.isEmpty())) {
            return;
        }

        if (!directory.exists()) {
            directory.mkdir();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        StoreableTableFileReader reader = new StoreableTableFileReader(file, table, provider);
        StoreableTableFileWriter writer = new StoreableTableFileWriter(file);

        String currentKey;

        do {
            currentKey = reader.nextKey();
                
            if (changed.containsKey(currentKey)) {
                writer.writeKeyValue(currentKey, changed.get(currentKey));
                changed.remove(currentKey);
                continue;
            }

            if (deleted.contains(currentKey)) {
                continue;
            }

            if (currentKey != null) {
                writer.writeKeyValue(currentKey, reader.getCurrentSerializedValue());
            }

        } while (currentKey != null);

        for (Map.Entry<String, String> entry : changed.entrySet()) {
            writer.writeKeyValue(entry.getKey(), entry.getValue());
            //System.out.println(entry);
        }

        writer.flush();
    }
}
