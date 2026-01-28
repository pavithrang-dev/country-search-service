package com.example.country_search_service.repository;


import com.example.country_search_service.model.CountyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountyRepository extends JpaRepository<CountyEntity, String> {

    /**
     * State-only search (e.g. q=wa)
     * Ordered alphabetically by county name
     */
    List<CountyEntity> findByStateIgnoreCaseOrderByNameAsc(
            String state,
            Pageable pageable
    );

    /**
     * Name-only or name + state search
     * (e.g. q=cowl or q=cowlitz, wa)
     */
    @Query("""
        SELECT c FROM CountyEntity c
        WHERE
          LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))
          AND (:state IS NULL OR LOWER(c.state) = LOWER(:state))
        ORDER BY
          c.state ASC,
          c.name ASC
    """)
    List<CountyEntity> searchByNameAndState(
            @Param("name") String name,
            @Param("state") String state,
            Pageable pageable
    );
}
