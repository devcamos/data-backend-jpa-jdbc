package com.example.repository;

import com.example.model.BaseEntity;
import com.example.model.Person;
import com.example.repository.jdbc.JdbcExampleEntityRepository;
import com.example.repository.jdbc.JdbcPersonRepository;
import com.example.repository.jpa.JpaPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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
    private JdbcPersonRepository jdbcPersonRepository;
    
    @Autowired(required = false)
    private JpaPersonRepository jpaPersonRepository;
    
    @Test
    void testJdbcBaseEntityCrud() {
        if (jdbcExampleRepository == null) {
            System.out.println("JDBC repository not enabled, skipping test");
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
    void testJdbcPersonCrud() {
        if (jdbcPersonRepository == null) {
            System.out.println("JDBC Person repository not enabled, skipping test");
            return;
        }
        
        // Create
        Person person = Person.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .age(30)
            .department("Engineering")
            .build();
        
        Person saved = jdbcPersonRepository.save(person);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        
        // Read
        Optional<Person> found = jdbcPersonRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getName());
        
        // Test custom queries
        List<Person> byName = jdbcPersonRepository.findByName("John");
        assertFalse(byName.isEmpty());
        
        List<Person> byDepartment = jdbcPersonRepository.findByDepartment("Engineering");
        assertFalse(byDepartment.isEmpty());
        
        // Update
        saved.setAge(31);
        Person updated = jdbcPersonRepository.save(saved);
        assertEquals(31, updated.getAge());
        
        // Test count and exists
        assertTrue(jdbcPersonRepository.existsById(saved.getId()));
        assertTrue(jdbcPersonRepository.count() > 0);
        
        // Delete
        jdbcPersonRepository.deleteById(saved.getId());
        Optional<Person> deleted = jdbcPersonRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }
    
    @Test
    void testJpaPersonCrud() {
        if (jpaPersonRepository == null) {
            System.out.println("JPA repository not enabled, skipping test");
            return;
        }
        
        // Create multiple persons
        Person person1 = Person.builder()
            .name("Alice Johnson")
            .email("alice.johnson@example.com")
            .age(25)
            .department("Marketing")
            .build();
        
        Person person2 = Person.builder()
            .name("Bob Smith")
            .email("bob.smith@example.com")
            .age(35)
            .department("Engineering")
            .build();
        
        Person person3 = Person.builder()
            .name("Carol Davis")
            .email("carol.davis@example.com")
            .age(28)
            .department("Marketing")
            .build();
        
        jpaPersonRepository.save(person1);
        jpaPersonRepository.save(person2);
        jpaPersonRepository.save(person3);
        
        // Test custom query methods
        List<Person> byName = jpaPersonRepository.findByNameContainingIgnoreCase("alice");
        assertFalse(byName.isEmpty());
        
        List<Person> byDepartment = jpaPersonRepository.findByDepartment("Marketing");
        assertEquals(2, byDepartment.size());
        
        List<Person> byAgeRange = jpaPersonRepository.findByAgeBetween(25, 30);
        assertEquals(2, byAgeRange.size());
        
        List<Person> byDepartmentAndAge = jpaPersonRepository.findByDepartmentAndAgeGreaterThan("Engineering", 30);
        assertEquals(1, byDepartmentAndAge.size());
        
        List<Person> byEmail = jpaPersonRepository.findByEmailContaining("example.com");
        assertEquals(3, byEmail.size());
        
        // Test aggregation queries
        long marketingCount = jpaPersonRepository.countByDepartment("Marketing");
        assertEquals(2, marketingCount);
        
        Double avgAge = jpaPersonRepository.getAverageAgeByDepartment("Marketing");
        assertNotNull(avgAge);
        assertEquals(26.5, avgAge, 0.1);
        
        // Clean up
        jpaPersonRepository.deleteAll();
    }
} 