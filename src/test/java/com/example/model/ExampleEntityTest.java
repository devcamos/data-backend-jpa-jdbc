package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExampleEntityTest {
    @Test
    void testGettersAndSetters() {
        com.example.model.BaseEntity entity = new BaseEntity();
        entity.setId(1L);
        entity.setField1("Test Field 1");
        entity.setField2("Test Field 2");
        entity.setField3(123);
        
        assertEquals(1L, entity.getId());
        assertEquals("Test Field 1", entity.getField1());
        assertEquals("Test Field 2", entity.getField2());
        assertEquals(123, entity.getField3());
    }
}
