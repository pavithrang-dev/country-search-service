package com.example.country_search_service.service;

import com.example.country_search_service.mapper.CountyMapper;
import com.example.country_search_service.model.CountyDto;
import com.example.country_search_service.repository.CountyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class CountySearchService {
    private final CountyRepository countyRepository;
    private final CountyMapper countyMapper;

    public List<CountyDto> suggest(String q){
        String normalized = q.trim().toLowerCase();
        Pageable limit = PageRequest.of(0, 5);
        return countyRepository.search(normalized,limit).stream().map(countyMapper::toDto).toList();
    }
}
