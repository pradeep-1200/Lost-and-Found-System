import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LostFoundManager manager = new LostFoundManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Lost and Found System ---");
            System.out.println("1. Report Lost Item");
            System.out.println("2. Report Found Item");
            System.out.println("3. View All Items");
            System.out.println("4. Search Item by Name");
            System.out.println("5. Mark Item as Returned");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                case 2:
                    System.out.print("Enter your name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter your contact: ");
                    String userContact = scanner.nextLine();
                    User user = new User(userName, userContact);

                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter location: ");
                    String location = scanner.nextLine();

                    int id = manager.generateId();
                    Item item = new Item(id, name, description, location, user) {};
                    manager.addItem(item);

                    System.out.println("Item reported successfully with ID: " + id);
                    break;

                case 3:
                    List<Item> allItems = manager.getAllItems();
                    if (allItems.isEmpty()) {
                        System.out.println("No items reported.");
                    } else {
                        for (Item i : allItems) {
                            System.out.println(i);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter item name to search: ");
                    String searchName = scanner.nextLine();
                    List<Item> results = manager.searchByName(searchName);
                    if (results.isEmpty()) {
                        System.out.println("No items found with that name.");
                    } else {
                        for (Item i : results) {
                            System.out.println(i);
                        }
                    }
                    break;

                case 5:
                    System.out.print("Enter Item ID to mark as returned: ");
                    int returnId = scanner.nextInt();
                    manager.markItemAsReturned(returnId);
                    break;

                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
