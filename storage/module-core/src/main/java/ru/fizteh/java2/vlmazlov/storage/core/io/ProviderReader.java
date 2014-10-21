package ru.fizteh.java2.vlmazlov.storage.core.io;

import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTable;
import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTableProvider;
import ru.fizteh.java2.vlmazlov.storage.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProviderReader {
    private static final int FILES_QUANTITY = 16;
    private static final int DIRECTORIES_QUANTITY = 16;


    private static int getNum(File file) {
        String[] tokens = file.getName().split("\\.");
        return Integer.parseInt(tokens[0]);
    }

    private static <V> void addTablePart(GenericTable<V> tablePart, GenericTable<V> table) {

        for (Map.Entry<String, V> entry : tablePart) {
            table.put(entry.getKey(), entry.getValue());
        }
    }

    private static <V> void checkKeys(GenericTable<V> tablePart, File file, File directory)
     throws ValidityCheckFailedException {
        for (Map.Entry<String, V> entry : tablePart) {
            ValidityChecker.checkKeyStorageAffiliation(entry.getKey(), getNum(file), getNum(directory),
                    FILES_QUANTITY, DIRECTORIES_QUANTITY);
        }
    }

    public static <V, T extends GenericTable<V>> void loadFileForKey(String key, 
        File root, T table, GenericTableProvider<V, T> provider)
    throws CloneNotSupportedException {

        T tableForFile = (T) table.clone();
        String dirName = Math.abs(key.getBytes()[0]) % DIRECTORIES_QUANTITY + ".dir";
        String fileName = Math.abs(key.getBytes()[0]) / FILES_QUANTITY % FILES_QUANTITY + ".dat";

        File directory = new File(root, dirName);
        File file = new File(directory, fileName);

        if ((!directory.exists()) || (!file.exists())) {
            return;
        }

        try {
           TableReader.readTable(directory, file, tableForFile, provider);
        } catch (IOException | ValidityCheckFailedException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        tableForFile.pushChanges();
        addTablePart(tableForFile, table);
    }

    //validity is to be checked in read() method of the corresponding provider
    public static <V, T extends GenericTable<V>> void readMultiTable(File root, 
        T table, GenericTableProvider<V, T> provider)
            throws IOException, ValidityCheckFailedException, CloneNotSupportedException {
        ArrayList<ArrayList<T>> tableParts =
                new ArrayList<ArrayList<T>>(DIRECTORIES_QUANTITY);

        for (int i = 0; i < DIRECTORIES_QUANTITY; ++i) {
            tableParts.add(i, new ArrayList<T>(FILES_QUANTITY));
            for (int j = 0; j < FILES_QUANTITY; ++j) {
                tableParts.get(i).add(j, (T) table.clone());
            }
        }

        for (File directory : root.listFiles()) {
            //validity is already checked, therefore, the file is there for a reason; 
            //or I could delete it. Both options - not that nice.
            if (directory.isFile()) {
                continue;
            }

            ValidityChecker.checkMultiFileStorageDir(directory, DIRECTORIES_QUANTITY);

            for (File file : directory.listFiles()) {
                ValidityChecker.checkMultiFileStorageFile(file, FILES_QUANTITY);

                TableReader.readTable(directory, file, tableParts.get(getNum(directory)).get(getNum(file)), provider);

                /*
                Only pushed changes can be iterated over.
                The parts are created in writeMultiTable exclusively for each thread;
                therefore, synchronization is unnecessary.
                */
                tableParts.get(getNum(directory)).get(getNum(file)).pushChanges();

                checkKeys(tableParts.get(getNum(directory)).get(getNum(file)), file, directory);
            }
        }

        for (ArrayList<T> tablePart : tableParts) {
            for (int j = 0; j < tablePart.size(); ++j) {
                addTablePart(tablePart.get(j), table);
            }
        }
    } 

    public static <V, T extends GenericTable<V>> List<File> getTableDirList(GenericTableProvider<V, T> provider)
            throws ValidityCheckFailedException {
        ValidityChecker.checkMultiTableDataBaseRoot(provider.getRoot());

        File rootFile = new File(provider.getRoot());
        List<File> tableDirs = new ArrayList<File>();

        Collections.addAll(tableDirs, rootFile.listFiles());

        return tableDirs;
    }
}
