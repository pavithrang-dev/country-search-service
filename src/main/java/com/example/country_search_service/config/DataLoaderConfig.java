package com.example.country_search_service.config;

import com.example.country_search_service.model.CountyEntity;
import com.example.country_search_service.repository.CountyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoaderConfig implements ApplicationRunner {
    private final CountyRepository repository;
    private final ObjectMapper objectMapper;
    @Override
    public void run(ApplicationArguments args) throws Exception{
        if(repository.count()>0){
           // log.info("County data already loaded.Skipping.");
            return;
        }
     //   log.info("Loading county data from data.json");
        InputStream inputStream = new ClassPathResource("data.json").getInputStream();

        List<CountyEntity> counties = objectMapper.readValue(inputStream, new TypeReference<List<CountyEntity>>() {}
        );
        repository.saveAll(counties);
      //  log.info("Loaded {} counties into database", counties.size());

    }
}
