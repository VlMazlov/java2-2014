package ru.fizteh.java2.fediq.marketplace.orm.model;

import ru.fizteh.java2.fediq.marketplace.model.Ware;

import javax.persistence.*;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 10.11.14
 */
@Entity
@Table(name = "wares")
public class WareEntity {

    @Column(name = "id", nullable = false, unique = true)
    @Id
    private String identifier;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "measure", nullable = false)
    private int measureId;

    @JoinColumn(name = "measure", referencedColumnName = "id", unique = true, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private MeasuringEntity measuringEntity;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }

    public MeasuringEntity getMeasuringEntity() {
        return measuringEntity;
    }

    public void setMeasuringEntity(MeasuringEntity measuringEntity) {
        this.measuringEntity = measuringEntity;
    }

    public Ware toWare() {
        Ware ware = new Ware();
        ware.setIdentifier(getIdentifier());
        ware.setMeasuring(getMeasuringEntity().getName());
        ware.setName(getName());
        return ware;
    }
}
