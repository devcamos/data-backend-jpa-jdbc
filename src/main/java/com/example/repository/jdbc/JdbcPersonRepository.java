package com.example.repository.jdbc;

import com.example.model.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "data-access.jdbc.enabled", havingValue = "true")
public class JdbcPersonRepository {
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Person> rowMapper = (rs, rowNum) -> {
        Person person = new Person();
        person.setId(rs.getLong("id"));
        person.setName(rs.getString("name"));
        person.setEmail(rs.getString("email"));
        person.setAge(rs.getInt("age"));
        person.setDepartment(rs.getString("department"));
        return person;
    };

    public JdbcPersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person", rowMapper);
    }

    public Optional<Person> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", rowMapper, id)
                .stream()
                .findFirst();
    }

    public List<Person> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name LIKE ?", rowMapper, "%" + name + "%");
    }

    public List<Person> findByDepartment(String department) {
        return jdbcTemplate.query("SELECT * FROM person WHERE department = ?", rowMapper, department);
    }

    public Person save(Person person) {
        if (person.getId() == null) {
            jdbcTemplate.update(
                "INSERT INTO person (name, email, age, department) VALUES (?, ?, ?, ?)",
                person.getName(), person.getEmail(), person.getAge(), person.getDepartment()
            );
        } else {
            jdbcTemplate.update(
                "UPDATE person SET name = ?, email = ?, age = ?, department = ? WHERE id = ?",
                person.getName(), person.getEmail(), person.getAge(), person.getDepartment(), person.getId()
            );
        }
        return person;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM person", Long.class);
    }

    public boolean existsById(Long id) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM person WHERE id = ?", Long.class, id) > 0;
    }
} 