import java.util.List;

public class LostFoundManager {
    private DatabaseManager dbManager;
    private User currentUser;

    public LostFoundManager() {
        this.dbManager = new DatabaseManager();
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public int registerUser(String name, String email, String phone, String password) {
        return dbManager.registerUser(name, email, phone, password);
    }
    
    public boolean login(String email, String password) {
        User user = dbManager.loginUser(email, password);
        if (user != null) {
            this.currentUser = user;
            return true;
        }
        return false;
    }
    
    public void logout() {
        this.currentUser = null;
    }

    public int addItem(Item item) {
        int itemId = dbManager.addItem(item);
        if (itemId > 0) {
            // Find potential matches
            List<Item> matches = dbManager.findPotentialMatches(item);
            if (!matches.isEmpty()) {
                System.out.println("\n*** POTENTIAL MATCHES FOUND ***");
                for (Item match : matches) {
                    System.out.println(match);
                }
                System.out.println("Contact the owners if any item matches!");
            }
        }
        return itemId;
    }

    public List<Item> getAllItems() {
        return dbManager.getAllItems();
    }

    public List<Item> searchItems(String keyword, String category, String type) {
        return dbManager.searchItems(keyword, category, type);
    }

    public boolean markItemAsReturned(int id) {
        return dbManager.markItemAsReturned(id);
    }
    
    public void close() {
        dbManager.close();
    }
}