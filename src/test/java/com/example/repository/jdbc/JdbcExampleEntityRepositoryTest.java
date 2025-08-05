package com.example.repository.jdbc;

import com.example.model.BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcExampleEntityRepository.class)
class JdbcExampleEntityRepositoryTest {
    @Autowired
    private JdbcExampleEntityRepository repository;

    @Test
    void testRepositoryNotNull() {
        assertNotNull(repository);
    }
    // Add more tests for save, findById, etc. as needed
}
