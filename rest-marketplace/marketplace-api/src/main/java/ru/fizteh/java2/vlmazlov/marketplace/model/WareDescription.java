package ru.fizteh.java2.vlmazlov.marketplace.model;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class WareDescription
{

    private String name;
    private String measuring;

    public WareDescription(String name, String measuring)
    {
        this.name = name;
        this.measuring = measuring;
    }

    public WareDescription()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getMeasuring()
    {
        return measuring;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setMeasuring(String measuring)
    {
        this.measuring = measuring;
    }
}
