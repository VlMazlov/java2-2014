package ru.fizteh.java2.vlmazlov.marketplace.model;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class TraderDescription
{

    private final String name;
    //needs fixing?
    private final String country;

    protected TraderDescription(String name, String country)
    {
        this.name = name;
        this.country = country;
    }

    public String getName()
    {
        return name;
    }

    public String getCountry()
    {
        return country;
    }
}
