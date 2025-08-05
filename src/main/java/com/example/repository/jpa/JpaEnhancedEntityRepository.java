package com.example.repository.jpa;

import com.example.model.EnhancedEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "data-access.jpa.enabled", havingValue = "true")
public interface JpaEnhancedEntityRepository extends JpaRepository<EnhancedEntity, Long> {
    
    // Basic query methods
    List<EnhancedEntity> findByNameContainingIgnoreCase(String name);
    
    Optional<EnhancedEntity> findByEmail(String email);
    
    List<EnhancedEntity> findByCategory(String category);
    
    List<EnhancedEntity> findByIsActive(Boolean isActive);
    
    List<EnhancedEntity> findByPriorityGreaterThan(Integer priority);
    
    // Combined query methods
    List<EnhancedEntity> findByCategoryAndIsActive(String category, Boolean isActive);
    
    List<EnhancedEntity> findByPriorityBetween(Integer minPriority, Integer maxPriority);
    
    List<EnhancedEntity> findByCreatedAtAfter(LocalDateTime date);
    
    List<EnhancedEntity> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Custom queries
    @Query("SELECT e FROM EnhancedEntity e WHERE e.name LIKE %:name% AND e.isActive = true")
    List<EnhancedEntity> findActiveByNameContaining(@Param("name") String name);
    
    @Query("SELECT e FROM EnhancedEntity e WHERE e.category = :category AND e.priority >= :minPriority")
    List<EnhancedEntity> findByCategoryAndPriorityGreaterThanEqual(@Param("category") String category, @Param("minPriority") Integer minPriority);
    
    @Query("SELECT COUNT(e) FROM EnhancedEntity e WHERE e.category = :category")
    long countByCategory(@Param("category") String category);
    
    @Query("SELECT AVG(e.priority) FROM EnhancedEntity e WHERE e.category = :category")
    Double getAveragePriorityByCategory(@Param("category") String category);
    
    @Query("SELECT e.category, COUNT(e) FROM EnhancedEntity e GROUP BY e.category")
    List<Object[]> getCategoryCounts();
    
    @Query("SELECT e FROM EnhancedEntity e WHERE e.externalId = :externalId")
    Optional<EnhancedEntity> findByExternalId(@Param("externalId") String externalId);
    
    @Query("SELECT e FROM EnhancedEntity e WHERE e.description IS NOT NULL AND LENGTH(e.description) > :minLength")
    List<EnhancedEntity> findByDescriptionLengthGreaterThan(@Param("minLength") Integer minLength);
} 