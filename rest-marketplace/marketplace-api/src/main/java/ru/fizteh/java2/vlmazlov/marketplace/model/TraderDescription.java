package ru.fizteh.java2.vlmazlov.marketplace.model;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class TraderDescription
{

    private String name;
    //needs fixing?
    private String country;

    public TraderDescription(String name, String country)
    {
        this.name = name;
        this.country = country;
    }

    public TraderDescription()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getCountry()
    {
        return country;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
}
