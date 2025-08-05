package com.example.performance;

import com.example.model.BaseEntity;
import com.example.repository.jdbc.JdbcExampleEntityRepository;
import com.example.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimplePerformanceTest {
    
    @Autowired
    private JdbcExampleEntityRepository repository;
    
    @Autowired
    private TestDataGenerator testDataGenerator;
    
    @Test
    void testLargeDatasetInsertPerformance() {
        int datasetSize = Integer.parseInt(System.getProperty("dataset.size", "1000"));
        int iterations = Integer.parseInt(System.getProperty("test.iterations", "3"));
        
        List<BaseEntity> testData = testDataGenerator.generateLargeDataset(datasetSize);
        
        // Warm-up
        repository.save(testData.get(0));
        
        // Performance test
        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            
            try {
                testData.forEach(repository::save);
                
                long duration = System.currentTimeMillis() - startTime;
                System.out.printf("Iteration %d: Inserted %d records in %d ms%n", 
                    i + 1, datasetSize, duration);
                    
                // Assert performance threshold (10 seconds)
                assertTrue(duration < 10000, "Performance threshold exceeded: " + duration + "ms");
                
            } catch (Exception e) {
                System.err.println("Error during iteration " + (i + 1) + ": " + e.getMessage());
                throw e;
            }
        }
    }
    
    @Test
    void testCategorizedDatasetPerformance() {
        int datasetSize = 5000;
        int categoryCount = 10;
        
        List<BaseEntity> testData = testDataGenerator.generateCategorizedDataset(datasetSize, categoryCount);
        
        // Insert test data
        long insertStartTime = System.currentTimeMillis();
        testData.forEach(repository::save);
        long insertDuration = System.currentTimeMillis() - insertStartTime;
        
        System.out.printf("Inserted %d records in %d ms%n", datasetSize, insertDuration);
        
        // Query performance test
        long queryStartTime = System.currentTimeMillis();
        List<BaseEntity> results = repository.findAll();
        long queryDuration = System.currentTimeMillis() - queryStartTime;
        
        System.out.printf("Queried %d records in %d ms%n", results.size(), queryDuration);
        
        assertEquals(datasetSize, results.size());
        
        // Assert performance thresholds
        assertTrue(insertDuration < 30000, "Insert performance threshold exceeded: " + insertDuration + "ms");
        assertTrue(queryDuration < 5000, "Query performance threshold exceeded: " + queryDuration + "ms");
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
        assertTrue(memoryIncrease / 1024 / 1024 < 500, 
            "Memory usage too high: " + (memoryIncrease / 1024 / 1024) + "MB");
    }
} 