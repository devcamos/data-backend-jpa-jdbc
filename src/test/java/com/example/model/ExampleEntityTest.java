package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExampleEntityTest {
    @Test
    void testGettersAndSetters() {
        com.example.model.BaseEntity entity = new BaseEntity();
        entity.setId(1L);
        entity.setName("Test Name");
        assertEquals(1L, entity.getId());
        assertEquals("Test Name", entity.getName());
    }
}
