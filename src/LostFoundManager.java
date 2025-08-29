import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LostFoundManager {
    private static final String FILE_NAME =
            System.getProperty("user.dir") + File.separator + "data" + File.separator + "lost_found_data.ser";

    private List<Item> items;
    private int idCounter;

    public LostFoundManager() {
        this.items = new ArrayList<>();
        this.idCounter = 1;
        loadData();
    }

    public void addItem(Item item) {
        items.add(item);
        saveData();
    }

    public List<Item> getAllItems() {
        return items;
    }

    public List<Item> searchByName(String name) {
        List<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                results.add(item);
            }
        }
        return results;
    }

    public Optional<Item> getItemById(int id) {
        return items.stream().filter(i -> i.getId() == id).findFirst();
    }

    public void markItemAsReturned(int id) {
        Optional<Item> itemOpt = getItemById(id);
        if (itemOpt.isPresent()) {
            itemOpt.get().markAsReturned();
            saveData();
            System.out.println("Item marked as returned.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public int generateId() {
        return idCounter++;
    }

    private void saveData() {
        try {
            File folder = new File(System.getProperty("user.dir") + File.separator + "data");
            if (!folder.exists()) {
                folder.mkdir();
            }

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(items);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
                items = (List<Item>) ois.readObject();
                ois.close();
                if (!items.isEmpty()) {
                    idCounter = items.get(items.size() - 1).getId() + 1;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
