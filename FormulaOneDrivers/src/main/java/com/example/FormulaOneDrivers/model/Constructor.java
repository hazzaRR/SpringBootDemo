package com.example.FormulaOneDrivers.model;

import jakarta.persistence.*;

@Entity(name = "Constructor")
@Table(
        name = "Constructor",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_constructor_code", columnNames = "constructor_code")
        }
)
public class Constructor {
    @Id
    @SequenceGenerator(
            name = "constructor_sequence",
            sequenceName = "constructor_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "constructor_sequence"
    )
    private long id;

    @Column(
            name = "constructor_code",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String constructorCode;

    @Column(
            name = "name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String name;

    @Column(
            name = "headquarters",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String headquarters;

    protected Constructor() {
    }

    public Constructor(String constructorCode, String name, String headquarters) {
        this.constructorCode = constructorCode;
        this.name = name;
        this.headquarters = headquarters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConstructorCode() {
        return constructorCode;
    }

    public void setConstructorCode(String constructorCode) {
        this.constructorCode = constructorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    @Override
    public String toString() {
        return "Constructor{" +
                "constructorCode='" + constructorCode + '\'' +
                ", name='" + name + '\'' +
                ", headquarters='" + headquarters + '\'' +
                '}';
    }
}
