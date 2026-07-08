import java.io.Serializable;
import java.time.LocalDateTime;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String name;
    protected String description;
    protected String category;
    protected String location;
    protected String status;
    protected String type; // LOST or FOUND
    protected LocalDateTime dateReported;
    protected User reportedBy;

    public Item(int id, String name, String description, String category, String location, String type, User reportedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.location = location;
        this.type = type;
        this.reportedBy = reportedBy;
        this.status = "ACTIVE";
        this.dateReported = LocalDateTime.now();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public LocalDateTime getDateReported() { return dateReported; }
    public User getReportedBy() { return reportedBy; }
    
    public void setStatus(String status) { this.status = status; }
    public void markAsReturned() { this.status = "RETURNED"; }

    @Override
    public String toString() {
        return String.format("[%s] ID: %d | %s | %s | %s | %s | Status: %s | By: %s",
                type, id, name, category, description, location, status, reportedBy.getName());
    }
}
