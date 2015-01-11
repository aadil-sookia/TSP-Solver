import java.io.*;
import java.util.ArrayList;

public class Pro5_sookiamu {
    
	public static BufferedReader cin = new BufferedReader (new InputStreamReader (System.in));;
	public static Graph G = new Graph ();
	public static Node N = new Node ();
	public static TSPSolver S = new TSPSolver ();
	public static NNSolver NN = new NNSolver ();
	public static NNFLSolver NNFL = new NNFLSolver ();
	public static NISolver NI = new NISolver ();
	public Pro5_sookiamu () {}
	private static ArrayList <Graph> Gr_list = new ArrayList <Graph> ();

	//function to display main menu
	public static void displayMenu () {
    	System.out.print("   JAVA TRAVELING SALESMAN PROBLEM V2\n" + "L - Load graphs from file\n" +"I - Display graph info\n" + "C - Clear all graphs\n");
    	System.out.print("R - Run all algorithms\n" + "D - Display algorithm performance\n" + "X - Compare average algorithm performance\n" + "Q - Quit\n\n");
    	}
 
	//function to get integer
	public static int getInteger (String prompt, int LB, int UB) throws IOException {
   	 
    	boolean valid;
    	int value;
   	 
    	do {
       	valid = true;
       	value = 1;
       	System.out.print(prompt);
      	 
        	try { value = Integer.parseInt(cin.readLine()); }
       	 
            	catch (NumberFormatException e) {
               	 
                	if (UB == Integer.MAX_VALUE) {
                    	System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB);
                    	valid = false;
                	}
                	else {              	 
                    	System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB);
                    	valid = false;    	}
            	}
       	 
            	catch (IOException e) {
                	if (UB == Integer.MAX_VALUE) {
                    	System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB);
                    	valid = false;
                	}
                	else {              	 
                    	System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB);
                    	valid = false;    	}  
            	}
       	 
        	if (value < LB || value > UB) {
            	if (UB == Integer.MAX_VALUE) {
                	System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB);
                	valid = false;
            	}
            	else {              	 
                	System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB);
                	valid = false;    	}    
            	}
       	 
       	 
 
         	} while (!valid);
   	 
    	return value;	}

	//function to get double
	public static double getDouble (String prompt, double LB, double UB) {
    	boolean valid;
    	double value;
    	do {
        	valid = true;
        	value = 0;
        	System.out.print(prompt);
        	try { value = Double.parseDouble(cin.readLine()); }
       	 
           	catch (NumberFormatException e) {
               	System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
               	valid = false;    	}
          	 
           	catch (IOException e) {
               	System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
               	valid = false;        	}
          	 
        	if (value < LB || value > UB) {
            	System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
               	valid = false;        	}
   	 
            	} while (!valid);
       	 
    	return value;
       	 
    	}

	//reads information from textfile and stores as graph objects in arraylist type graph
	public static void loadGraph () throws IOException {
   	 
    	System.out.print("\nEnter file name (0 to cancel): ");
   	 
    	String input = (cin.readLine());
   	 
    	String line;
   	 
    	//if input is 0, exit loadGraph function
    	if (input.contains(Integer.toString(0))) {
        	System.out.println("\nFile loading process canceled.\n");
        	return;
       	 
    	}
   	 
   	 
    	//check to see if file exists - exit if file does not exist
    	File f = new File (input);
    	if ( ! (f.exists()) ) {
        	System.out.println("\nERROR: File not found!\n"); return; }
   	 
    	BufferedReader fin = new BufferedReader ( new FileReader (input) );
   	 
    	int graphnumber = 0;
    	int validgraphs = 0;
   	 
    	loop: do {
       	 
        	//initialize line each time you go through a line of the text file
        	line = fin.readLine();
       	 
        	//if reached end, break out of loop
        	if (line == null) break;
       	 
        	//if reached empty line, re-start iteration to create new graph object
        	if (line.isEmpty()) continue loop;
       	 
        	//determine number of nodes
        	int number = Integer.parseInt(line);
       	 
        	 
        	Graph Gr = new Graph ();
       	 
        	Gr.initialize (number);
       	 
        	graphnumber ++;
       	 
        	//initializing number cities
        	for (int i = 0; i < number; i ++) {
           	 
            	//storing tokenized string as city name, latitude, longitude (node info)
            	line = fin.readLine();
            	String [] data = line.split(",");
            	String name = data [0];
            	double latitude = Double.parseDouble (data [1]);
            	double longitude = Double.parseDouble (data [2]);
           	 
            	Gr.addNode(name, latitude, longitude, i);
                          	 
        	}
       	 
        	//initializing arcs (nodes - 1)
        	for (int i = 0; i < (number - 1); i ++) {
           	 
            	line = fin.readLine();
            	String [] arcs = line.split(",");
           	 
            	//loop through as many arcs as there are
            	for (int j = 0; j < arcs.length; j ++) {
               	 
                	Gr.setArc( i, ( Integer.parseInt (arcs [j]) - 1 ), true );               	 
               	 
            	}
           	 
                         	 
        	}
       	 
        	//counting number of arcs and setting it for public access
        	int count = 0;
        	for (int i = 0; i < Gr.getnumNodes(); i ++)
            	for (int j = i + 1; j < Gr.getnumNodes(); j ++)
                	if (Gr.existsArc (i, j))
                    	count ++;
       	 
        	Gr.setnumArcs(count);
        	Gr.setlinkSize(count);
      	 
       	 
        	//check if valid graphs have been entered
        	if (Gr.isvalidNode() && Gr.isvalidLatLong()) {
       		 Gr_list.add(Gr);
            	validgraphs ++;
        	}
       		 
    	} while (line!= null);
   	 
    	fin.close();

    	
    	System.out.format("\n%d of %d graphs loaded!\n\n", validgraphs, graphnumber);
   	 
   	 
	}
   
	//displays graph info
	public static void displayGraph () throws IOException {
   	 
   	 do {
   	 
   	 System.out.println("\nGRAPH SUMMARY");
   	 System.out.println("No.    # nodes    # arcs");
   	 System.out.println("------------------------");
   	 
   	 for (int i = 0; i < Gr_list.size(); i ++) {
   		 System.out.format("%3d%11d%10d\n", (i+1), Gr_list.get(i).getnumNodes(), Gr_list.get(i).getnumArcs());
   	 }
   	 
   	 System.out.println();
   	 
   	 int input = getInteger ("Enter graph to see details (0 to quit): ", 0, Gr_list.size() );
   	 
   	 if (input == 0) { System.out.println(); return; }
   	 
   	 else  { System.out.println(); Gr_list.get(input-1).print(); }
   	 
   	 
   	 } while (true);
   	 
	}
    
	//clears arraylist of graphs
	public static void clearGraphs () {
   	 Gr_list.clear();
   	 NN = new NNSolver ();
   	 NNFL = new NNFLSolver ();
   	 NI = new NISolver ();
   	 System.out.println("\nAll graphs cleared.");
	}
	
	//runs algorithm on all graphs 
	public static void runAlgorithm () {
		
		for (int i = 0; i < Gr_list.size(); i ++)
			NN.run(Gr_list, i);
		NN.printDone();
		
		for (int i = 0; i < Gr_list.size(); i ++)
			NNFL.run(Gr_list, i);
		NNFL.printDone();

		for (int i = 0; i < Gr_list.size(); i ++)
			NI.run(Gr_list, i);
		NI.printDone();
		
	}
	
	//displays statistics (distance, comp time, standard deviation)
	public static void displayStats () {

		if (NN.hasResults() || NNFL.hasResults() || NI.hasResults()) {
			
			NN.printStats(Gr_list.size(), "nearest neighbor:");
			NNFL.printStats(Gr_list.size(), "nearest neighbor first-last:");
			NI.printStats(Gr_list.size(), "node insertion:");
			 		
		}
		
		else  System.out.println("\nERROR: Results do not exist for all algorithms!\n");
	}
	
	public static void compareStats () {
		
		if (NN.hasResults() || NNFL.hasResults() || NI.hasResults()) {
			
			double [][] allStats = new double [3][3];
			
			System.out.println("\n------------------------------------------------------------");
			System.out.println("           Cost (km)     Comp time (ms)     Success rate (%)");
			System.out.println("------------------------------------------------------------");
			
			NN.getStats (allStats, Gr_list.size());
			NNFL.getStats (allStats, Gr_list.size());
			NI.getStats (allStats, Gr_list.size());
			
			System.out.format("NN%18.2f%19.3f%21.1f\n", allStats[0][0], allStats[0][1], allStats[0][2]);
			System.out.format("NN-FL%15.2f%19.3f%21.1f\n", allStats[1][0], allStats[1][1], allStats[1][2]);
			System.out.format("NI%18.2f%19.3f%21.1f\n", allStats[2][0], allStats[2][1], allStats[2][2]);
			System.out.println("------------------------------------------------------------");
			
			String [] winners = new String [3];
			String overall = "Unclear";
						
			for (int i = 0; i < 2; i ++) {
				
				if (Double.isNaN (allStats[1][i]) )
					winners [i] = "NN";
				
				else if (Double.isNaN(allStats[1][i]) )
						winners [i] = "NN-FL";
					
				else if ( allStats[0][i] <= allStats [1][i] && allStats [0][i] <= allStats[1][i] )
					winners[i] = "NN";
				
				else if ( allStats [1][i] <= allStats [2][i])
					winners [i] = "NN-FL";
				
				else winners[i] = "NI";
						
			}
			
			if ( allStats[0][2] >= allStats [1][2] && allStats[0][2] >= allStats [2][2])
				winners[2] = "NN";
			
			else if ( allStats [1][2] >= allStats [2][2])
				winners[2] = "NN-FL";
			
			else winners[2] = "NI";
			
			System.out.format("Winner%14s%19s%21s\n", winners[0], winners[1], winners[2]);
			System.out.println("------------------------------------------------------------");
			
			if (winners[0] == winners[1] && winners [0] == winners[2] && winners[1] == winners[2])
				overall = winners[0];
			
			System.out.format("Overall winner: %s\n\n", overall);
				
			
		}
		
		else System.out.println("\nERROR: Results do not exist for all algorithms!\n");
	}
    
	//Main function
	public static void main(String[] args) throws NumberFormatException, IOException {

	menuOption();
	 
   	}
  	 
	//function to get character menu options (used for main menu and edit arcs)
	public static void menuOption () throws IOException {
		
    	String input;   
    	char men_op = 'a';
   	 
    	do {
        	displayMenu ();
        	System.out.print("Enter choice: ");
        	input = (cin.readLine());
   
        	if (input.length() == 0) { System.out.println("\nERROR: Invalid menu choice!\n"); continue; }
       	 
        	
        	if (input.length() == 1) 
        		men_op = input.charAt(0);
        	
        	else { System.out.println("\nERROR: Invalid menu choice!\n"); continue; }

        	if ( men_op != 'l' && men_op != 'L' && men_op != 'i' && men_op != 'I' && men_op != 'c' && men_op != 'C' && men_op != 'r' && men_op != 'R' && men_op != 'd' && men_op != 'D'
                	&& men_op != 'x' && men_op != 'X' && men_op != 'q' && men_op != 'Q') {
            	System.out.println("\nERROR: Invalid menu choice!\n");
            	continue;
        	}
       	 
        	if (men_op == 'q' || men_op == 'Q') {
            	System.out.print("\nCiao!\n");
            	return;
             	}
       	 
            	 
       	//all menu options
       	if (men_op == 'l' || men_op == 'L') {
       	 
           	loadGraph();
           	//NN.reset();
           	NN = new NNSolver ();
           	NNFL = new NNFLSolver ();
           	NI = new NISolver ();
           	continue;
          	 
       	}
       	
       	if (men_op == 'd' || men_op == 'D') {
   		    
   			displayStats ();
   			continue;
       	}
       	
       	if (men_op == 'x' || men_op == 'X') {
       		
       		compareStats();
       		continue;
       	}
      	 
       	if (Gr_list.isEmpty()) {
   		    
   			System.out.format("\nERROR: No graphs have been loaded!\n\n");
   			continue;
          		    
       	}
	 
       	if (men_op == 'i' || men_op == 'I') {
   		    
   			displayGraph();
   		    
       	}
      	 
       	if (men_op == 'c' || men_op == 'C') {
   		    
   			clearGraphs();
   			System.out.println();
       	}
       	 
       	if (men_op == 'R' || men_op == 'r') {
   		    
   			runAlgorithm ();
   			System.out.println();
       	}
   		    

 
    	} while (men_op != 'q' || men_op != 'Q');
   	 
          	 	}
}
