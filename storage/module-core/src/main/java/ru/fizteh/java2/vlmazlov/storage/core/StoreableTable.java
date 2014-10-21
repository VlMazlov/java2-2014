package ru.fizteh.java2.vlmazlov.storage.core;

import ru.fizteh.java2.vlmazlov.storage.api.ColumnFormatException;
import ru.fizteh.java2.vlmazlov.storage.api.Storeable;
import ru.fizteh.java2.vlmazlov.storage.api.Table;
import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTable;
import ru.fizteh.java2.vlmazlov.storage.core.io.StoreableTableFileManager;
import ru.fizteh.java2.vlmazlov.storage.utils.ValidityCheckFailedException;
import ru.fizteh.java2.vlmazlov.storage.utils.ValidityChecker;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StoreableTable extends GenericTable<Storeable> implements Table, Cloneable, AutoCloseable {

    private StoreableTableProvider specificProvider;
    private boolean isClosed;
    private final List<Class<?>> valueTypes;

    public StoreableTable(StoreableTableProvider provider, 
        String name, List<Class<?>> valueTypes) 
    throws ValidityCheckFailedException, IOException {
        
        super(provider, name, false);

        if (valueTypes == null) {
            throw new IllegalArgumentException("Value types not specified");
        }

        specificProvider = provider;
        ///questionable
        this.valueTypes = Collections.unmodifiableList(new ArrayList<Class<?>>(valueTypes));
        isClosed = false;

        setInitialSize(provider, name);
    }

    public StoreableTable(StoreableTableProvider provider, 
        String name, boolean autoCommit, List<Class<?>> valueTypes) 
    throws ValidityCheckFailedException, IOException {
        
        super(provider, name, autoCommit);

        if (valueTypes == null) {
            throw new IllegalArgumentException("Value types not specified");
        }

        specificProvider = provider;
        this.valueTypes = Collections.unmodifiableList(new ArrayList<Class<?>>(valueTypes));
        isClosed = false;

        setInitialSize(provider, name);
    }

    private void setInitialSize(StoreableTableProvider provider, String name)
    throws ValidityCheckFailedException, IOException {
        getCommitLock.writeLock().lock();

        try {
            commitedSize = StoreableTableFileManager.getTableSize(name, provider, this);
        } finally {
            getCommitLock.writeLock().unlock();
        }
    }

    //MUST be under lock
    private void loadKey(String key) 
    throws IOException, ValidityCheckFailedException {
        checkClosed();

        Map<String, Storeable> fileData = StoreableTableFileManager.readFileForKey(key, this, specificProvider);
        Storeable value = fileData.get(key);

        if (!Thread.holdsLock(getCommitLock.writeLock())) {

            getCommitLock.readLock().unlock();
            getCommitLock.writeLock().lock();

            try {
                
                commited.putAll(fileData);

                //presence of the key in question, if it is the case, must be ensured
                if (value != null) {
                  commited.put(key, value);
                } 
            } finally    {
                getCommitLock.writeLock().unlock();
                getCommitLock.readLock().lock();
            }

        } else {
            commited.putAll(fileData);

            //presence of the key in question, if it is the case, must be ensured
            if (value != null) {
                commited.put(key, value);
            }
        }
    }

    //MUST be under lock
    @Override
    protected Storeable getCommited(String key) {
        checkClosed();

        if (!commited.containsKey(key)) {
            try {
                loadKey(key);
            } catch (Exception ex) {
                throw new RuntimeException("Unable to load key from file: " + ex.getMessage());
            }
        }

        //readlock reaquired in loadKey()
        return commited.get(key);
    }

    @Override
    public String getName() {
        checkClosed();
        return super.getName();
    }

    @Override
    public Storeable get(String key) {
        checkClosed();
        return super.get(key);
    }

    @Override
    public Storeable put(String key, Storeable value) throws ColumnFormatException {
        checkClosed();

        try {
            ValidityChecker.checkValueFormat(this, value);
        } catch (ValidityCheckFailedException ex) {
            throw new ColumnFormatException(ex.getMessage());
        }

        return super.put(key, value);
    }

    @Override
    public Storeable remove(String key) {
        checkClosed();
        return super.remove(key);
    }

    @Override
    public int commit() throws IOException {
        checkClosed();
        return super.commit();
    }

    @Override
    public int rollback() {
        checkClosed();
        return super.rollback();
    }

    @Override
    public int getColumnsCount() {
        checkClosed();
        return valueTypes.size();
    }

    @Override
    public Class<?> getColumnType(int columnIndex) throws IndexOutOfBoundsException {
        checkClosed();
        return valueTypes.get(columnIndex);
    }

    @Override
    protected boolean isValueEqual(Storeable first, Storeable second) {
        checkClosed();
        return specificProvider.serialize(this, first).equals(specificProvider.serialize(this, second));
    }

    @Override
    public void checkRoot(File root) throws ValidityCheckFailedException {
        checkClosed();
        ValidityChecker.checkMultiStoreableTableRoot(root);
    }

    @Override
    public int size() {
        checkClosed();
        
        getCommitLock.readLock().lock();
        
        int size = commitedSize;

        try {

            for (Map.Entry<String, Storeable> entry : changed.get().entrySet()) {
                if (getCommited(entry.getKey()) == null) {
                    ++size;
                }
            }

            for (String entry : deleted.get()) {
                if (getCommited(entry) != null) {
                    --size;
                }
            }
        } finally {
            getCommitLock.readLock().unlock();
        }

        return size;
    }

    @Override
    protected void storeOnCommit() throws IOException, ValidityCheckFailedException {
        checkClosed();

        StoreableTableFileManager.writeSize(this, specificProvider);
        StoreableTableFileManager.writeSignature(this, specificProvider);
        StoreableTableFileManager.modifyMultipleFiles(changed.get(), deleted.get(), this, specificProvider);
    }

    @Override
    public StoreableTable clone() throws CloneNotSupportedException{
        checkClosed();

        throw new CloneNotSupportedException("Cloning not supported in the current version");
    }

    public void close() {
        if (isClosed) {
            return;
        }

        specificProvider.closeTable(getName());
        rollback();
        isClosed = true;
    }

    public void checkClosed() {
        if (isClosed) {
            throw new IllegalStateException("trying to operate on a closed table");
        }
    }

    public String toString() {
        checkClosed();
        StringBuilder builder = new StringBuilder();

        builder.append(getClass().getSimpleName());
        builder.append("[");
        builder.append(new File(provider.getRoot(), getName()).getPath());
        builder.append("]");

        return builder.toString();
    }
}
