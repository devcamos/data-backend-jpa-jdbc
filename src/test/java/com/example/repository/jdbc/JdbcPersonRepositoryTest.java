package com.example.repository.jdbc;

import com.example.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcPersonRepository.class)
class JdbcPersonRepositoryTest {
    @Autowired
    private JdbcPersonRepository repository;

    @Test
    void testRepositoryNotNull() {
        assertNotNull(repository);
    }
    // Add more tests for save, findById, etc. as needed
}
