import java.io.Serializable;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String name;
    protected String description;
    protected String location;
    protected boolean isReturned;
    protected User reportedBy;

    public Item(int id, String name, String description, String location, User reportedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.reportedBy = reportedBy;
        this.isReturned = false;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public boolean isReturned() { return isReturned; }
    public User getReportedBy() { return reportedBy; }

    public void markAsReturned() { this.isReturned = true; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Description: %s | Location: %s | Returned: %s | Reported By: %s",
                id, name, description, location, (isReturned ? "Yes" : "No"), reportedBy);
    }
}
