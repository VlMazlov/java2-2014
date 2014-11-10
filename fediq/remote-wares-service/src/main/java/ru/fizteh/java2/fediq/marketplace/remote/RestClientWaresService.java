package ru.fizteh.java2.fediq.marketplace.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.model.Ware;
import ru.fizteh.java2.fediq.marketplace.model.WareDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 14/10/14
 */
@Service
public class RestClientWaresService implements WaresService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("http://${ru.fizteh.java2.fediq.marketplace.rest.host:localhost}:" +
            "${ru.fizteh.java2.fediq.marketplace.rest.port:8080}/" +
            "${ru.fizteh.java2.fediq.marketplace.rest.waresPrefix:}")
    private String baseUrl;

    @Override
    public Ware getWare(String id) {
        return restTemplate.getForObject(buildWareUrl(id), Ware.class);
    }

    @Override
    public void saveWare(Ware ware) {
        restTemplate.put(buildWareUrl(ware.getIdentifier()), ware);
    }

    @Override
    public Ware createWare(WareDescription description) {
        return restTemplate.postForObject(buildWareUrl(""), description, Ware.class);
    }

    @Override
    public boolean deleteWare(String id) {
        restTemplate.delete(buildWareUrl(id));
        return true;
    }

    @Override
    public List<String> listWareIds() {
        return restTemplate.getForObject(buildWareUrl(""), StringList.class);
    }

    private String buildWareUrl(String id) {
        return baseUrl + "wares/" + id;
    }

    public static class StringList extends ArrayList<String> {}
}
