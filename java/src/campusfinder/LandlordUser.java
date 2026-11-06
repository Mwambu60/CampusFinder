package campusfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Landlord account model that tracks properties submitted by that landlord. */
public class LandlordUser extends User {                    // ← extends User
    private final String phone;
    private final List<Listing> listings = new ArrayList<>();

    public LandlordUser(String name, String phone) {
        super(name);                                        // ← call parent constructor
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void addListing(Listing listing) {
        listings.add(listing);
    }

    public List<Listing> getListings() {
        return Collections.unmodifiableList(listings);
    }

    // ===== Polymorphism happens here =====
    @Override
    public String getRole() {
        return "Landlord";
    }

    @Override
    public void displayInfo() {
        System.out.println("Landlord Name : " + getName());
        System.out.println("Phone         : " + phone);
        System.out.println("Listings      : " + listings.size());
    }
}

/**package campusfinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;*/
/** Landlord account model that tracks properties submitted by that landlord. */
/**public class LandlordUser {
    private final String name,phone; private final List<Listing> listings=new ArrayList<>();
    public LandlordUser(String name,String phone){this.name=name;this.phone=phone;}
    public String getName(){return name;} public String getPhone(){return phone;}
    public void addListing(Listing listing){listings.add(listing);}
    public List<Listing> getListings(){return Collections.unmodifiableList(listings);}
} */
