package com.example.country_search_service.repository;

import com.example.country_search_service.model.CountyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CountyRepository extends JpaRepository<CountyEntity,String> {
    @Query("""
            SELECT c FROM CountyEntity c 
            WHERE 
             LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%'))
             OR
             LOWER(c.state) LIKE LOWER(CONCAT('%', :term, '%'))
            """)
    List<CountyEntity> search(@Param("term")String term, Pageable pageable);
}
