package ru.fizteh.java2.vlmazlov.marketplace.model;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class WareDescription
{

    private final String name;
    private final String measuring;

    protected WareDescription(String name, String measuring)
    {
        this.name = name;
        this.measuring = measuring;
    }

    public String getName()
    {
        return name;
    }

    public String getMeasuring()
    {
        return measuring;
    }
}
