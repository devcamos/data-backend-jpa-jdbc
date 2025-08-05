package com.example.repository;

import com.example.model.BaseEntity;
import com.example.model.EnhancedEntity;
import com.example.repository.jdbc.JdbcExampleEntityRepository;
import com.example.repository.jdbc.JdbcEnhancedEntityRepository;
import com.example.repository.jpa.JpaEnhancedEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.hikari.maximum-pool-size=10"
})
class CrudOperationsTest {
    
    @Autowired(required = false)
    private JdbcExampleEntityRepository jdbcExampleRepository;
    
    @Autowired(required = false)
    private JdbcEnhancedEntityRepository jdbcEnhancedRepository;
    
    @Autowired(required = false)
    private JpaEnhancedEntityRepository jpaEnhancedRepository;
    
    @Test
    void testJdbcBaseEntityCrud() {
        if (jdbcExampleRepository == null) {
            System.out.println("JDBC BaseEntity repository not enabled, skipping test");
            return;
        }
        
        // Create
        BaseEntity entity = BaseEntity.builder()
            .field1("Test Field 1")
            .field2("Test Field 2")
            .field3(123)
            .build();
        
        BaseEntity saved = jdbcExampleRepository.save(entity);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        
        // Read
        Optional<BaseEntity> found = jdbcExampleRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Field 1", found.get().getField1());
        
        // Update
        saved.setField1("Updated Field 1");
        BaseEntity updated = jdbcExampleRepository.save(saved);
        assertEquals("Updated Field 1", updated.getField1());
        
        // Delete
        jdbcExampleRepository.deleteById(saved.getId());
        Optional<BaseEntity> deleted = jdbcExampleRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }
    
    @Test
    void testJdbcEnhancedEntityCrud() {
        if (jdbcEnhancedRepository == null) {
            System.out.println("JDBC EnhancedEntity repository not enabled, skipping test");
            return;
        }
        
        // Create
        EnhancedEntity entity = EnhancedEntity.builder()
            .name("Test Enhanced Entity")
            .email("test@example.com")
            .description("Test description")
            .priority(5)
            .isActive(true)
            .category("Technology")
            .externalId("EXT-001")
            .build();
        
        EnhancedEntity saved = jdbcEnhancedRepository.save(entity);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        
        // Read
        Optional<EnhancedEntity> found = jdbcEnhancedRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Enhanced Entity", found.get().getName());
        
        // Test custom queries
        List<EnhancedEntity> byName = jdbcEnhancedRepository.findByName("Test");
        assertFalse(byName.isEmpty());
        
        List<EnhancedEntity> byCategory = jdbcEnhancedRepository.findByCategory("Technology");
        assertFalse(byCategory.isEmpty());
        
        List<EnhancedEntity> activeEntities = jdbcEnhancedRepository.findByIsActive(true);
        assertFalse(activeEntities.isEmpty());
        
        // Update
        saved.setPriority(8);
        EnhancedEntity updated = jdbcEnhancedRepository.save(saved);
        assertEquals(8, updated.getPriority());
        
        // Test count and exists
        assertTrue(jdbcEnhancedRepository.existsById(saved.getId()));
        assertTrue(jdbcEnhancedRepository.count() > 0);
        
        // Delete
        jdbcEnhancedRepository.deleteById(saved.getId());
        Optional<EnhancedEntity> deleted = jdbcEnhancedRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }
    
    @Test
    void testJpaEnhancedEntityCrud() {
        if (jpaEnhancedRepository == null) {
            System.out.println("JPA EnhancedEntity repository not enabled, skipping test");
            return;
        }
        
        // Create multiple entities
        EnhancedEntity entity1 = EnhancedEntity.builder()
            .name("Alice Johnson")
            .email("alice.johnson@example.com")
            .description("High priority task")
            .priority(9)
            .isActive(true)
            .category("Technology")
            .externalId("EXT-001")
            .build();
        
        EnhancedEntity entity2 = EnhancedEntity.builder()
            .name("Bob Smith")
            .email("bob.smith@example.com")
            .description("Standard operation")
            .priority(5)
            .isActive(true)
            .category("Finance")
            .externalId("EXT-002")
            .build();
        
        EnhancedEntity entity3 = EnhancedEntity.builder()
            .name("Carol Davis")
            .email("carol.davis@example.com")
            .description("Critical system")
            .priority(7)
            .isActive(false)
            .category("Technology")
            .externalId("EXT-003")
            .build();
        
        jpaEnhancedRepository.save(entity1);
        jpaEnhancedRepository.save(entity2);
        jpaEnhancedRepository.save(entity3);
        
        // Test custom query methods
        List<EnhancedEntity> byName = jpaEnhancedRepository.findByNameContainingIgnoreCase("alice");
        assertFalse(byName.isEmpty());
        
        List<EnhancedEntity> byCategory = jpaEnhancedRepository.findByCategory("Technology");
        assertEquals(2, byCategory.size());
        
        List<EnhancedEntity> byPriority = jpaEnhancedRepository.findByPriorityGreaterThan(5);
        assertEquals(2, byPriority.size());
        
        List<EnhancedEntity> activeByCategory = jpaEnhancedRepository.findByCategoryAndIsActive("Technology", true);
        assertEquals(1, activeByCategory.size());
        
        List<EnhancedEntity> byPriorityRange = jpaEnhancedRepository.findByPriorityBetween(5, 8);
        assertEquals(2, byPriorityRange.size());
        
        // Test custom queries
        List<EnhancedEntity> activeByName = jpaEnhancedRepository.findActiveByNameContaining("alice");
        assertEquals(1, activeByName.size());
        
        List<EnhancedEntity> byCategoryAndPriority = jpaEnhancedRepository.findByCategoryAndPriorityGreaterThanEqual("Technology", 8);
        assertEquals(1, byCategoryAndPriority.size());
        
        // Test aggregation queries
        long techCount = jpaEnhancedRepository.countByCategory("Technology");
        assertEquals(2, techCount);
        
        Double avgPriority = jpaEnhancedRepository.getAveragePriorityByCategory("Technology");
        assertNotNull(avgPriority);
        assertEquals(8.0, avgPriority, 0.1);
        
        // Test external ID lookup
        Optional<EnhancedEntity> byExternalId = jpaEnhancedRepository.findByExternalId("EXT-001");
        assertTrue(byExternalId.isPresent());
        
        // Clean up
        jpaEnhancedRepository.deleteAll();
    }
} 