package campusfinder;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
/** Filters accommodation by location, monthly rent range and property type. */
public class ListingFilter {
    private final String location,type; private final int minPrice,maxPrice;
    public ListingFilter(String location,int minPrice,int maxPrice,String type){
        this.location=location==null?"":location.trim().toLowerCase(Locale.ROOT);
        this.minPrice=Math.max(0,minPrice);this.maxPrice=maxPrice<=0?Integer.MAX_VALUE:maxPrice;
        this.type=type==null?"":type.trim().toLowerCase(Locale.ROOT);
    }
    public List<Listing> apply(List<Listing> listings){
        return listings.stream().filter(x ->
          (location.isEmpty()||x.getLocation().toLowerCase(Locale.ROOT).contains(location)||x.getTitle().toLowerCase(Locale.ROOT).contains(location))
          && x.getPrice()>=minPrice && x.getPrice()<=maxPrice
          && (type.isEmpty()||x.getType().toLowerCase(Locale.ROOT).equals(type))
        ).collect(Collectors.toList());
    }
}