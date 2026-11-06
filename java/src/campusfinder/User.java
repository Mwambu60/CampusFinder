package campusfinder;

/**
 * Common abstraction for every CampusFinder user.
 * Subclasses supply their own role and contact information.
 */
public abstract class User {
    private final String name;

    protected User(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name cannot be empty.");
        }
        this.name = name.trim();
    }

    // Encapsulation: the field is private and accessed through a getter.
    public final String getName() {
        return name;
    }

    // Abstraction: every concrete user type must define these behaviours.
    public abstract String getRole();
    public abstract String getContactInfo();

    public String getProfileSummary() {
        return getRole() + " | Name: " + getName() + " | Contact: " + getContactInfo();
    }
}
