import java.util.ArrayList;

public class TSPSolver {
	
	private ArrayList <int [] > solnPath = new ArrayList <int []>(); // ArrayList *or* array of solution paths
	private ArrayList <Double> solnCost = new ArrayList <Double>() ; // ArrayList *or* array of solution costs
	private ArrayList<Double> compTime = new ArrayList <Double>() ; // ArrayList *or* array of computation times
	private ArrayList <Boolean> solnFound = new ArrayList <Boolean>() ; // ArrayList *or* array of T/F solns found
	private boolean resultsExist = false ; // whether or not results exist
	private ArrayList <Integer> visited = new ArrayList <Integer> ();

	// constructors
	public TSPSolver () { }
	public TSPSolver ( ArrayList < Graph > G ) { }

	// getters
	public int [] getSolnPath ( int i ) { return solnPath.get(i); }
	public double getSolnCost ( int i ) { return solnCost.get(i); }
	public double getCompTime ( int i ) { return compTime.get(i); }
	public boolean getSolnFound ( int i ) { return solnFound.get(i); }
	public boolean hasResults () { return resultsExist; }
	
	// setters
	public void setSolnPath ( int i , int [] solnPath1 ) { solnPath.add(i,solnPath1); }
	public void setSolnCost ( int i , double solnCost1 ) { solnCost.add(i, solnCost1); }
	public void setCompTime ( int i , double compTime1 ) { compTime.add(i,(double) compTime1); }
	public void setSolnFound ( int i , boolean solnFound1 ) { solnFound.add(i, solnFound1); }
	public void setHasResults ( boolean b ) { resultsExist = b; }
	
	
	//prints ERROR - not found
	public void printFalse (int a) {
		
		System.out.format("\nERROR: No TSP route found for Graph %d!", a);
	}

	//prints DONE
	public void printDone () {
		
		System.out.println("All graphs are done");
		
	}
	
	// run nearest neighbor on Graph i
	public void run ( ArrayList < Graph > G , int a ) { 
		
		setHasResults(true);
		
		long start = System.currentTimeMillis();
		
		//create path array of size numNodes 
		int [] Path = new int [G.get(a).getnumNodes() + 1];
		
		//first node in path is always node 1 (index 0)
		//Path [0] = 0;
		visited.clear();
		visited.add(0);
		//int currentNode = 0;
	
		//loop through Path array until it's filled - can kick out if unable to run algorithm due to lack of unused nodes
		loop: for (int i = 0; i < G.get(a).getnumNodes() - 1 ; i ++) {
			
			int [] newNode = new int [2];
			newNode = nextNode (G.get(a), visited);
			
			//add nextNode to path	
			if (visited.get(0) != newNode[0] && visited.get(visited.size()-1) != newNode[0]) {
				visited.add(newNode[1],newNode[0]);
				continue loop;
				
			}
			
			else { 	printFalse(a+1);
					setSolnCost(a,-1.0); 
					setSolnFound(a,false);
					setCompTime(a,-1.0); 
					setSolnPath(a,Path);
					return;}
			
			}
		
	double totaldistance = 0;
	
	if (G.get(a).existsArc(visited.get(visited.size()-1), visited.get(0))) 
		totaldistance += G.get(a).distance(visited.get(visited.size()-1), visited.get(0));

	else { 	printFalse(a+1);
			solnCost.add(a,-1.0);
			solnFound.add(a,false);
			compTime.add(a,-1.0);
			solnPath.add(a,Path);
			return; }
	
	//initialize values of Path array - use visited arraylist to populate elements + initialize last value of Path as first node in visited
	for (int c = 0; c < visited.size(); c ++)
		Path[c] = visited.get(c);
	
	Path[Path.length - 1] = visited.get(0);
	
	for (int b = 0; b < (Path.length - 2) ; b ++)
		totaldistance += G.get(a).distance(Path [b], Path [b+1]);
	
	long elapsedTime = System.currentTimeMillis() - start;
	solnPath.add(a, Path);
	compTime.add(a,(double) elapsedTime);
	solnCost.add(a,totaldistance);
	solnFound.add(a, true);

	}
	
	//finds next node - keep a blank function in TSPSolver and Override in other subclasses
	
	public int [] nextNode (Graph G, ArrayList <Integer> visited) {
		
		int [] k = new int[2];
		return k;
	}
	
	
	// find nearest unvisited neighbor to last visited node
	public int nearestNeighbor ( Graph G , ArrayList < Integer > visited, int i ) { 
		
		double min = Integer.MAX_VALUE;
		int nextNode = i;
		//check if node j has been visited
		for (int j = 0; j < G.getnumNodes(); j ++) {

			if (visited.contains(j)) 
				continue;

			//check if arc between node i and j exist
			else if ( !(G.existsArc(i, j) ) ) 
				continue;

			//node j hasn't been visited and arc i-j exists
			else if  ( G.distance(i, j) < min ) {
					//if current distance is lower than minimum, change minimum and change index of bestNode
					min = G.distance(i, j);
					nextNode = j;
							
			}
		
		}
	
		return nextNode;
		
	}
	
	// reset variables and arrays
	public void reset () { 
		
		solnPath.clear();
		solnCost.clear();
		compTime.clear();
		solnFound.clear();
		visited.clear();
		setHasResults(false);

		
	}
	
	// print statistics
	public void printStats (int size, String prompt) {
		
		System.out.println("\nDetailed results for " + prompt);
		System.out.println("-----------------------------------------------");
		System.out.println("No.        Cost (km)     Comp time (ms)   Route   ");
		System.out.println("-----------------------------------------------");

		for (int i = 0; i < size; i++) {
			
			if (getSolnCost(i) != -1) { 
				
				System.out.format("%3d%17.2f%19.3f   ",(i+1), getSolnCost(i), getCompTime(i));

				for (int j = 0; j < getSolnPath(i).length - 1; j++) 
					System.out.format("%d-", (getSolnPath(i)[j] + 1) );
				
				System.out.print(getSolnPath(i)[getSolnPath(i).length -1] + 1 + "\n");

			}
			
			else {
				
				System.out.format("%3d                -                  -   -\n", (i+1));
			}
			
			
		}
		
		
		System.out.println("\nStatistical summary for " + prompt);
		System.out.println("---------------------------------------");
		System.out.println("           Cost (km)     Comp time (ms)");
		System.out.println("---------------------------------------");
		System.out.format("Average%13.2f%19.3f\n", Average(size, solnCost), Average(size, compTime) );
		System.out.format("St Dev %13.2f%19.3f\n", stDev(size, Average(size, solnCost), solnCost), stDev(size, Average(size, compTime), compTime ) ) ;
		System.out.format("Min    %13.2f%19.3f\n", min(size, solnCost), min(size, compTime) );
		System.out.format("Max    %13.2f%19.3f\n", max(size, solnCost), max(size, compTime) );

		double rate = successRate (size);
		
		System.out.format("\nSuccess rate: %.1f%%\n", rate );
		
		System.out.println();

		
	}
	
	// calculates success rate
	public double successRate (int size) {
		
		double success = 0;
		
		for (int i = 0; i < size; i++) 
			
			if (getSolnCost(i) != -1)  
				success ++;
		
		success = (success/(double)size) * 100;
		
		return success;
	
	
	}
	
	
	//gets average cost
	public double avgCost (int size) {
		
		double avg =  Average(size, solnCost);
		
		return avg;
		
		
	}
	
	//gets average cost
	public double avgComp (int size) {
		
		double avg =  Average(size, compTime);
		
		return avg;
		
		
	}
	
	//calculates average with a size and arraylist of doubles
	public double Average (int a, ArrayList <Double> V) {

		double sum = 0.0;
		
		int valid = 0;
		
		for (int i = 0; i < a; i ++) {
			
			if (V.get(i) != -1) {
				sum += V.get(i);
				valid ++;
				
			}
			
		}
		
		double average = sum/valid;
		
		return average;
			
		
	}
	
	//calculates minimum with a size and arraylist of doubles
	public double min (int a, ArrayList <Double> V) {
		
		double min = Double.MAX_VALUE;
		
		for (int i = 0; i < a; i ++) {
			
			if (V.get(i) != -1 && V.get(i) < min) 
				min = V.get(i);
				
			}
		
		return min;
		
	}
	
	//calculates maximum with a size and arraylist of doubles
	public double max (int a, ArrayList <Double> V) {
		
		double max = 0;
		
		for (int i = 0; i < a; i ++) {
			
			if (V.get(i) != -1 && V.get(i) > max)
				max = V.get(i);
		}
		
		return max;
			
	}
	
	//calculates standard deviation with a size and arraylist of doubles
	public double stDev(int a, double avg, ArrayList <Double> V) {
		
		int valid = -1;
		
		double sum = 0;
		
		double dev = 0.0;

		for (int i = 0; i < a; i ++) {
			
			if (V.get(i) != -1) {
				valid ++;
				sum += Math.pow((V.get(i)-avg),2);
				
			}
			
		}
		
		dev = Math.sqrt ((sum/valid));
		
		return dev;
	}
	
	//TO BE FILLED
	public void init ( ArrayList < Graph > G ) { } // initialize variables and arrays
	
	public void printAll () { }// print results for all graphs
	
	

}
