package campusfinder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Student account model with an in-memory list of saved property IDs. */
public class StudentUser extends User {                     // ← extends User
    private final String email;
    private final Set<Integer> savedListingIds = new HashSet<>();

    public StudentUser(String name, String email) {
        super(name);                                        // ← call parent constructor
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public void saveListing(int id) {
        savedListingIds.add(id);
    }
    public void removeSavedListing(int id) {
        savedListingIds.remove(id);
    }

    public Set<Integer> getSavedListingIds() {
        return Collections.unmodifiableSet(savedListingIds);
    }

    // ===== Polymorphism happens here =====
    @Override
    public String getRole() {
        return "Student";
    }
    @Override
    public void displayInfo() {
        System.out.println("Student Name : " + getName());
        System.out.println("Email        : " + email);
        System.out.println("Saved Homes  : " + savedListingIds.size());
    }
}
