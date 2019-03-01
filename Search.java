package finalPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.NodeSetData;

//Search class, responsible for implementing the search algorithms. 
public class Search {
	
	ArrayList<int[][]> dfsexpanded = new ArrayList<int[][]>();

	public static void main(String[] args){
		
		//different test cases 
		int[][] easy = new int[][] {{1,3,4},{8,6,2},{7,0,5}};
		int[][] medium = new int[][] {{2,8,1},{0,4,3},{7,6,5}};
		int[][] hard = new int[][] {{5,6,7},{4,0,8},{3,2,1}};
		int[][] goalState = new int[][]{{1,2,3},{8,0,4},{7,6,5}};
		int[][] goal = new int[][] {{1,3,4},{8,6,2},{0,7,5}};
		
		//Creating instance of class 
		Search s = new Search();
		System.out.println(".......BFS.....");
//		s.bfs(easy, goalState);
//		s.bfs(medium, goalState);
//		s.bfs(hard, goalState);
//
//		System.out.println(".......DFS.....");
//		s.DFS(easy, goalState);
//		s.DFS(medium, goalState);
//		s.DFS(hard, goalState);
//		
//		System.out.println(".......UCS.....");
//		s.uniformCost(easy, goalState);	
//		s.uniformCost(medium, goalState);
//		s.uniformCost(hard, goalState);
//		
//		System.out.println(".......GREEDYbfs.....");
//		s.Greedybfs(easy, goalState);
//		s.Greedybfs(medium, goalState);
//		s.Greedybfs(hard, goalState);
//		
//		System.out.println(".......A* (1).....");
		s.aStar(easy, goalState);
//		s.aStar(medium, goalState);
//		s.aStar(hard, goalState);
	}
	
	//returns totalCost and performs breadth first search from startState to the goalState. 
	public int bfs(int[][] startState, int[][] goalState){
		
		long startTime = System.nanoTime();
		long totalTime = 0; 
		double seconds = 0.0;
		
		int depth;
		ArrayList<int[][]> expanded = new ArrayList<int[][]>();
		
		//checks if the startState is the goalState
		if(startState == goalState){
            System.out.println("Goal Node Found!");
            return 0; 
        }
		
		Queue<Node> pq = new LinkedList<Node>();
		
		//space queue keeps a track of the Nodes added on the space, to check the size of the queue at its max. 
		Queue<Node> space = new LinkedList<Node>();
		
		Node root = new Node(startState, null, null, 0, 0);
		depth = 0; 
		
		//pushes the startNode on the queue. 
		pq.add(root);
		space.add(root);
		expanded.add(root.state);
		int totalCost = 0;
		
		/* checks if the queue is empty, and if it's not, it pops a Node from queue and searches for its neighbors 
		 * and pushes the neighbors on the queue.
		 */
		
		while(!pq.isEmpty()) {
			Node current = pq.poll();
			totalCost += current.cost;
			System.out.println(current.action + ", cost = " + current.cost + ", totalCost = " + totalCost);
			printState(current.state);
				
			if(equal(current.state, goalState)) {
				System.out.println("Goal state found ...");
				printState(current.state);
				System.out.println("Space size " + space.size());
				System.out.println("Total time = " + seconds + " seconds");
				System.out.println("Depth = " + depth);
				return 0;
			}
			depth++;
			
			ArrayList<Node> node = current.neighbors();	
			Iterator<Node> i = node.listIterator();
			
			while(i.hasNext()) {
				Node n = i.next();
				if(!isExpanded(expanded, n.state)) {
					n.parent = current;
					expanded.add(n.state);
					pq.add(n);
					space.add(n);
			}			
		}
			long endTime   = System.nanoTime();
			totalTime = endTime - startTime;
			seconds = (double) totalTime/1_000_000_000.0;
			
			//kills a program if it runs more than 5 minutes. 
			if(seconds > 120) {
				System.out.println("Taking too long..Terminating the program");
				System.exit(0);
			}
	}
		return totalCost;
}
	
	/*
	 * Depth First Search function takes a startState and the goalState, and calls the DFSRec fun 
	 */
	public void DFS(int[][] startState, int[][] goalState) {
		
		
		System.out.println("IN DFS...");
		
		if(equal(startState, goalState)) {
			System.out.println("Goal state found");
			return;
		}
		DFSRec(startState, goalState);
		
	}
	
	/*
	 * Calls the neighbors and recursively calls DFSRec to visited other unexpanded Nodes. 
	 */
	public void DFSRec(int[][] startState, int[][] goalState) {
		
		long startTime = System.nanoTime();
		long totalTime = 0; 
		double seconds = 0.0;
		dfsexpanded.add(startState);
		System.out.println("Printing current state...");
		
		printState(startState);
		Node root = new Node(startState, null, null, 0, 0);
		System.out.println(root.action + ", cost = " + root.cost + ", totalCost = " + root.totalCost);

		ArrayList<Node> node = root.neighbors();
		
		/*
		 * checks if currentState equals goalState
		 * prints the current state, space size, totalTime
		 */
		if(equal(startState, goalState)) {
			System.out.println("Goal state found");
			printState(startState);
			System.out.println("Total time = " + seconds + " seconds");
			
			return;
		}
			
			Iterator<Node> i = node.listIterator();

			while(i.hasNext()) {
				Node n = i.next();
				if(!isExpanded(dfsexpanded, n.state)) {
					DFSRec(n.state, goalState);
					}

				}
			long endTime   = System.nanoTime();
			totalTime = endTime - startTime;
			seconds = (double) totalTime/1_000_000_000.0;
			
			//kills a program if it runs more than 5 minutes. 
			if(seconds > 120) {
				System.out.println("Taking too long..Terminating the program");
				System.exit(0);
			}

			}			

	/*
	 * Uniform Cost Search algorithm. It expanded the minimum Cost Node first.  
	 */
	public void uniformCost(int[][] startState, int[][] goalState) {

		long startTime = System.nanoTime();
		long totalTime = 0; 
		double seconds = 0.0;
		int depth = 0;
		ArrayList<int[][]> expanded = new ArrayList<int[][]>();
		

		if(startState == goalState){
            System.out.println("Goal Node Found!");
            return; 
        }
		
		//creates a queue for storing the nodes. 
		Queue<Node> pq = new LinkedList<Node>();
		
		//space queue keeps a track of the Nodes added on the space, to check the size of the queue at its max. 

		Queue<Node> space = new LinkedList<Node>();
		
		//creates a Node with the startState. 
		Node root = new Node(startState, null, null, 0, 0);
		
		//pushes the starting node onto the queue. 
		pq.add(root);
		space.add(root);
		expanded.add(root.state);
		int totalCost = 0;
		
		while(!pq.isEmpty()) {
			
			//pops the startNode from the queue and stores in current. 
			Node current = pq.poll();
			totalCost += current.cost;
			System.out.println(current.action + ", cost = " + current.cost + ", totalCost = " + totalCost);
			printState(current.state);
			
			/*
			 * checks if currentState equals goalState
			 * prints the current state, space size, totalTime
			 */
			if(equal(current.state, goalState)) {
				System.out.println("Goal state found ...");
				printState(current.state);
				System.out.println("Space size " + space.size());
				System.out.println("Total time = " + seconds + " seconds");
				System.out.println("Depth = " + depth);
				return;
			}
			
			depth++;
			ArrayList<Node> node = current.neighbors();	
			Iterator<Node> i = node.listIterator();
			
			while(i.hasNext()) {
				/*
				 * In the next two lines, Node n equals the minimum Cost Node returned from the minCostNode function. 
				 * node.remove(n), removes the node from the ArrayList, so next time minCostNode() function is called, it returns the minimum Cost Node 
				 * from the remaining nodes in the ArrayList.
				 */
				Node n = minCostNode(node); 
				node.remove(n); 
				if(!isExpanded(expanded, n.state)) {
					expanded.add(n.state);
					space.add(n);
					n.parent = current;
					pq.add(n);
				}			
			}
			long endTime   = System.nanoTime();
			totalTime = endTime - startTime;
			seconds = (double) totalTime/1_000_000_000.0;
			
			//kills a program if it runs more than 5 minutes. 
			if(seconds > 120) {
				System.out.println("Taking too long..Terminating the program");
				System.exit(0);
			}

		}
	}
	
	/*
	 * Greedy best first search, it has the heuristic h(n) = minimum number of tiles misplaced. 
	 */
	public void Greedybfs(int[][] startState, int[][] goalState) {
		
		long startTime = System.nanoTime();
		long totalTime = 0; 
		double seconds = 0.0;
		int depth = 0; 
		//int array to keep track of expanded nodes. 
		ArrayList<int[][]> expanded = new ArrayList<int[][]>();
		int totalCost = 0; 
		
		//checks if the startState is the goalState
		if(startState == goalState){
            System.out.println("Goal Node Found!");
            return;
        }
		
		//creates a queue for storing the nodes. 				
		Queue<Node> pq = new LinkedList<Node>();
		
		//space queue keeps a track of the Nodes added on the space, to check the size of the queue at its max. 
		Queue<Node> space = new LinkedList<Node>();
		
		Node root = new Node(startState, null, null, 0, 0);
	
		pq.add(root);
		
		while(!pq.isEmpty()) {
			
			//pops the startNode from the queue and stores in current. 
			Node current = pq.poll();
			totalCost += current.cost;
			System.out.println(current.action + ", cost = " + current.cost + ", totalCost = " + totalCost);
			printState(current.state);
			
			depth++;
			/*
			 * checks if currentState equals goalState
			 * prints the current state, space size, totalTime
			 */
			if(equal(current.state, goalState)) {
				System.out.println("Goal state found ...");
				printState(current.state);
				System.out.println("Space size " + space.size());
				System.out.println("Total time = " + seconds + " seconds");
				System.out.println("Depth = " + depth);
				return;
			}
			else {
				//gets neighbors of the current node and stores in the node.
				ArrayList<Node> node = current.neighbors();	
				Iterator<Node> i = node.listIterator();
				
				while(i.hasNext()) {
					/*
					 * Node n equals the Node that has minimum tiles misplaced. . 
					 * node.remove(n) removes the minimum Node from the ArrayList, so next time minTilesMisplaced() is called, 
					 * it gets called on the remaining nodes. 
					 */
					Node n = minTilesMisplaced(node, goalState); 
					node.remove(n); 
					if(!isExpanded(expanded, n.state)) {
						expanded.add(n.state);
						space.add(n);
						pq.add(n);
					}		
			}
				long endTime   = System.nanoTime();
				totalTime = endTime - startTime;
				seconds = (double) totalTime/1_000_000_000.0;
				
				//kills a program if it runs more than 5 minutes. 
				if(seconds > 120) {
					System.out.println("Taking too long..Terminating the program");
					System.exit(0);
				}

		}	
	}		
}
	
	/*
	 * A* Search algorithm, with f(n) = g(n) + h(n)
	 * g(n) = totalCost from startNode to n; 
	 * h(n) = total number of tiles misplaced in startState. 
	 */
	public void aStar(int[][] startState, int[][] goalState) {
		
		long startTime = System.nanoTime();
		long totalTime = 0; 
		double seconds = 0.0;
		int depth = 0; 
		ArrayList<int[][]> expanded = new ArrayList<int[][]>();

		if(startState == goalState){
            System.out.println("Goal Node Found!");
            return; 
        }
		
		Queue<Node> pq = new LinkedList<Node>();
		
		Queue<Node> space = new LinkedList<Node>();

		Node root = new Node(startState, null, null, 0, 0);
		System.out.println(root.cost);
		
		pq.add(root);
		expanded.add(root.state);
		int totalCost = 0;
		
		while(!pq.isEmpty()) {
			
			Node current = pq.poll();
			totalCost += current.cost;
			System.out.println(current.action + ", cost = " + current.cost + ", totalCost = " + totalCost);
			printState(current.state);
				
			if(equal(current.state, goalState)) {
				System.out.println("Goal state found ...");
				printState(current.state);
				System.out.println("Space size " + space.size());
				System.out.println("Total time = " + seconds + " seconds");
				System.out.println("Depth = " + depth);
				return;
			}
			depth++;
			
			ArrayList<Node> node = current.neighbors();	
			Iterator<Node> i = node.listIterator();
			
			while(i.hasNext()) {
				/*
				 * Node n equals the Node that has minimum f(n). 
				 * node.remove(n) removes the minimum Node from the ArrayList, so next time minF() is called, 
				 * it gets called on the remaining nodes. 
				 */
				Node n = minF(startState, node);
				node.remove(n);
				if(!isExpanded(expanded, n.state)) {
					expanded.add(n.state);
					space.add(n);
					pq.add(n);
			}			
		}
			long endTime   = System.nanoTime();
			totalTime = endTime - startTime;
			seconds = (double) totalTime/1_000_000_000.0;
			
			//kills a program if it runs more than 5 minutes. 
			if(seconds > 120) {
				System.out.println("Taking too long..Terminating the program");
				System.exit(0);
			}
		}
	}

		//returns the Node with minimum cost from the ArrayList of Nodes.  
		public Node minCostNode(ArrayList<Node> nodes) {		
			Node n = nodes.get(0);
			
			for(int i = 0; i < nodes.size(); i++) {
				if(nodes.get(i).cost < n.cost) {
					n = nodes.get(i);
				}
			}

			return n;		
		}
	
	//calculates the number of tiles Misplaced between startState and goalState. 
	public int tilesMisplaced(int[][] startState, int[][] goalState) { 
	    int count = 0; 
	    for (int i = 0; i < startState.length; i++) 
	      for (int j = 0; j < startState[0].length; j++) 
	        if (startState[i][j] != 0 && startState[i][j] != goalState[i][j]) {
		           count++; 

	        }
	    return count; 
	    
	}
	/*
	 * returns the Node that has minimum tiles misplaced between the node and the goalState.
	 * Used for the best first search, since best first search has f(n) = h(n) and 
	 * h(n) = number of tiles misplaced. 
	 */
	public Node minTilesMisplaced(ArrayList<Node> nodes, int[][] goalState) {		
		Node n = nodes.get(0);
		
		for(int i = 0; i < nodes.size(); i++) {
			if(tilesMisplaced(nodes.get(i).state, goalState) < tilesMisplaced(n.state, goalState)) {
				n = nodes.get(i);
			}
		}

		return n;		
	}
	//calculates the f(n) for A*, h = number of tiles misplaced. 
		public Node minF(int[][] startState, ArrayList<Node> nodes) {
			
			Node n = nodes.get(0);
			for(int i = 0; i < nodes.size(); i++) {
				if(totalCost(startState, nodes.get(i).state) + (tilesMisplaced(startState, nodes.get(i).state)) < 
						totalCost(startState, n.state) + tilesMisplaced(startState, n.state)){
					n = nodes.get(i);
						
					}
			}
			return n;
		}
	
	//calculates the total Cost between startState and current n. 
	public int totalCost(int[][] startState, int[][] goalState) {
		return bfs(startState, goalState);
	}
	
	
	
	
	
	//This function takes an expanded array, and a state, and checks if the state is in the array. 
	public boolean isExpanded (ArrayList<int[][]> expanded, int[][] state) {
		for(int i = 0; i < expanded.size(); i++) {
			if(equal(expanded.get(i), state)) {
				return true;
			}
		}
		return false;
	}
	
	//prints the int[][] array. 
	public static void printState(int[][] state) {
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				System.out.print(state[i][j] + " ");
			}
		}
		System.out.println();
	}
	
	//prints the parent states of the root and the state of the root itself. 
	public void printPath(Node root){
		if(root == null){
			return;
		}
		printPath(root.getParent());
		printState(root.state);
		System.out.println();
	}
	
	/*
	 * checks if two states are equal, I created a new method because java equals() function does not 
	 * work on int[][]. 
	 */
	public boolean equal(int[][] startState, int[][] goalState) {
		for(int i = 0; i < startState.length; i++) {
			if(!Arrays.equals(startState[i], goalState[i])) {
				return false;
			}
		}
		return true;
	}
}