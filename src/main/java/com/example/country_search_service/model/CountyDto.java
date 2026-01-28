package com.example.country_search_service.model;

public class CountyDto {
    private String fips;
    private String state;
    private String name;

    public CountyDto(String fips, String state, String name) {
        this.fips = fips;
        this.state = state;
        this.name = name;
    }

    public String getFips() {
        return fips;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}