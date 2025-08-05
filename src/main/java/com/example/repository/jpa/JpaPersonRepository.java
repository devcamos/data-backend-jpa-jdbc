package com.example.repository.jpa;

import com.example.model.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ConditionalOnProperty(name = "data-access.jpa.enabled", havingValue = "true")
public interface JpaPersonRepository extends JpaRepository<Person, Long> {
    
    // Custom query methods
    List<Person> findByNameContainingIgnoreCase(String name);
    
    List<Person> findByDepartment(String department);
    
    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);
    
    List<Person> findByDepartmentAndAgeGreaterThan(String department, Integer age);
    
    @Query("SELECT p FROM Person p WHERE p.email LIKE %:email%")
    List<Person> findByEmailContaining(@Param("email") String email);
    
    @Query("SELECT COUNT(p) FROM Person p WHERE p.department = :department")
    long countByDepartment(@Param("department") String department);
    
    @Query("SELECT AVG(p.age) FROM Person p WHERE p.department = :department")
    Double getAverageAgeByDepartment(@Param("department") String department);
} 