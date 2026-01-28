package com.example.country_search_service.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "counties", indexes = {
        @Index(name = "idx_county_name", columnList = "name"),
        @Index(name = "idx_state", columnList = "state")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountyEntity {

    @Id
    @Column(length = 10)
    private String fips;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String name;
}
