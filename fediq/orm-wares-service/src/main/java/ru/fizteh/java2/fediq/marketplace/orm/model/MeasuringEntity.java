package ru.fizteh.java2.fediq.marketplace.orm.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 10.11.14
 */
@Entity
@Table(name = "measures")
public class MeasuringEntity {
    @Column(unique = true, nullable = false)
    @Index(name = "measures_name_index")
    private String name;

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public MeasuringEntity(String name) {
        this.name = name;
    }

    public MeasuringEntity() {
        // Default constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
