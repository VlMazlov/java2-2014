package ru.fizteh.java2.fediq.marketplace.api;

import ru.fizteh.java2.fediq.marketplace.model.Ware;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 06/10/14
 */
public interface WaresService {
    Ware getWare(String id);
    void saveWare(Ware ware);
}
