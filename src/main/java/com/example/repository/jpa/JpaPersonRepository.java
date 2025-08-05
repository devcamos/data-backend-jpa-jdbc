package com.example.repository.jpa;

import com.example.model.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "data-access.jpa.enabled", havingValue = "true")
public interface JpaPersonRepository extends JpaRepository<Person, Long> {
} 