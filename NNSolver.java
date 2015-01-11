import java.util.ArrayList;

public class NNSolver extends TSPSolver {
	
	public NNSolver() {	}

	@Override
	public int [] nextNode (Graph G, ArrayList <Integer> visited) {
		
		int [] nodeInfo = new int [2];
		nodeInfo [0] = nearestNeighbor (G, visited, visited.get( visited.size() - 1));
		nodeInfo [1] = visited.size();
		return nodeInfo;
	
	}
	
	@Override
	public void printFalse (int a){
		
		System.out.format("\nERROR: NN did not find a TSP route for Graph %d!", a);
	}
	
	@Override
	public void printDone () {
		
		System.out.println("\nNearest neighbor algorithm done.");
		
	}
	
	public void getStats(double [][] allStats, int size) {
		
		allStats [0][0] = avgCost(size);
		allStats [0][1] = avgComp (size);
		allStats [0][2] = successRate (size);
		
		
	}

}


