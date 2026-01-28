package com.example.country_search_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountyDto {
    private String fips;
    private String state;
    private String name;
}

