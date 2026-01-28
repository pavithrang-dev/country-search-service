package com.example.country_search_service.controller;

import com.example.country_search_service.controller.CountySearchController;
import com.example.country_search_service.model.CountyDto;
import com.example.country_search_service.service.CountySearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;



import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountySearchController.class)
class CountySearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CountySearchService service;

    @Test
    void shouldReturnSuggestions_whenValidQueryProvided() throws Exception {

        List<CountyDto> response = List.of(
                new CountyDto("53015", "WA", "Cowlitz")
        );

        when(service.suggest("wa")).thenReturn(response);

        mockMvc.perform(get("/suggest")
                        .param("q", "wa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("WA"))
                .andExpect(jsonPath("$[0].name").value("Cowlitz"));

        verify(service).suggest("wa");
    }

    @Test
    void shouldReturnBadRequest_whenQueryIsMissing() throws Exception {
        mockMvc.perform(get("/suggest"))
                .andExpect(status().isBadRequest());
    }
}
