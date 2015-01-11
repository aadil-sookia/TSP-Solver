import java.util.ArrayList;

public class NNFLSolver extends TSPSolver {

	public NNFLSolver () {}
	
	@Override
	public int [] nextNode (Graph G, ArrayList <Integer> visited) {
		
		
		int [] nodeInfo = new int [2];
		
		//find nearest neighbour to first element of path
		int firstNN = nearestNeighbor (G, visited, visited.get(0));
		
		//find nearest neighbour to last element of path
		int lastNN = nearestNeighbor (G, visited, visited.get(visited.size() - 1));
		
		//if there's no nearest neighbour for first element
		if (visited.get(0) == firstNN) {

				//return values for last neighbour and its nearest neighbour - TSPSolver will check if nearest neighbour has been found
				nodeInfo [0] = lastNN;
				nodeInfo [1] = visited.size();
				return nodeInfo;

			
		}
		
		//if there's no nearest neighbour for last element but there is nearest neighbour for first element
		if (visited.get(visited.size() - 1) == lastNN) {
			
			nodeInfo [0] = firstNN;
			nodeInfo [1] = 0;
			return nodeInfo;
			
		}
		
		//if both elements have nearest neighbours, calculate distances and compare which has shortest
		
		double firstDist = G.distance( visited.get(0), firstNN);
		
		double lastDist = G.distance (visited.get(visited.size() - 1), lastNN);
		
		if ( firstDist < lastDist) {
			
			nodeInfo [0] = firstNN;
			nodeInfo [1] = 0;
			return nodeInfo;
			
			
		}
		
		else {
			
			nodeInfo [0] = lastNN;
			nodeInfo [1] = visited.size();
			return nodeInfo;
			
		}
		
	}
	
	@Override
	public void printFalse (int a){
		
		System.out.format("\nERROR: NN-FL did not find a TSP route for Graph %d!", a);
	}
	
	@Override
	public void printDone () {
		
		System.out.println("\nNearest neighbor first-last algorithm done.");
		
	}
	
	public void getStats(double [][] allStats, int size) {
		
		allStats [1][0] = avgCost(size);
		allStats [1][1] = avgComp (size);
		allStats [1][2] = successRate (size);
		
		
	}
}
