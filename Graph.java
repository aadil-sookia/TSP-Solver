import java.io.*;
import java.util.ArrayList;
//import java.util.Arrays;

public class Graph {
    
	//creating variables
	public static Pro5_sookiamu M = new Pro5_sookiamu ();
	private int numNodes;
	private int numArcs;
	private boolean [][] adjMatrix;
	private double [][] costMatrix;
	private ArrayList <Node> node = new ArrayList <Node> ();
	private boolean validNode = true;
	private boolean validLatLong = true;
    
	//2d array to link the count number with its respective node connections (arcs)
	int linkSize = getlinkSize();
	int [][] links;
	int count;
    
	//constructors
	public Graph () {}
	public Graph (int numNodes) {}

	//setters
	public void setnumNodes (int n) { this.numNodes = n; }
	public void setnumArcs (int m) { this.numArcs = m; }
	public void setArc (int i, int j, boolean b) { adjMatrix [i][j] = b; adjMatrix [j][i] = b; }
	public void setCost (int i, int j, double c) { costMatrix [i][j] = c; costMatrix [j][i] = c; }
	public void setvalidNode (boolean b) { validNode = b; }
	public void setvalidLatLong (boolean b) { validLatLong = b; }
    public void setlinkSize (int size) { linkSize = size; } 	
	
	//getters
	public int getnumNodes () { return this.numNodes; }
	public int getnumArcs () { return this.numArcs; }
	public boolean getArc (int i, int j) { return this.adjMatrix [i][j]; }
	public double getCost (int i, int j) { return this.costMatrix [i][j]; }
	public ArrayList <Node> getNode () { return node; }
	public boolean isvalidNode () { return validNode; }
	public boolean isvalidLatLong () { return validLatLong; }
	public int getlinkSize () { return linkSize; }
	 
	//function to fill in arraylist with strings (names) and doubles (latitudes/longitudes)
	public void initialize (int numNodes) throws IOException {
    	 
    	setnumNodes (numNodes);
    	adjMatrix = new boolean[numNodes][numNodes];
    	costMatrix = new double[numNodes][numNodes];

}
    
	// add a node
	public void addNode (String name, double latitude, double longitude, int i) throws IOException {
   	 
    	Node N = new Node ();
           	 
    	N.setName(name);
    	N.setLatitude(latitude);
    	N.setLongitude(longitude);
   	 
    	if ( latitude > 90 || latitude < - 90 || longitude > 180 || longitude < -180) {
    	
  		 setvalidLatLong (false);
   		 
    	}
    	 
    	if (existsNode(N)) {
               	setvalidNode (false);
               	
    	}
           	 
     	node.add(i, new Node ( N.getName(), N.getLatitude(), N.getLongitude() ));
  	 
	}
    
	// check if node exists to avoid repetitions
	public boolean existsNode (Node N) {
   	 
    	boolean check = false;
               	 
    	for (int i = 0; i < node.size() && check == false; i ++)
        	if ( ( N.getName().equals(node.get(i).getName()) ) || ( N.getLatitude() == node.get(i).getLatitude() && N.getLongitude() == node.get(i).getLongitude() ) ) {
            	check = true;
                       	}
           	 
    	return check;
   	 
  	}
    
	//function to print Node
	public void printNode () {
   	 
    	System.out.println("\nNODE LIST\n" + "No." + "               " + "Name" + "        " + "Coordinates");
    	System.out.println("-----------------------------------------");
   	 
    	for (int i = 0; i < getnumNodes(); i ++) {

       	 
        	String latlong = "(" + node.get(i).getLatitude() + "," + node.get(i).getLongitude() + ")";

       	 
        	System.out.format("%3d%19s%19s\n", (i+1), node.get(i).getName(), latlong);
       	 
    	}

    	System.out.println();
   	 
	}
    
	// check if arc exists to avoid repetitions
	public boolean existsArc ( int i, int j) {
   	 
    	boolean check = true;
   	 
    	//if arc doesn't exist, send back false
    	if (!getArc (i, j))
        	check = false;
   	 
    	return check;
    
	}
    
	// print arc list
	public void printArcs () {
   	 
    	System.out.println("ARC LIST");
    	System.out.println("No." + "    " + "Cities" + "       " + "Distance");
    	System.out.println("----------------------------");
   	 
    	count = 0;

    	for (int i = 0; i < numNodes; i ++) {
        	for (int j = i + 1; j < numNodes; j ++) {
            	if (existsArc (i, j)) {
                	count ++;
                	String arcs = (i+1)+"-"+(j+1);
                	System.out.format("%3d%10s%15.2f", count, arcs , distance (i, j) );
                	System.out.println();
                	links [count][0] = i + 1;
                	links [count][1] = j + 1;    
                                 	 
                  	}
            	}
        	}
   	 
    	System.out.println();
   	 
   	}
 
	// print all graph info (number nodes, number arcs, node list, arc list)
	public void print () {
		
		links = new int [getlinkSize() + 1][2];

    	System.out.println("Number of nodes: " + getnumNodes());
    	System.out.println("Number of arcs: " + getnumArcs());
    	printNode();
    	printArcs();
           	 
	}   
    
	// calculates distance between two nodes
	public double distance (int i, int j) {
   	 
    	final double radians = Math.PI/180;
   	 
    	double lat1 = radians * node.get(i).getLatitude();
    	double long1 = radians * node.get(i).getLongitude();
    	double lat2 = radians * node.get(j).getLatitude();
    	double long2 = radians * node.get(j).getLongitude();
               	 
    	double a = Math.sin( (lat1 - lat2)/2 ) *  Math.sin( (lat1 - lat2)/2 ) + ( ( Math.cos(lat1) ) * Math.cos(lat2) *  Math.sin( (long1 - long2)/2 ) * Math.sin( (long1 - long2)/2 ) ) ;
   	 
    	double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
   	 
    	double distance = 6371 * b;

    	setCost (i , j , distance);
   	 
    	return distance;
       	 
	}
    
	// check feasibility of path P
	public boolean checkPath ( int [] path) {
   	 
    	boolean feasible = true;
   	 
    	//first check = see if start city and end city are same
   	 
    	if (path[0] != path[getnumNodes()]) {
       	 
        	System.out.println("\nERROR: Start and end cities must be the same!\n");
        	feasible = false;
        	return feasible;
       	 
    	}
   	 
    	//second check = see if user input appears twice (excluding last input)
   	 
    	for (int i = 0; i < getnumNodes(); i ++) {
        	for (int j = i + 1; j <getnumNodes(); j ++) {
            	if (path[i] == path[j]) {
                	System.out.println("\nERROR: Cities cannot be visited more than once!");
                	System.out.println("ERROR: Not all cities are visited!\n");
                	feasible = false;
                	return feasible;
                                   	 
            	}
               	 
        	}
    	}
   	 
    	//third check = see if arc exists
   	 
    	for (int i = 0; i < getnumNodes(); i ++) {
        	if (!existsArc( (path[i] - 1), (path[i + 1] - 1) ) ) {
            	System.out.println("\nERROR: Arc " + path[i] + "-" + path[i+1] + " does not exist!\n");
            	feasible = false;    
            	return feasible;
        	}
                   	 
    	}
   	 
    	return feasible;

	}
    
	// calculate cost of path P
	public double pathCost ( int [] path) {
   	 
    	double distance = 0;
           	 
    	for (int i = 0; i < getnumNodes () ; i ++)
        	distance += getCost ( ( path[i] - 1 ), ( path[i + 1] - 1 ) );
               	 
    	return distance;

	}
    

}

