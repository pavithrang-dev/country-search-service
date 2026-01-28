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
public class CountySearchService {
    private final CountyRepository repository;
    private final CountyMapper mapper;

    public CountySearchService(CountyRepository repository, CountyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CountyDto> suggest(String q) {

        String normalized = q.trim();

        Pageable limit = PageRequest.of(0, 5);

        // STATE-ONLY SEARCH
        if (normalized.length() == 2 && !normalized.contains(",")) {
            return repository
                    .findByStateIgnoreCaseOrderByNameAsc(normalized, limit)
                    .stream()
                    .map(mapper::toDto)
                    .toList();
        }

        // NAME or NAME + STATE
        String[] parts = normalized.split(",");

        String name = parts[0].trim();
        String state = parts.length > 1 ? parts[1].trim() : null;

        return repository
                .searchByNameAndState(name, state, limit)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

}
