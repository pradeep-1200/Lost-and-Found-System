# Enhanced Lost & Found Item Management System

## Overview
A comprehensive console-based application to report, search, and manage lost and found items with MySQL database integration and advanced features.

---

## New Features (Version 2.0)
- **User Authentication** - Register and login system
- **Item Categories** - Electronics, Clothing, Documents, Jewelry, Books, Keys, Bags, Sports Equipment, Other
- **Advanced Search** - Search by keyword, category, and type
- **Automatic Matching** - System suggests potential matches when items are reported
- **Status Tracking** - Track item status (Active, Returned)
- **MySQL Database** - Persistent data storage
- **Enhanced UI** - Better console interface with organized menus

---

## Database Features
- User management with secure authentication
- Item tracking with timestamps
- Relationship mapping between users and items
- Efficient search with database indexing
- Data persistence and reliability

---

## Prerequisites
1. **Java 11 or higher**
2. **MySQL Server** (8.0 or higher recommended)
3. **Maven** (for dependency management)

---

## Setup Instructions

### 1. Database Setup
1. Install MySQL Server
2. Create database and tables:
   ```sql
   mysql -u root -p < database_setup.sql
   ```
3. Update database credentials in `DatabaseManager.java`:
   ```java
   private static final String USERNAME = "your_username";
   private static final String PASSWORD = "your_password";
   ```

### 2. Application Setup
1. Clone or download this repository
2. Navigate to project directory
3. Install dependencies:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn exec:java
   ```

### Alternative (without Maven):
1. Download MySQL Connector JAR
2. Compile with classpath:
   ```bash
   javac -cp "mysql-connector-java-8.0.33.jar" src/*.java
   ```
3. Run with classpath:
   ```bash
   java -cp ".:mysql-connector-java-8.0.33.jar:src" Main
   ```

---

## How to Use

### First Time Setup
1. Run the application
2. Choose "Register" to create a new account
3. Login with your credentials

### Reporting Items
1. Select "Report Lost Item" or "Report Found Item"
2. Fill in item details including category
3. System will automatically suggest potential matches

### Searching Items
1. Use "Search Items" for advanced filtering
2. Search by keyword, category, or type (Lost/Found)
3. View detailed results with contact information

### Managing Items
1. Mark items as returned when reunited
2. View all active items in the system
3. Track your reported items

---

## System Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│      Main       │───▶│ LostFoundManager │───▶│ DatabaseManager │
│   (Console UI)  │    │   (Business)     │    │   (Data Layer)  │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │                        │
                                ▼                        ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │ Item & User     │    │  MySQL Database │
                       │   (Models)      │    │   (Storage)     │
                       └─────────────────┘    └─────────────────┘
```

---

## Database Schema

### Users Table
- `id` (Primary Key)
- `name`, `email`, `phone`
- `password`, `created_at`

### Items Table
- `id` (Primary Key)
- `name`, `description`, `category`
- `location`, `status`, `type`
- `date_reported`, `user_id` (Foreign Key)

---

## Future Enhancements
- Web-based GUI using Spring Boot
- Email notifications for matches
- Image upload for items
- Advanced matching algorithms
- Mobile app integration
- Admin dashboard
- Reporting and analytics

---

## Author
**Kalis** - Enhanced Version 2.0

---

## License
This project is open source and available under the MIT License.