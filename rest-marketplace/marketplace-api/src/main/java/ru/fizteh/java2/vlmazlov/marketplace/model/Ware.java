package ru.fizteh.java2.vlmazlov.marketplace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.fizteh.java2.vlmazlov.marketplace.api.ManageableEntry;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class Ware extends WareDescription implements ManageableEntry
{
    @JsonProperty("id")
    private String identifier;

    public Ware(WareDescription wareDescription, String identifier)
    {
        super(wareDescription.getName(), wareDescription.getMeasuring());
        this.identifier = identifier;
    }

    public Ware()
    {
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
