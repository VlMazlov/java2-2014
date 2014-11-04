package ru.fizteh.java2.vlmazlov.marketplace.api;

import java.util.List;

/**
 * Created by vlmazlov on 03.11.14.
 */
public interface GenericManager<T, V extends ManageableEntry>
{
    public V get(String id);
    public void save(V toSave);
    public V create(T description);
    public boolean delete(String id);
    public List<String> list();
}
