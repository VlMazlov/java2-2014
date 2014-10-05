package ru.fizteh.java2.fediq.marketplace.inmemory;

import org.springframework.stereotype.Service;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.model.Ware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 06/10/14
 */
@Service
public class InMemoryWaresService implements WaresService {

    private Map<String, Ware> waresMap = new HashMap<>();

    @Override
    public Ware getWare(String id) {
        // FIXME checks
        return waresMap.get(id);
    }

    @Override
    public void saveWare(Ware ware) {
        // FIXME checks
        waresMap.put(ware.getId(), ware);
    }
}
