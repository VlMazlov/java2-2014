package ru.fizteh.java2.vlmazlov.marketplace.inmemory;

import org.springframework.stereotype.Service;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Service
public class InMemoryTraderManager extends GenericInMemoryManager<TraderDescription, Trader>
{
    public Trader constructByDescriptionAndId(TraderDescription description, String id) {
        return new Trader(description, id);
        }
}