package com.example.country_search_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "counties", indexes = {
        @Index(name = "idx_county_name", columnList = "name"),
        @Index(name = "idx_state", columnList = "state")
})
public class CountyEntity {
    @Override
    public String toString() {
        return "CountyEntity{" +
                "fips='" + fips + '\'' +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Id
    @Column(length = 10)
    private String fips;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String name;

    // No-args constructor
    public CountyEntity() {
    }

    // All-args constructor
    public CountyEntity(String fips, String state, String name) {
        this.fips = fips;
        this.state = state;
        this.name = name;
    }

    // Getter and setter for fips
    public String getFips() {
        return fips;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }

    // Getter and setter for state
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}