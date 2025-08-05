package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void testGettersAndSetters() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setAge(30);
        assertEquals(1L, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals(30, person.getAge());
    }
}
