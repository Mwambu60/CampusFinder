package campusfinder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/** Student account model with an in-memory list of saved property IDs. */
public class StudentUser {
    private final String name,email; private final Set<Integer> savedListingIds=new HashSet<>();
    public StudentUser(String name,String email){this.name=name;this.email=email;}
    public String getName(){return name;} public String getEmail(){return email;}
    public void saveListing(int id){savedListingIds.add(id);}
    public void removeSavedListing(int id){savedListingIds.remove(id);}
    public Set<Integer> getSavedListingIds(){return Collections.unmodifiableSet(savedListingIds);}
}