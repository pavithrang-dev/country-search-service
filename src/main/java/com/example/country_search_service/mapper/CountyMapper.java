package com.example.country_search_service.mapper;

import com.example.country_search_service.model.CountyDto;
import com.example.country_search_service.model.CountyEntity;
import org.springframework.stereotype.Component;

@Component
public class CountyMapper {

    public CountyDto toDto(CountyEntity entity) {
        return new CountyDto(
                entity.getFips(),
                entity.getState(),
                entity.getName()
        );
    }
}
