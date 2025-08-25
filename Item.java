// Item.java
public class Item {
    protected String itemName;
    protected String description;
    protected String status; // Lost, Found, Returned

    public Item(String itemName, String description, String status) {
        this.itemName = itemName;
        this.description = description;
        this.status = status;
    }

    public String getItemName() { return itemName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void displayDetails() {
        System.out.println("Item: " + itemName + " | Description: " + description + " | Status: " + status);
    }
}
