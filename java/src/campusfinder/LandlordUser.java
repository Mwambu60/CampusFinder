package campusfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Landlord account: inherits shared identity from User and manages listings. */
public class LandlordUser extends User {
    private final String phone;
    private final List<Listing> listings = new ArrayList<>();

    public LandlordUser(String name, String phone) {
        super(name);
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Landlord phone cannot be empty.");
        }
        this.phone = phone.trim();
    }

    public String getPhone() { return phone; }

    @Override
    public String getRole() { return "Landlord"; }

    @Override
    public String getContactInfo() { return phone; }

    public void addListing(Listing listing) {
        if (listing == null) throw new IllegalArgumentException("Listing cannot be null.");
        listings.add(listing);
    }

    /** Returns a read-only view so callers cannot alter the landlord's private list. */
    public List<Listing> getListings() {
        return Collections.unmodifiableList(listings);
    }
}
