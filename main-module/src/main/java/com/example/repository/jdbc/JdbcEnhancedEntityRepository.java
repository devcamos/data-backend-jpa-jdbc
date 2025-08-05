package com.example.repository.jdbc;

import com.example.model.EnhancedEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "data-access.jdbc.enabled", havingValue = "true")
public class JdbcEnhancedEntityRepository {
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<EnhancedEntity> rowMapper = (rs, rowNum) -> {
        EnhancedEntity entity = new EnhancedEntity();
        entity.setId(rs.getLong("id"));
        entity.setName(rs.getString("name"));
        entity.setEmail(rs.getString("email"));
        entity.setDescription(rs.getString("description"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            entity.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            entity.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        entity.setPriority(rs.getInt("priority"));
        entity.setIsActive(rs.getBoolean("is_active"));
        entity.setCategory(rs.getString("category"));
        entity.setExternalId(rs.getString("external_id"));
        
        return entity;
    };

    public JdbcEnhancedEntityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EnhancedEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity", rowMapper);
    }

    public Optional<EnhancedEntity> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity WHERE id = ?", rowMapper, id)
                .stream()
                .findFirst();
    }

    public List<EnhancedEntity> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity WHERE name LIKE ?", rowMapper, "%" + name + "%");
    }

    public List<EnhancedEntity> findByCategory(String category) {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity WHERE category = ?", rowMapper, category);
    }

    public List<EnhancedEntity> findByIsActive(Boolean isActive) {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity WHERE is_active = ?", rowMapper, isActive);
    }

    public Optional<EnhancedEntity> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity WHERE email = ?", rowMapper, email)
                .stream()
                .findFirst();
    }

    public List<EnhancedEntity> findByPriorityGreaterThan(Integer priority) {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity WHERE priority > ?", rowMapper, priority);
    }

    public EnhancedEntity save(EnhancedEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        
        if (entity.getId() == null) {
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO enhanced_entity (name, email, description, created_at, updated_at, priority, is_active, category, external_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getEmail());
                ps.setString(3, entity.getDescription());
                ps.setTimestamp(4, Timestamp.valueOf(entity.getCreatedAt()));
                ps.setTimestamp(5, Timestamp.valueOf(entity.getUpdatedAt()));
                ps.setInt(6, entity.getPriority());
                ps.setBoolean(7, entity.getIsActive());
                ps.setString(8, entity.getCategory());
                ps.setString(9, entity.getExternalId());
                return ps;
            }, keyHolder);
            
            Long generatedId = keyHolder.getKey().longValue();
            entity.setId(generatedId);
        } else {
            entity.setUpdatedAt(now);
            
            jdbcTemplate.update(
                "UPDATE enhanced_entity SET name = ?, email = ?, description = ?, updated_at = ?, priority = ?, is_active = ?, category = ?, external_id = ? WHERE id = ?",
                entity.getName(), entity.getEmail(), entity.getDescription(),
                Timestamp.valueOf(entity.getUpdatedAt()), entity.getPriority(),
                entity.getIsActive(), entity.getCategory(), entity.getExternalId(), entity.getId()
            );
        }
        return entity;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM enhanced_entity WHERE id = ?", id);
    }

    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM enhanced_entity", Long.class);
    }

    public boolean existsById(Long id) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM enhanced_entity WHERE id = ?", Long.class, id) > 0;
    }

    public long countByCategory(String category) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM enhanced_entity WHERE category = ?", Long.class, category);
    }

    public List<EnhancedEntity> findActiveByCategory(String category) {
        return jdbcTemplate.query("SELECT * FROM enhanced_entity WHERE category = ? AND is_active = true", rowMapper, category);
    }
} 