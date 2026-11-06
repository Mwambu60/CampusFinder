package campusfinder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Student account: inherits shared identity from User and saves listing IDs. */
public class StudentUser extends User {
    private final String email;
    private final Set<Integer> savedListingIds = new HashSet<>();

    public StudentUser(String name, String email) {
        super(name);
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Student email cannot be empty.");
        }
        this.email = email.trim();
    }

    public String getEmail() { return email; }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public String getContactInfo() { return email; }

    public void saveListing(int id) { savedListingIds.add(id); }
    public void removeSavedListing(int id) { savedListingIds.remove(id); }

    /** Returns a read-only view so callers cannot alter the private saved-ID set. */
    public Set<Integer> getSavedListingIds() {
        return Collections.unmodifiableSet(savedListingIds);
    }
}
