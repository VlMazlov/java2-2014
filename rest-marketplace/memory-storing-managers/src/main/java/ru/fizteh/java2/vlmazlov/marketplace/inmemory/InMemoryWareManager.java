package ru.fizteh.java2.vlmazlov.marketplace.inmemory;

import org.springframework.stereotype.Service;
import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.model.Ware;
import ru.fizteh.java2.vlmazlov.marketplace.model.WareDescription;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Service
public class InMemoryWareManager extends GenericInMemoryManager<WareDescription, Ware>
{
    public Ware constructByDescriptionAndId(WareDescription description, String id) {
        return new Ware(description, id);
    }
}
