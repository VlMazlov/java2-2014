package ru.fizteh.java2.fediq.marketplace.inmemory;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.model.Ware;
import ru.fizteh.java2.fediq.marketplace.model.WareDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 06/10/14
 */
@Service
public class InMemoryWaresService implements WaresService {

    private Map<String, Ware> waresMap = new ConcurrentHashMap<>();

    @Override
    public Ware getWare(String id) {
        // FIXME validate
        return waresMap.get(id);
    }

    @Override
    public void saveWare(Ware ware) {
        // FIXME validate
        waresMap.put(ware.getIdentifier(), ware);
    }

    @Override
    public boolean deleteWare(String id) {
        // FIXME validate
        return waresMap.remove(id) != null;
    }

    @Override
    public Ware createWare(WareDescription description) {
        // FIXME validate
        Ware ware = new Ware();
        ware.setMeasuring(description.getMeasuring());
        ware.setName(description.getName());
        ware.setIdentifier(generateId());
        saveWare(ware);
        return ware;
    }

    @Override
    public List<String> listWareIds() {
        return new ArrayList<>(waresMap.keySet());
    }

    private static String generateId() {
        return RandomStringUtils.randomAlphanumeric(6);
    }
}
