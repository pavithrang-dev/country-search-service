package com.example.country_search_service.controller;

import com.example.country_search_service.model.CountyDto;
import com.example.country_search_service.service.CountySearchService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/suggest")
public class CountySearchController{
    private final CountySearchService service;

    public CountySearchController(CountySearchService service) {
        this.service = service;
    }
    @GetMapping
    public List<CountyDto> suggest(@RequestParam
                                   @NotBlank(message = "Query parameter 'q' must not be blank") String q){
        return service.suggest(q);
    }
}
