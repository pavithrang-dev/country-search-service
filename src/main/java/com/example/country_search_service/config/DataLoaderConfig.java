package com.example.country_search_service.config;

import com.example.country_search_service.model.CountyEntity;
import com.example.country_search_service.repository.CountyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoaderConfig implements ApplicationRunner {
    private final CountyRepository repository;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(DataLoaderConfig.class);

    public DataLoaderConfig(CountyRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repository.count() > 0) {
             log.info("County data already loaded.Skipping.");
            return;
        }
         log.info("Loading county data from data.json");
        InputStream inputStream = new ClassPathResource("data.json").getInputStream();

        List<CountyEntity> counties = objectMapper.readValue(inputStream, new TypeReference<List<CountyEntity>>() {});
        repository.saveAll(counties);
         log.info("Loaded {} counties into database", counties.size());
    }
}