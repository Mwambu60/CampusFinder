package campusfinder;
public class Listing {
    private final int id, price;
    private final String title, location, type, description, landlordName, phone;
    public Listing(int id,String title,String location,String type,int price,String description,String landlordName,String phone){
        this.id=id;this.title=title;this.location=location;this.type=type;this.price=price;this.description=description;this.landlordName=landlordName;this.phone=phone;
    }
    public int getId(){return id;} public int getPrice(){return price;}
    public String getTitle(){return title;} public String getLocation(){return location;} public String getType(){return type;}
    public String getDescription(){return description;} public String getLandlordName(){return landlordName;} public String getPhone(){return phone;}
    private String q(String s){return s==null?"":s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","");}
    public String toJson(){return "{\"id\":"+id+",\"title\":\""+q(title)+"\",\"location\":\""+q(location)+"\",\"type\":\""+q(type)+"\",\"price\":"+price+",\"description\":\""+q(description)+"\",\"landlordName\":\""+q(landlordName)+"\",\"phone\":\""+q(phone)+"\"}";}
}