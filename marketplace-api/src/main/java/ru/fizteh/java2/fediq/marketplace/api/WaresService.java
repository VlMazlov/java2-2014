package ru.fizteh.java2.fediq.marketplace.api;

import ru.fizteh.java2.fediq.marketplace.model.Ware;

import java.util.List;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 06/10/14
 */
public interface WaresService {
    Ware getWare(String id);
    void saveWare(Ware ware);
    boolean deleteWare(String id);
    List<String> listWareIds();
}
