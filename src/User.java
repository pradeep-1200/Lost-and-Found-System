import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String contact;

    public User(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return name + " (" + contact + ")";
    }
}
