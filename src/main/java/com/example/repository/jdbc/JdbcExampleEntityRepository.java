package com.example.repository.jdbc;

import com.example.model.BaseEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "data-access.jdbc.enabled", havingValue = "true")
public class JdbcExampleEntityRepository {
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<BaseEntity> rowMapper = (rs, rowNum) -> {
        BaseEntity entity = new BaseEntity();
        entity.setId(rs.getLong("id"));
        entity.setField1(rs.getString("field1"));
        entity.setField2(rs.getString("field2"));
        entity.setField3(rs.getInt("field3"));
        return entity;
    };

    public JdbcExampleEntityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BaseEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM example_entity", rowMapper);
    }

    public Optional<BaseEntity> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM example_entity WHERE id = ?", rowMapper, id)
                .stream()
                .findFirst();
    }

    public BaseEntity save(BaseEntity entity) {
        if (entity.getId() == null) {
            jdbcTemplate.update(
                "INSERT INTO example_entity (field1, field2, field3) VALUES (?, ?, ?)",
                entity.getField1(), entity.getField2(), entity.getField3()
            );
        } else {
            jdbcTemplate.update(
                "UPDATE example_entity SET field1 = ?, field2 = ?, field3 = ? WHERE id = ?",
                entity.getField1(), entity.getField2(), entity.getField3(), entity.getId()
            );
        }
        return entity;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM example_entity WHERE id = ?", id);
    }
}
