package ru.fizteh.java2.vlmazlov.marketplace.inmemory;

import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.api.ManageableEntry;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vlmazlov on 03.11.14.
 */
public abstract class GenericInMemoryManager<T, V extends ManageableEntry> implements GenericManager<T, V>
{
    private final Map<String, V> entriesMap = new ConcurrentHashMap<>();

    @Override
    public V get(String id)
    {
        return entriesMap.get(id);
    }

    @Override
    public void save(V toSave)
    {
        entriesMap.put(toSave.getIdentifier(), toSave);
    }

    @Override
    public V create(T description)
    {
        String id;

        do
        {
            id = generateId();
        } while (entriesMap.containsKey(id));

        V created = constructByDescriptionAndId(description, id);

        save(created);
        return created;
    }

    @Override
    public boolean delete(String id)
    {
        return entriesMap.remove(id) != null;
    }

    @Override
    public List<String> list()
    {
        return new ArrayList<>(entriesMap.keySet());
    }

    private String generateId()
    {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    protected abstract V constructByDescriptionAndId(T description, String id);
}
