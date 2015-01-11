public class Node {
    
    public static Pro5_sookiamu M = new Pro5_sookiamu ();
    public static Graph G = new Graph ();
    private String name;
    private double latitude;
    private double longitude;
   
    //constructors
    public Node () {this.name = "null"; this.latitude = 0.; this.longitude = 0.;}
    public Node (String city_name, double lat, double longi) {
        this.name = city_name;
        this.latitude = lat;
        this.longitude = longi;
    }
    
    //setters
    public void setName (String city_name) { this.name = city_name; }
    public void setLatitude (double lat) { this.latitude = lat; }
    public void setLongitude (double longi) { this.longitude = longi; }
       
    //getters
    public String getName () { return this.name; }
    public double getLatitude () { return this.latitude; }
    public double getLongitude () { return this.longitude; }
   
        
}


