package ru.fizteh.java2.vlmazlov.storage.core.io;

import ru.fizteh.java2.vlmazlov.storage.api.Storeable;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTable;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTableProvider;
import ru.fizteh.java2.vlmazlov.storage.utils.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StoreableTableFileReader {

    private Map.Entry<String, Storeable> curEntry;
    private final Map<String, Storeable> data;
    private final Iterator<Map.Entry<String, Storeable>> iterator;
    private final StoreableTable table;
    private final StoreableTableProvider provider;


    private String readUTFString(RandomAccessFile dataBaseStorage, int readingPosition, int length) 
    throws IOException {
        byte[] bytes = new byte[length];

        dataBaseStorage.seek(readingPosition);
        dataBaseStorage.read(bytes);
        return new String(bytes, "UTF-8");
    }

    public StoreableTableFileReader(File file, StoreableTable table, StoreableTableProvider provider)
    throws IOException, ValidityCheckFailedException {
        data = new HashMap<String, Storeable>();
        this.table = table;
        this.provider = provider;
        loadFile(file);
        iterator = data.entrySet().iterator();
    }

    private void loadFile(File file) throws IOException, ValidityCheckFailedException {
        
        if ((!file.exists()) || (file.length() == 0)) {
            return;
        }

        try (RandomAccessFile dataBaseStorage = new RandomAccessFile(file, "r")) {
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
                        data.put(key, provider.deserialize(table, value));
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
                data.put(key, provider.deserialize(table, value));
            } catch (ParseException ex) {
                throw new IOException(ex.getMessage());
            }

        }
    }

    public String nextKey() {
        if (iterator.hasNext()) {
           curEntry = iterator.next();
           return curEntry.getKey();
        } else {
            return null;
        }
    }

    public String getCurrentSerializedValue() {
        return provider.serialize(table, curEntry.getValue());
    }

    public Map<String, Storeable> getData() {
        return data;
    }
}
