package campusfinder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
/** Minimal Java HTTP API. GET /api/listings; POST /api/listings; GET /api/health. */
public class CampusFinderServer {
    private static final List<Listing> listings=new ArrayList<>();
    private static final List<LandlordUser> landlords=new ArrayList<>();
    private static int nextId=1;
    public static void main(String[] args)throws IOException{
        seed();
        HttpServer server=HttpServer.create(new InetSocketAddress(8080),0);
        server.createContext("/api/health",e->send(e,200,"{\"status\":\"ok\"}"));
        server.createContext("/api/listings",CampusFinderServer::handleListings);
        server.setExecutor(Executors.newCachedThreadPool());server.start();
        System.out.println("CampusFinder API: http://localhost:8080  (Ctrl+C to stop)");
    }
    private static void seed(){
        add("Bright room near UNZA","Lusaka · UNZA","Room",1800,"Neat student room with shared kitchen, water and electricity access.","Martha Banda","0977000101");
        add("Chilenje student bedsitter","Lusaka · Chilenje","Bedsitter",2800,"Self-contained bedsitter with private bathroom, secure yard and nearby bus routes.","Peter Zulu","0977000102");
        add("Shared house near CBU","Kitwe · Riverside · CBU","Shared house",1500,"Affordable furnished room in a shared student house with kitchen and lounge.","Grace Phiri","0977000103");
        add("Modern apartment in Northrise","Ndola · Northrise","Apartment",5200,"One-bedroom apartment with fitted kitchen and secure parking.","Daniel Mwansa","0977000104");
        add("Budget room near Mulungushi","Lusaka · Kabwe Road","Room",1200,"Simple, clean student room with shared bathroom and transport access.","Ruth Tembo","0977000105");
        add("Furnished bedsitter in Roma","Lusaka · Roma · UNZA","Bedsitter",4200,"Furnished private unit with kitchenette and 24-hour security.","Brian Chanda","0977000106");
        add("Student apartment in Kitwe","Kitwe · Parklands · CBU","Apartment",3900,"Comfortable apartment close to shops and public transport.","Lydia Kalaba","0977000107");
        add("Shared student home in Kabulonga","Lusaka · Kabulonga","Shared house",2300,"Quiet shared home with furnished common areas and secure access.","Michael Phiri","0977000108");
        add("Affordable room in Ndola","Ndola · Kansenshi","Room",1600,"Private lockable room with shared kitchen and bathroom.","Esther Musonda","0977000109");
    }
    private static void add(String t,String l,String type,int p,String d,String n,String phone){listings.add(new Listing(nextId++,t,l,type,p,d,n,phone));}
    private static void handleListings(HttpExchange e)throws IOException{
        cors(e);
        if(e.getRequestMethod().equalsIgnoreCase("OPTIONS")){e.sendResponseHeaders(204,-1);return;}
        if(e.getRequestMethod().equalsIgnoreCase("GET")){
            Map<String,String> q=parse(e.getRequestURI().getRawQuery());
            List<Listing> found=new ListingFilter(q.get("location"),number(q.get("minPrice")),number(q.get("maxPrice")),q.get("type")).apply(listings);
            StringJoiner json=new StringJoiner(",","[","]");found.forEach(x->json.add(x.toJson()));send(e,200,json.toString());return;
        }
        if(e.getRequestMethod().equalsIgnoreCase("POST")){
            Map<String,String> f=parse(new String(e.getRequestBody().readAllBytes(),StandardCharsets.UTF_8));
            String name=f.getOrDefault("landlordName","").trim(),title=f.getOrDefault("title","").trim(),loc=f.getOrDefault("location","").trim(),desc=f.getOrDefault("description","").trim(),phone=f.getOrDefault("phone","").trim(),type=f.getOrDefault("type","Room");
            int price=number(f.get("price"));
            if(name.isEmpty()||title.isEmpty()||loc.isEmpty()||desc.isEmpty()||phone.isEmpty()||price<1){send(e,400,"{\"error\":\"Complete all fields and enter a valid monthly rent.\"}");return;}
            synchronized(CampusFinderServer.class){LandlordUser landlord=new LandlordUser(name,phone);Listing item=new Listing(nextId++,title,loc,type,price,desc,name,phone);landlord.addListing(item);landlords.add(landlord);listings.add(item);send(e,201,item.toJson());}return;
        }
        send(e,405,"{\"error\":\"Method not allowed\"}");
    }
    private static Map<String,String> parse(String raw){
        Map<String,String> m=new HashMap<>();if(raw==null)return m;
        for(String pair:raw.split("&")){String[] p=pair.split("=",2);m.put(decode(p[0]),p.length>1?decode(p[1]):"");}return m;
    }
    private static String decode(String s){return URLDecoder.decode(s,StandardCharsets.UTF_8);}
    private static int number(String s){try{return s==null||s.isBlank()?0:Integer.parseInt(s);}catch(NumberFormatException ex){return 0;}}
    private static void cors(HttpExchange e){e.getResponseHeaders().set("Access-Control-Allow-Origin","*");e.getResponseHeaders().set("Access-Control-Allow-Methods","GET, POST, OPTIONS");e.getResponseHeaders().set("Access-Control-Allow-Headers","Content-Type");}
    private static void send(HttpExchange e,int status,String body)throws IOException{cors(e);byte[] b=body.getBytes(StandardCharsets.UTF_8);e.getResponseHeaders().set("Content-Type","application/json; charset=UTF-8");e.sendResponseHeaders(status,b.length);e.getResponseBody().write(b);e.close();}
}