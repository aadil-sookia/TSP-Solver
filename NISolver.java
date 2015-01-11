import java.util.ArrayList;

public class NISolver extends TSPSolver {
	
	public NISolver() {	}

	@Override
	public int [] nextNode (Graph G, ArrayList <Integer> visited) {
		
		int [] nodeInfo = new int [2];
		
		double firstDist = Double.MAX_VALUE;
		double lastDist = Double.MAX_VALUE;
		double midDist = Double.MAX_VALUE;
		int midIndex = 0;
		int midNN = 0;
	
		int firstNN = nearestNeighbor (G, visited, visited.get(0));
		int lastNN = nearestNeighbor (G, visited, visited.get(visited.size() - 1));
		if ( G.distance( visited.get(0), firstNN) != 0)
				firstDist = G.distance( visited.get(0), firstNN);
		if ( G.distance (visited.get(visited.size() - 1), lastNN) != 0)
			lastDist = G.distance (visited.get(visited.size() - 1), lastNN);
		
		//loops through each of the middle nodes (excludes first node of visited and last node of visited
		for (int a = 1; a < visited.size() - 1; a ++) {
			
			//calculate a distance between a middle node and its nearest neighbour
			double temp = G.distance(visited.get(a), nearestNeighbor (G, visited, visited.get(a)));
			
			//initializing tempNN outside so that it can later be accessed - is currently irrelevant
			int tempNN = nearestNeighbor (G, visited, visited.get(a));
			
			//check to see if there is a nearest neighbour => if distance between a middle node and its nearest neighbour is 0, the middle node has not found a nearest neighbour
			if (temp != 0) {
					
					//create a copy of the visited arraylist - will add values to exclude NN which cannot be inserted
					ArrayList <Integer> newvisited = new ArrayList <Integer> (visited);
					
					
					//do while loop that will continue looping until it can find a NN that can be inserted or until it fills up the newvisited arraylist
					loop: do {
					
						//creating a temporary NN using the newvisited array (temporary since not sure if it can be inserted)
						tempNN = nearestNeighbor (G, newvisited, visited.get(a));
						
						//if the temporary NN can be inserted, break out of the loop, since we found a NN that can be inserted
						if (canBeInserted (G, visited, a, tempNN)) {
							
							//System.out.format("Node %d is the nearest neighbour of node %d that can be inserted\n", tempNN + 1, visited.get(a) + 1);
							
							break loop;
							
						}
						
						//else, add temporary NN to newvisited so that we can find the next temporary NN
						else newvisited.add(tempNN);
						
						
					} while (newvisited.size() <= G.getnumNodes()); 
					
					//calculate new distance based off the NN that can be inserted (if it can't be inserted, there's still another check ahead)
					temp = G.distance(visited.get(a), tempNN);
					
					//check if nearest neighbor can be added
					if (temp != 0 && temp < midDist && canBeInserted (G, visited, a, tempNN)) {
						
						midDist = temp;
						midIndex = a;
						midNN = tempNN;
					}
			}
			
			
		}
		
		
		//else, check if internal node has been initialize (i.e. has a nearest neighbor) and then check if the NN can be inserted
		if (midDist != Double.MAX_VALUE && midDist <= firstDist && midDist <= lastDist && canBeInserted (G, visited, midIndex, midNN)) {
			
			nodeInfo [0] = midNN;
			nodeInfo [1] = midIndex + 1;
			return nodeInfo;
			
		}
		
		
		//if distance between first node and its NN is closer than others, return first node info
		else if (firstDist != Double.MAX_VALUE && firstDist <= lastDist) {

			nodeInfo [0] = firstNN;
			nodeInfo [1] = 0;
			return nodeInfo;
			
		}
		
		
		//if distance between last node and its NN is closer than others, return last node info
		else if (lastDist != Double.MAX_VALUE && lastDist <= firstDist) {

			nodeInfo [0] = lastNN;
			nodeInfo [1] = visited.size();
			return nodeInfo;
		}
		
		nodeInfo [0] = visited.get(0);
		nodeInfo [1] = 0;

		return nodeInfo;
	}
	
	// check if node k can be inserted at position i
	public boolean canBeInserted ( Graph G, ArrayList < Integer > visited , int i, int k) {
		
		boolean valid = false;
		
		if ( G.existsArc(visited.get(i+1),k) ) {
			
			valid = true;
		}
		
		return valid;
	}
	
	@Override
	public void printFalse (int a){
		
		System.out.format("\nERROR: NI did not find a TSP route for Graph %d!", a);
	}
	
	@Override
	public void printDone () {
		
		System.out.println("\nNode insertion algorithm done.");
		
	}
	
	public void getStats(double [][] allStats, int size) {
		
		allStats [2][0] = avgCost(size);
		allStats [2][1] = avgComp (size);
		allStats [2][2] = successRate (size);
		
		
	}
}

