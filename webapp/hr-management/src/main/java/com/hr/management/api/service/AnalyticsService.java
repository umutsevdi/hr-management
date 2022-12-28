package com.hr.management.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.writer.JsonReader;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class AnalyticsService {
    private Map<Long, Double> idSalaryMap;

    public AnalyticsService() {
        try {
            readJsonFromResources();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        log.info("Analytics Service initialized");
    }

    public void readJsonFromResources() throws
            Exception {
        // Read the "result.json" file from the resources directory
        InputStream inputStream = JsonReader.class.getResourceAsStream("/result.json");
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        // Use the ObjectMapper to parse the JSON file into a HashMap
        List<Object> list = objectMapper.readValue(inputStream, List.class);

        idSalaryMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            idSalaryMap.put((long) i + 1, (Double) list.get(i));
        }
    }

    public Double getSalary(Long id) {
        if (idSalaryMap.containsKey(id)) {
            return idSalaryMap.get(id);
        }
        return -1d;
    }
}
