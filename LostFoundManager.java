// LostFoundManager.java
import java.util.ArrayList;
import java.util.List;

public class LostFoundManager {
    private List<LostItem> lostItems = new ArrayList<>();
    private List<FoundItem> foundItems = new ArrayList<>();

    public void reportLostItem(String name, String desc) {
        lostItems.add(new LostItem(name, desc));
        System.out.println("Lost item reported successfully!");
    }

    public void reportFoundItem(String name, String desc) {
        foundItems.add(new FoundItem(name, desc));
        System.out.println("Found item reported successfully!");
    }

    public void showLostItems() {
        if (lostItems.isEmpty()) {
            System.out.println("No lost items reported yet.");
        } else {
            System.out.println("\n--- Lost Items ---");
            for (LostItem item : lostItems) {
                item.displayDetails();
            }
        }
    }

    public void showFoundItems() {
        if (foundItems.isEmpty()) {
            System.out.println("No found items reported yet.");
        } else {
            System.out.println("\n--- Found Items ---");
            for (FoundItem item : foundItems) {
                item.displayDetails();
            }
        }
    }

    public void searchItem(String name) {
        boolean found = false;
        for (LostItem item : lostItems) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                System.out.println("Match in Lost Items:");
                item.displayDetails();
                found = true;
            }
        }
        for (FoundItem item : foundItems) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                System.out.println("Match in Found Items:");
                item.displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items matched your search.");
        }
    }
}
