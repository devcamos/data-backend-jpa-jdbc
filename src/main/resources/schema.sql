-- Create example_entity table for BaseEntity
CREATE TABLE IF NOT EXISTS example_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    field1 VARCHAR(255),
    field2 VARCHAR(255),
    field3 INT
);

-- Create enhanced_entity table for EnhancedEntity
CREATE TABLE IF NOT EXISTS enhanced_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    priority INT,
    is_active BOOLEAN DEFAULT true,
    category VARCHAR(255),
    external_id VARCHAR(255)
); 