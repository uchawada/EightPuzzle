package finalPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//Node class, responsible for implementing the successor functions. 
public class Node {
	

	public int[][] state;
	public int[][] goalState;
	public String action;
	public Node parent; 
	public	int depth; 
	public int cost; 
	public int totalCost; 
	
	
	//constructor for the Node. 
	public Node(int[][] state, Node parent, String action,int cost, int depth){
		this.state = state;
		this.parent = parent; 
		this.action = action; 
		this.depth = depth; 
		this.cost = cost;
	}
	
	/*
	 * gets all the children of the current state and returns instance of children nodes,
	 * with its action, cost and depth. 
	 * Uses move method to identify the action, and swaps the tiles accordingly. 
	*/
	public ArrayList<Node> neighbors(){
		
		ArrayList<Node> child = new ArrayList<Node>();
				
		if (state[0][0] == 0){		
			Node n = new Node((move(state, 0, 0, "RIGHT")), null, "RIGHT", state[0][1], this.depth + 1); child.add(n);
			n = new Node((move(state, 0, 0, "DOWN")), null, "DOWN", state[1][0], this.depth + 1); child.add(n);

		}
		
		else if (state[0][1] == 0){
			Node n = new Node((move(state, 0, 1, "DOWN")), null, "DOWN", state[1][1], this.depth + 1); child.add(n);
			n = new Node(move(state,0,1,"LEFT"), null, "LEFT", state[0][0], this.depth + 1);child.add(n);
			n = new Node(move(state,0,1,"RIGHT"), null, "RIGHT", state[0][2], this.depth + 1);child.add(n);
		}
		
		else if (state[0][2] == 0){
			Node n = new Node((move(state, 0, 2, "DOWN")), null, "DOWN", state[1][2], this.depth + 1);child.add(n);
			n = new Node(move(state,0,1,"LEFT"), null, "LEFT", state[0][1], this.depth + 1);child.add(n);
		}
		
		else if (state[1][0] == 0){
			Node n = new Node((move(state, 1, 0, "DOWN")), null, "DOWN", state[2][0], this.depth + 1);child.add(n);
			n = new Node(move(state,1,0,"RIGHT"), null, "RIGHT", state[1][1], this.depth + 1);child.add(n);
			n = new Node(move(state,1,0,"UP"), null, "UP", state[0][0],this.depth + 1);child.add(n);
	}
	
		else if (state[1][1]== 0){
			Node n = new Node((move(state, 1, 1, "DOWN")), null, "DOWN", state[2][1],this.depth + 1);child.add(n);
			n = new Node((move(state, 1, 1, "RIGHT")), null, "RIGHT", state[1][2],this.depth + 1);child.add(n);
			n = new Node((move(state, 1, 1, "LEFT")), null, "LEFT", state[1][0],this.depth + 1);child.add(n);
			n = new Node((move(state, 1, 1, "UP")), null, "UP", state[0][1],this.depth + 1);child.add(n);
		}	
		
		else if (state[1][2]== 0){
			Node n = new Node((move(state, 1, 2, "DOWN")), null, "DOWN", state[2][2],this.depth + 1);child.add(n);
			n = new Node((move(state, 1, 2, "LEFT")), null, "LEFT", state[1][1],this.depth + 1);child.add(n);
			n = new Node((move(state, 1, 2, "UP")), null, "UP", state[0][2],this.depth + 1);child.add(n);
		}
	
		else if (state[2][0]== 0){
			Node n = new Node((move(state, 2, 0, "UP")), null, "UP", state[1][0],this.depth + 1);child.add(n);
			n = new Node((move(state, 2, 0, "RIGHT")), null, "RIGHT", state[2][1],this.depth + 1);child.add(n);
		}
		
		else if (state[2][1]== 0){
			Node n = new Node((move(state, 2, 1, "LEFT")), null, "LEFT", state[2][0],this.depth + 1);child.add(n);
			n = new Node((move(state,2,1,"UP")), null, "UP", state[1][1],this.depth + 1);child.add(n);
			n = new Node((move(state,2,1,"RIGHT")), null, "RIGHT", state[2][2],this.depth + 1);child.add(n);
		}
		
		else if (state[2][2]== 0){
			
			Node n = new Node((move(state,2,2,"UP")), null, "UP", state[1][2],this.depth + 1);child.add(n);
			n = new Node((move(state,2,2,"LEFT")), null, "LEFT", state[2][1],this.depth + 1);child.add(n);
		}
		
		return child; 
		
	}
	
	/*
	 * Swaps the tiles according to the action. 
	 * For eg, if the action is Right, the 0 is swapped with the tile to its right. 
	 */
	public static int[][] move(int[][] state, int x, int y, String position){
		int[][] child = new int[3][3];

		if(position == "RIGHT"){
			child = Node.swap(state, x, y, x, y+1);

		}
		if(position == "LEFT"){
			child = Node.swap(state, x, y, x, y-1);

		}
		if(position == "UP"){
			child = Node.swap(state, x, y, x-1, y);

		}
		if(position == "DOWN"){
			child = Node.swap(state, x, y, x+1, y);
		}
		return child;
	}

	/*
	 * Creates a copy of the state, and returns an int[][] with the swapped tiles. 
	 */
	public static int[][] swap(int[][] state, int x1, int y1, int x2, int y2){
		int[][] arr = new int[3][3];
		for (int i = 0; i < 3; i++){
			arr[i]= Arrays.copyOf(state[i], state[1].length);
		}
		int temp = arr[x1][y1];
		arr[x1][y1] = arr[x2][y2];
		arr[x2][y2] = temp;	

		return arr;
	}
	
	/*
	 * Gets the parent for the node. 
	 */
	public Node getParent() {
		return this.parent;
	}
	
}

