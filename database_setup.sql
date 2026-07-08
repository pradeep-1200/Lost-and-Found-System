-- MySQL Database Setup for Lost & Found System
-- Run this script to create the database and tables

CREATE DATABASE IF NOT EXISTS lost_found_db;
USE lost_found_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create items table
CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    type VARCHAR(10) NOT NULL, -- 'LOST' or 'FOUND'
    date_reported TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    matched_item_id INT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_type (type),
    INDEX idx_category (category),
    INDEX idx_status (status)
);

-- Insert sample data for testing
INSERT INTO users (name, email, phone, password) VALUES 
('John Doe', 'john@example.com', '123-456-7890', 'password123'),
('Jane Smith', 'jane@example.com', '098-765-4321', 'password456');

INSERT INTO items (name, description, category, location, type, user_id) VALUES 
('iPhone 13', 'Black iPhone 13 with blue case', 'Electronics', 'Library 2nd Floor', 'LOST', 1),
('Blue Backpack', 'Nike blue backpack with laptop inside', 'Bags', 'Cafeteria', 'FOUND', 2),
('Car Keys', 'Toyota keys with red keychain', 'Keys', 'Parking Lot A', 'LOST', 1);