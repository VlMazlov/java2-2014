package ru.fizteh.java2.fediq.marketplace.api;

import ru.fizteh.java2.fediq.marketplace.model.Ware;
import ru.fizteh.java2.fediq.marketplace.model.WareDescription;

import java.util.List;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 06/10/14
 */
public interface WaresService {
    Ware getWare(String id);
    void saveWare(Ware ware);
    Ware createWare(WareDescription description);
    boolean deleteWare(String id);
    List<String> listWareIds();
}
