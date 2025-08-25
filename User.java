// User.java
public class User {
    private String name;
    private String contact;

    public User(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }

    public void displayDetails() {
        System.out.println("Name: " + name + ", Contact: " + contact);
    }
}
