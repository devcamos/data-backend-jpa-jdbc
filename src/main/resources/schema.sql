-- Create example_entity table for BaseEntity
CREATE TABLE IF NOT EXISTS example_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    field1 VARCHAR(255),
    field2 VARCHAR(255),
    field3 INT
);

-- Create person table for Person entity
CREATE TABLE IF NOT EXISTS person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    age INT,
    department VARCHAR(255)
); 