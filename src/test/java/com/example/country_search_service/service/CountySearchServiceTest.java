package com.example.country_search_service.service;

import com.example.country_search_service.mapper.CountyMapper;
import com.example.country_search_service.model.CountyDto;
import com.example.country_search_service.model.CountyEntity;
import com.example.country_search_service.repository.CountyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountySearchServiceTest {

    @Mock
    private CountyRepository repository;

    @Mock
    private CountyMapper mapper;

    @InjectMocks
    private CountySearchService service;

    private CountyEntity countyEntity;
    private CountyDto countyDto;

    @BeforeEach
    void setUp() {
        countyEntity = new CountyEntity("53015", "WA", "Cowlitz");
        countyDto = new CountyDto("53015", "WA", "Cowlitz");
    }

    @Test
    void shouldSearchByStateOnly_whenQueryIsTwoLetters() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findByStateIgnoreCaseOrderByNameAsc("wa", pageable))
                .thenReturn(List.of(countyEntity));
        when(mapper.toDto(countyEntity)).thenReturn(countyDto);

        // when
        List<CountyDto> result = service.suggest("wa");

        // then
        assertEquals(1, result.size());
        assertEquals("WA", result.get(0).getState());

        verify(repository).findByStateIgnoreCaseOrderByNameAsc("wa", pageable);
        verify(repository, never()).searchByNameAndState(any(), any(), any());
    }

    @Test
    void shouldSearchByNameOnly_whenNoStateProvided() {
        Pageable pageable = PageRequest.of(0, 5);

        when(repository.searchByNameAndState("cowl", null, pageable))
                .thenReturn(List.of(countyEntity));
        when(mapper.toDto(countyEntity)).thenReturn(countyDto);

        List<CountyDto> result = service.suggest("cowl");

        assertEquals(1, result.size());
        verify(repository).searchByNameAndState("cowl", null, pageable);
    }

    @Test
    void shouldSearchByNameAndState_whenCommaSeparatedQueryProvided() {
        Pageable pageable = PageRequest.of(0, 5);

        when(repository.searchByNameAndState("cowlitz", "wa", pageable))
                .thenReturn(List.of(countyEntity));
        when(mapper.toDto(countyEntity)).thenReturn(countyDto);

        List<CountyDto> result = service.suggest("cowlitz, wa");

        assertEquals(1, result.size());
        verify(repository).searchByNameAndState("cowlitz", "wa", pageable);
    }
}

