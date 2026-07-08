import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String[] CATEGORIES = {
        "Electronics", "Clothing", "Documents", "Jewelry", "Books", 
        "Keys", "Bags", "Sports Equipment", "Other"
    };
    
    public static void main(String[] args) {
        LostFoundManager manager = new LostFoundManager();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Welcome to Enhanced Lost & Found System ===");
        
        // Authentication loop
        while (manager.getCurrentUser() == null) {
            System.out.println("\n1. Login\n2. Register\n3. Exit");
            System.out.print("Choose option: ");
            int authChoice = scanner.nextInt();
            scanner.nextLine();
            
            switch (authChoice) {
                case 1:
                    login(manager, scanner);
                    break;
                case 2:
                    register(manager, scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    manager.close();
                    return;
            }
        }
        
        // Main application loop
        while (true) {
            System.out.println("\n=== Lost & Found System - Welcome " + manager.getCurrentUser().getName() + " ===");
            System.out.println("1. Report Lost Item");
            System.out.println("2. Report Found Item");
            System.out.println("3. View All Items");
            System.out.println("4. Search Items");
            System.out.println("5. Mark Item as Returned");
            System.out.println("6. Logout");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    reportItem(manager, scanner, "LOST");
                    break;
                case 2:
                    reportItem(manager, scanner, "FOUND");
                    break;
                case 3:
                    viewAllItems(manager);
                    break;
                case 4:
                    searchItems(manager, scanner);
                    break;
                case 5:
                    markAsReturned(manager, scanner);
                    break;
                case 6:
                    manager.logout();
                    main(args); // Restart
                    return;
                case 7:
                    System.out.println("Goodbye!");
                    manager.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private static void login(LostFoundManager manager, Scanner scanner) {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (manager.login(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials!");
        }
    }
    
    private static void register(LostFoundManager manager, Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        int userId = manager.registerUser(name, email, phone, password);
        if (userId > 0) {
            System.out.println("Registration successful! Please login.");
        } else {
            System.out.println("Registration failed!");
        }
    }
    
    private static void reportItem(LostFoundManager manager, Scanner scanner, String type) {
        System.out.print("Item name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        
        System.out.println("Categories:");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.println((i + 1) + ". " + CATEGORIES[i]);
        }
        System.out.print("Select category (1-" + CATEGORIES.length + "): ");
        int catChoice = scanner.nextInt();
        scanner.nextLine();
        String category = CATEGORIES[catChoice - 1];
        
        System.out.print("Location: ");
        String location = scanner.nextLine();
        
        Item item = new Item(0, name, description, category, location, type, manager.getCurrentUser());
        int itemId = manager.addItem(item);
        
        if (itemId > 0) {
            System.out.println(type + " item reported successfully with ID: " + itemId);
        } else {
            System.out.println("Failed to report item!");
        }
    }
    
    private static void viewAllItems(LostFoundManager manager) {
        List<Item> items = manager.getAllItems();
        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            System.out.println("\n=== All Items ===");
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }
    
    private static void searchItems(LostFoundManager manager, Scanner scanner) {
        System.out.print("Search keyword (or press Enter to skip): ");
        String keyword = scanner.nextLine();
        
        System.out.print("Category filter (or press Enter to skip): ");
        String category = scanner.nextLine();
        
        System.out.print("Type filter (LOST/FOUND or press Enter to skip): ");
        String type = scanner.nextLine();
        
        List<Item> results = manager.searchItems(
            keyword.isEmpty() ? null : keyword,
            category.isEmpty() ? null : category,
            type.isEmpty() ? null : type
        );
        
        if (results.isEmpty()) {
            System.out.println("No items found matching criteria.");
        } else {
            System.out.println("\n=== Search Results ===");
            for (Item item : results) {
                System.out.println(item);
            }
        }
    }
    
    private static void markAsReturned(LostFoundManager manager, Scanner scanner) {
        System.out.print("Enter Item ID to mark as returned: ");
        int itemId = scanner.nextInt();
        
        if (manager.markItemAsReturned(itemId)) {
            System.out.println("Item marked as returned successfully!");
        } else {
            System.out.println("Failed to mark item as returned!");
        }
    }
}