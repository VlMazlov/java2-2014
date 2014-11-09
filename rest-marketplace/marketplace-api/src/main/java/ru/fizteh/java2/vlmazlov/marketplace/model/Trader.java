package ru.fizteh.java2.vlmazlov.marketplace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.fizteh.java2.vlmazlov.marketplace.api.ManageableEntry;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class Trader extends TraderDescription implements ManageableEntry
{
    @JsonProperty("id")
    private String identifier;

    public Trader()
    {
    }

    public Trader(TraderDescription traderDescription, String identifier)
    {
        super(traderDescription.getName(), traderDescription.getCountry());
        this.identifier = identifier;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
}
