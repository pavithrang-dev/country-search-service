package com.example.country_search_service.controller;

import com.example.country_search_service.model.CountyDto;
import com.example.country_search_service.service.CountySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggest")
public class CountySearchController{
    private final CountySearchService service;

    public List<CountyDto> suggest(@RequestParam String q){
        return service.suggest(q);
    }
}
