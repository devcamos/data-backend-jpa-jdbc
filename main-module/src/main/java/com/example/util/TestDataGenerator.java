package com.example.util;

import com.example.model.BaseEntity;
import com.example.model.EnhancedEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    
    private static final String[] CATEGORIES = {
        "Technology", "Finance", "Healthcare", "Education", "Retail",
        "Manufacturing", "Transportation", "Entertainment", "Sports", "Food"
    };
    
    private static final String[] DESCRIPTIONS = {
        "High priority task", "Standard operation", "Critical system", "Regular maintenance",
        "Feature enhancement", "Bug fix", "Performance optimization", "Security update",
        "User interface improvement", "Backend integration"
    };
    
    public List<BaseEntity> generateLargeDataset(int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> BaseEntity.builder()
                .field1(NAMES[random.nextInt(NAMES.length)])
                .field2(COMPANIES[random.nextInt(COMPANIES.length)])
                .field3(random.nextInt(1000) + 1)
                .build())
            .collect(Collectors.toList());
    }
    
    public List<BaseEntity> generateCategorizedDataset(int size, int categoryCount) {
        return IntStream.range(0, size)
            .mapToObj(i -> BaseEntity.builder()
                .field1("TestData" + i)
                .field2("Category" + (i % categoryCount))
                .field3(i)
                .build())
            .collect(Collectors.toList());
    }
    
    public BaseEntity generateSingleEntity() {
        return BaseEntity.builder()
            .field1(NAMES[random.nextInt(NAMES.length)])
            .field2(COMPANIES[random.nextInt(COMPANIES.length)])
            .field3(random.nextInt(1000) + 1)
            .build();
    }
    
    public List<EnhancedEntity> generateEnhancedDataset(int size) {
        LocalDateTime now = LocalDateTime.now();
        
        return IntStream.range(0, size)
            .mapToObj(i -> EnhancedEntity.builder()
                .name(NAMES[random.nextInt(NAMES.length)])
                .email("user" + i + "@example.com")
                .description(DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)])
                .createdAt(now.minusDays(random.nextInt(30)))
                .updatedAt(now.minusDays(random.nextInt(7)))
                .priority(random.nextInt(10) + 1)
                .isActive(random.nextBoolean())
                .category(CATEGORIES[random.nextInt(CATEGORIES.length)])
                .externalId("EXT-" + String.format("%06d", i))
                .build())
            .collect(Collectors.toList());
    }
    
    public List<EnhancedEntity> generateCategorizedEnhancedDataset(int size, int categoryCount) {
        LocalDateTime now = LocalDateTime.now();
        
        return IntStream.range(0, size)
            .mapToObj(i -> EnhancedEntity.builder()
                .name("EnhancedEntity" + i)
                .email("entity" + i + "@example.com")
                .description("Test description for entity " + i)
                .createdAt(now.minusDays(i % 30))
                .updatedAt(now.minusDays(i % 7))
                .priority((i % 10) + 1)
                .isActive(i % 3 != 0)
                .category("Category" + (i % categoryCount))
                .externalId("EXT-" + String.format("%06d", i))
                .build())
            .collect(Collectors.toList());
    }
    
    public EnhancedEntity generateSingleEnhancedEntity() {
        LocalDateTime now = LocalDateTime.now();
        
        return EnhancedEntity.builder()
            .name(NAMES[random.nextInt(NAMES.length)])
            .email("user" + random.nextInt(1000) + "@example.com")
            .description(DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)])
            .createdAt(now.minusDays(random.nextInt(30)))
            .updatedAt(now.minusDays(random.nextInt(7)))
            .priority(random.nextInt(10) + 1)
            .isActive(random.nextBoolean())
            .category(CATEGORIES[random.nextInt(CATEGORIES.length)])
            .externalId("EXT-" + String.format("%06d", random.nextInt(100000)))
            .build();
    }
} 