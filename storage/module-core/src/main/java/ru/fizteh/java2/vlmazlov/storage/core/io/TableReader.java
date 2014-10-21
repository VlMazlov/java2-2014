package ru.fizteh.java2.vlmazlov.storage.core.io;

import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTable;
import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTableProvider;
import ru.fizteh.java2.vlmazlov.storage.utils.ValidityChecker;
import ru.fizteh.java2.vlmazlov.storage.utils.ValidityCheckFailedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;

public class TableReader {
    private static String readUTFString(RandomAccessFile dataBaseStorage, int readingPosition, int length) 
    throws IOException {
        byte[] bytes = new byte[length];

        dataBaseStorage.seek(readingPosition);
        dataBaseStorage.read(bytes);
        return new String(bytes, "UTF-8");
    }


    public static <V, T extends GenericTable<V>> void readTable(
        File root, File storage, T table, GenericTableProvider<V, T> provider)
            throws IOException, ValidityCheckFailedException {
        if (root == null) {
            throw new FileNotFoundException("Directory not specified");
        }

        ValidityChecker.checkTableRoot(root);

        if ((!storage.exists()) || (storage.length() == 0)) {
            return;
        }

       // RandomAccessFile dataBaseStorage = new RandomAccessFile(storage, "r");

        try (RandomAccessFile dataBaseStorage = new RandomAccessFile(storage, "r")) {
            String key = null;
            int readPosition = 0;
            int initialOffset = -1;
            int prevOffset = -1;

            do {

                dataBaseStorage.seek(readPosition);

                while (dataBaseStorage.getFilePointer() < dataBaseStorage.length()) {
                    if (dataBaseStorage.readByte() == '\0') {
                        break;
                    }
                }

                int keyLen = (int) dataBaseStorage.getFilePointer() - readPosition - 1;

                int curOffset = dataBaseStorage.readInt();

                ValidityChecker.checkTableOffset(curOffset);

                if (prevOffset == -1) {
                    initialOffset = curOffset;
                } else {
                    String value = readUTFString(dataBaseStorage, prevOffset, curOffset - prevOffset);

                    ValidityChecker.checkTableValue(value);

                    try {
                        table.put(key, provider.deserialize(table, value));
                    } catch (ParseException ex) {
                        throw new IOException(ex.getMessage());
                    }
                }
                prevOffset = curOffset;
                //read key      
                key = readUTFString(dataBaseStorage, readPosition, keyLen);
                ValidityChecker.checkTableKey(key);

                readPosition = (int) dataBaseStorage.getFilePointer() + 5;

            } while (readPosition < initialOffset);

            String value = readUTFString(dataBaseStorage, prevOffset, (int) dataBaseStorage.length() - prevOffset);

            ValidityChecker.checkTableValue(value);

            try {
                table.put(key, provider.deserialize(table, value));
            } catch (ParseException ex) {
                throw new IOException(ex.getMessage());
            }
        }
    }
}

