package ru.fizteh.java2.vlmazlov.marketplace.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.api.ManageableEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlmazlov on 03.11.14.
 */

@Service
public abstract class GenericRestClientManager<T, V extends ManageableEntry> implements GenericManager<T, V>
{
    @Value("http://${ru.fizteh.java2.fediq.marketplace.rest.host:localhost}:" +
            "${ru.fizteh.java2.vlmazlov.marketplace.rest.port:8080}/" +
            "${ru.fizteh.java2.vlmazlov.marketplace.rest.service.prefix:}")
    protected String baseURL;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public V get(String id)
    {
        return restTemplate.getForObject(constructURL(id), getTargetEntryClass());
    }

    @Override
    public void save(V toSave)
    {
        restTemplate.put(constructURL(toSave.getIdentifier()), toSave);
    }

    @Override
    public V create(T description)
    {
        return restTemplate.postForObject(constructURL(""), description, getTargetEntryClass());
    }

    @Override
    public boolean delete(String id)
    {
        restTemplate.delete(constructURL(id));
        return true;
    }

    @Override
    public List<String> list()
    {
        return restTemplate.getForObject(constructURL(""), StringList.class);
    }

    protected abstract String constructURL(String id);
    protected abstract Class<V> getTargetEntryClass();

    public static class StringList extends ArrayList<String> {}
}
