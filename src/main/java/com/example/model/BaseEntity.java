package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ExampleEntity is a generic entity template.
 * Extend or modify this class to create more complex entities
 * for demonstrating various result set processing patterns.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@lombok.Builder
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Example generic fields
    private String field1;
    private String field2;
    private Integer field3;

    // TODO: Add more fields and relationships as needed to highlight processing patterns
}
