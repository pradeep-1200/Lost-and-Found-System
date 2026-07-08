import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/lost_found_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "!)qp^vb91525";
    
    private Connection connection;
    
    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            createTables();
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
    
    private void createTables() {
        try {
            Statement stmt = connection.createStatement();
            
            // Create users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "phone VARCHAR(20), " +
                    "password VARCHAR(255) NOT NULL)");
            
            // Create items table
            stmt.execute("CREATE TABLE IF NOT EXISTS items (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "description TEXT, " +
                    "category VARCHAR(50), " +
                    "location VARCHAR(100), " +
                    "status VARCHAR(20) DEFAULT 'ACTIVE', " +
                    "type VARCHAR(10) NOT NULL, " +
                    "date_reported TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "user_id INT, " +
                    "matched_item_id INT NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id))");
            
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
    
    public int registerUser(String name, String email, String phone, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
        return -1;
    }
    
    public User loginUser(String email, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? AND password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), 
                              rs.getString("email"), rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return null;
    }
    
    public int addItem(Item item) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO items (name, description, category, location, type, user_id) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setString(3, item.getCategory());
            stmt.setString(4, item.getLocation());
            stmt.setString(5, item.getType());
            stmt.setInt(6, item.getReportedBy().getId());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
        return -1;
    }
    
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT i.*, u.name as user_name, u.email, u.phone " +
                    "FROM items i JOIN users u ON i.user_id = u.id " +
                    "WHERE i.status = 'ACTIVE' ORDER BY i.date_reported DESC");
            
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("user_name"), 
                                   rs.getString("email"), rs.getString("phone"));
                Item item = new Item(rs.getInt("id"), rs.getString("name"), 
                                   rs.getString("description"), rs.getString("category"),
                                   rs.getString("location"), rs.getString("type"), user);
                item.setStatus(rs.getString("status"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving items: " + e.getMessage());
        }
        return items;
    }
    
    public List<Item> searchItems(String keyword, String category, String type) {
        List<Item> items = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder(
                    "SELECT i.*, u.name as user_name, u.email, u.phone " +
                    "FROM items i JOIN users u ON i.user_id = u.id WHERE i.status = 'ACTIVE'");
            
            if (keyword != null && !keyword.isEmpty()) {
                query.append(" AND (i.name LIKE ? OR i.description LIKE ?)");
            }
            if (category != null && !category.isEmpty()) {
                query.append(" AND i.category = ?");
            }
            if (type != null && !type.isEmpty()) {
                query.append(" AND i.type = ?");
            }
            
            PreparedStatement stmt = connection.prepareStatement(query.toString());
            int paramIndex = 1;
            
            if (keyword != null && !keyword.isEmpty()) {
                String searchPattern = "%" + keyword + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }
            if (category != null && !category.isEmpty()) {
                stmt.setString(paramIndex++, category);
            }
            if (type != null && !type.isEmpty()) {
                stmt.setString(paramIndex++, type);
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("user_name"), 
                                   rs.getString("email"), rs.getString("phone"));
                Item item = new Item(rs.getInt("id"), rs.getString("name"), 
                                   rs.getString("description"), rs.getString("category"),
                                   rs.getString("location"), rs.getString("type"), user);
                item.setStatus(rs.getString("status"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error searching items: " + e.getMessage());
        }
        return items;
    }
    
    public boolean markItemAsReturned(int itemId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE items SET status = 'RETURNED' WHERE id = ?");
            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error marking item as returned: " + e.getMessage());
        }
        return false;
    }
    
    public List<Item> findPotentialMatches(Item item) {
        List<Item> matches = new ArrayList<>();
        try {
            String oppositeType = item.getType().equals("LOST") ? "FOUND" : "LOST";
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT i.*, u.name as user_name, u.email, u.phone " +
                    "FROM items i JOIN users u ON i.user_id = u.id " +
                    "WHERE i.type = ? AND i.category = ? AND i.status = 'ACTIVE' " +
                    "AND (i.name LIKE ? OR i.description LIKE ?) " +
                    "ORDER BY i.date_reported DESC LIMIT 5");
            
            stmt.setString(1, oppositeType);
            stmt.setString(2, item.getCategory());
            String searchPattern = "%" + item.getName() + "%";
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("user_name"), 
                                   rs.getString("email"), rs.getString("phone"));
                Item match = new Item(rs.getInt("id"), rs.getString("name"), 
                                    rs.getString("description"), rs.getString("category"),
                                    rs.getString("location"), rs.getString("type"), user);
                matches.add(match);
            }
        } catch (SQLException e) {
            System.out.println("Error finding matches: " + e.getMessage());
        }
        return matches;
    }
    
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}