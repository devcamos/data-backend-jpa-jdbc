package com.example.util;

import com.example.model.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TestDataGenerator {
    private final Random random = new Random();
    
    private static final String[] NAMES = {
        "John Doe", "Jane Smith", "Bob Johnson", "Alice Brown", "Charlie Wilson",
        "Diana Davis", "Edward Miller", "Fiona Garcia", "George Martinez", "Helen Rodriguez"
    };
    
    private static final String[] COMPANIES = {
        "TechCorp", "DataSystems", "CloudSoft", "InnovateLab", "DigitalWorks",
        "SmartSolutions", "FutureTech", "NextGen", "EliteSystems", "PrimeData"
    };
    
    /**
     * Generates a large dataset of BaseEntity objects with realistic test data
     * @param size Number of entities to generate
     * @return List of generated BaseEntity objects
     */
    public List<BaseEntity> generateLargeDataset(int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> BaseEntity.builder()
                .field1(NAMES[random.nextInt(NAMES.length)])
                .field2(COMPANIES[random.nextInt(COMPANIES.length)])
                .field3(random.nextInt(1000) + 1)
                .build())
            .collect(Collectors.toList());
    }
    
    /**
     * Generates test data with specific patterns for performance testing
     * @param size Number of entities to generate
     * @param categoryCount Number of categories to distribute data across
     * @return List of generated BaseEntity objects
     */
    public List<BaseEntity> generateCategorizedDataset(int size, int categoryCount) {
        return IntStream.range(0, size)
            .mapToObj(i -> BaseEntity.builder()
                .field1("TestData" + i)
                .field2("Category" + (i % categoryCount))
                .field3(i)
                .build())
            .collect(Collectors.toList());
    }
    
    /**
     * Generates a single BaseEntity with random data
     * @return Generated BaseEntity
     */
    public BaseEntity generateSingleEntity() {
        return BaseEntity.builder()
            .field1(NAMES[random.nextInt(NAMES.length)])
            .field2(COMPANIES[random.nextInt(COMPANIES.length)])
            .field3(random.nextInt(1000) + 1)
            .build();
    }
} 