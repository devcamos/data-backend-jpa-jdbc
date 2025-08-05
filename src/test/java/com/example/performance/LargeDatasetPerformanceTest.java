package com.example.performance;

import com.example.metrics.RepositoryMetrics;
import com.example.model.BaseEntity;
import com.example.repository.jdbc.JdbcExampleEntityRepository;
import com.example.util.TestDataGenerator;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.hikari.maximum-pool-size=20",
    "management.endpoints.web.exposure.include=metrics"
})
class LargeDatasetPerformanceTest {
    
    @Autowired
    private JdbcExampleEntityRepository repository;
    
    @Autowired
    private TestDataGenerator testDataGenerator;
    
    @Autowired
    private RepositoryMetrics metrics;
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @Test
    void testLargeDatasetInsertPerformance() {
        int datasetSize = Integer.parseInt(System.getProperty("dataset.size", "1000"));
        int iterations = Integer.parseInt(System.getProperty("test.iterations", "3"));
        
        List<BaseEntity> testData = testDataGenerator.generateLargeDataset(datasetSize);
        
        // Warm-up
        repository.save(testData.get(0));
        
        // Performance test
        for (int i = 0; i < iterations; i++) {
            Timer.Sample sample = metrics.startInsertTimer();
            
            try {
                testData.forEach(repository::save);
                
                sample.stop(Timer.builder("large.dataset.insert")
                    .tag("dataset.size", String.valueOf(datasetSize))
                    .tag("iteration", String.valueOf(i))
                    .register(meterRegistry));
                    
            } catch (Exception e) {
                metrics.recordError();
                throw e;
            }
        }
        
        // Assert performance thresholds
        Timer timer = meterRegistry.get("large.dataset.insert").timer();
        assertThat(timer.mean(TimeUnit.MILLISECONDS)).isLessThan(10000); // 10 seconds threshold
    }
    
    @Test
    void testCategorizedDatasetPerformance() {
        int datasetSize = 5000;
        int categoryCount = 10;
        
        List<BaseEntity> testData = testDataGenerator.generateCategorizedDataset(datasetSize, categoryCount);
        
        // Insert test data
        Timer.Sample insertSample = metrics.startInsertTimer();
        testData.forEach(repository::save);
        insertSample.stop(Timer.builder("categorized.dataset.insert")
            .tag("dataset.size", String.valueOf(datasetSize))
            .tag("categories", String.valueOf(categoryCount))
            .register(meterRegistry));
        
        // Query performance test
        Timer.Sample querySample = metrics.startQueryTimer();
        List<BaseEntity> results = repository.findAll();
        querySample.stop(Timer.builder("categorized.dataset.query")
            .tag("dataset.size", String.valueOf(datasetSize))
            .register(meterRegistry));
        
        assertEquals(datasetSize, results.size());
    }
    
    @Test
    void testMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Generate and process large dataset
        List<BaseEntity> testData = testDataGenerator.generateLargeDataset(10000);
        testData.forEach(repository::save);
        
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = finalMemory - initialMemory;
        
        // Log memory usage
        System.out.printf("Memory Usage: Initial=%d MB, Final=%d MB, Increase=%d MB%n",
            initialMemory / 1024 / 1024,
            finalMemory / 1024 / 1024,
            memoryIncrease / 1024 / 1024);
        
        // Assert reasonable memory usage (less than 500MB increase)
        assertThat(memoryIncrease / 1024 / 1024).isLessThan(500);
    }
} 