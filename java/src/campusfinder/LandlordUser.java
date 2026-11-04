package campusfinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/** Landlord account model that tracks properties submitted by that landlord. */
public class LandlordUser {
    private final String name,phone; private final List<Listing> listings=new ArrayList<>();
    public LandlordUser(String name,String phone){this.name=name;this.phone=phone;}
    public String getName(){return name;} public String getPhone(){return phone;}
    public void addListing(Listing listing){listings.add(listing);}
    public List<Listing> getListings(){return Collections.unmodifiableList(listings);}
}