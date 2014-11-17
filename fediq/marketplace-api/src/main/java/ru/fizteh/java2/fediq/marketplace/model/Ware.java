package ru.fizteh.java2.fediq.marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 06/10/14
 */
public class Ware extends WareDescription {
    @JsonProperty("id")
    private String identifier;

    @JsonIgnore
    private String internalData;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getInternalData() {
        return internalData;
    }

    public void setInternalData(String internalData) {
        this.internalData = internalData;
    }
}
