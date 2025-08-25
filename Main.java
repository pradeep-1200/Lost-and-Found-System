// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LostFoundManager manager = new LostFoundManager();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Lost & Found Item Management System ---");
            System.out.println("1. Report Lost Item");
            System.out.println("2. Report Found Item");
            System.out.println("3. View All Lost Items");
            System.out.println("4. View All Found Items");
            System.out.println("5. Search Item by Name");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String lostName = sc.nextLine();
                    System.out.print("Enter description: ");
                    String lostDesc = sc.nextLine();
                    manager.reportLostItem(lostName, lostDesc);
                    break;

                case 2:
                    System.out.print("Enter item name: ");
                    String foundName = sc.nextLine();
                    System.out.print("Enter description: ");
                    String foundDesc = sc.nextLine();
                    manager.reportFoundItem(foundName, foundDesc);
                    break;

                case 3:
                    manager.showLostItems();
                    break;

                case 4:
                    manager.showFoundItems();
                    break;

                case 5:
                    System.out.print("Enter item name to search: ");
                    String searchName = sc.nextLine();
                    manager.searchItem(searchName);
                    break;

                case 6:
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 6);
        sc.close();
    }
}
