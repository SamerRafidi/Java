package lab3;

import java.util.Scanner;
public class Graph {
	public static int infinity = 10000; //logical infinity
	Vertex[] vArray; //array of vertices
	int size=0; //number of vertices
//Constructor: construct the graph described by inputString;
//the string contains only non-negative
//integers separated by white spaces; the first integer is
// the number of vertices; each of the following triple of integers
//specifies an edge, namely end1, end2 and weight; you may assume
//that the input string respects the format

	public Graph(String inputString) {
		Scanner input = new Scanner(inputString);
		size = input.nextInt();
		vArray = new Vertex[size];
		// fill the array of vertices;
        for (int i = 0; i < size; i++) {
        	// create the Vertex objects
            vArray[i] = new Vertex(i);
        }
		//read the info from the string. Initialize the variables
		int end1;
		int end2; 
		int w;
		// While loop that keeps running as long as there is another edge
		while(input.hasNext()) {
			//read next edge
			end1 = input.nextInt();
			end2 = input.nextInt();
			w = input.nextInt();
			//create two edge objects; insert them in adjList of end1 and end2, at the beginning
			// Create new edge, edge1 which is between end1 and end2 with weight w
            Edge edge1 = new Edge(w, vArray[end1].adjList, vArray[end2]);
            // Add edge1 to the beginning of adjList of end1.
            vArray[end1].adjList = edge1;
            // Create new edge, edge2 which is between end2 and end1 with weight 1. 
            Edge edge2 = new Edge(w, vArray[end2].adjList, vArray[end1]);
            // Add edge2 to the beginning of adjList of end2
            vArray[end2].adjList = edge2;
		}
	// End constructor
	}
	public String adjListString() {
		Edge p; //edge pointer
		// Creates empty string with variable s
		String s = " ";
		for(int i=0; i<size; i++) {
			//p points to first edge in adjList of vertex i
			p = vArray[i].adjList; 
			while(p != null) {
				s += " \n edge: (v" + i +", v" + p.end.label + "), weight: " + p.weight;
				//move to next edge
				p = p.next; 
			}//end while
		}// end for
		// Return output string
		return s;
	} // End method
		//minST: find an MST using Prim’s algorithm, where the starting vertex
		//has label r; return a string that lists all edges in the MST,
		//in the order they were found; see the output of the test class
		//for clarification on the format of the string
	//O(E+logV)
    public String minST(int r) {
        // Initialize arrays to keep track of visited vertices and the minimum edge weight to connect each vertex to the MST
        // Initialize arrays to keep track of visited vertices
        boolean[] vVertices = new boolean[size];
        // Initialize array to keep track of minimum edge weight to connect each vertex to the MST
        int[] minW = new int[size];
        // Initialize array to keep track of previous vertex 
        int[] prevV = new int[size];

        // For loop used to go through the array 
        for (int i = 0; i < size; i++) {
        	// Sets boolean to false which represents the vertex has not been visited
        	vVertices[i] = false;
        	// Set the minimum weight to infinity 
        	minW[i] = infinity;
        	// Set previous vertex to -1
        	prevV[i] = -1;
        }

        // Set boolean to true which means the root has been visited
        vVertices[r] = true;
        // Set weight of root to 0
        minW[r] = 0;

        // Initialize the string
        String s = "MST: ";

        // Repeat until all vertices have been visited
        for (int i = 1; i < size; i++) {
            // Find the minimum weight edge connecting a visited vertex to an unvisited vertex
        	// Initialize min value to infinity
            int minWeight = infinity;
            // Initialize vertex 1 to -1
            int v1 = -1;
            // Initialize vertex 2 to -1
            int v2 = -1;
            // For loop used to check through all vertices to find the edge with minimum weight
            for (int j = 0; j < size; j++) {
            	// Checks if the vertex and index j has been visited
                if (vVertices[j]) {
                	// If so, look at all edges connected to this vertex
                    Edge e = vArray[j].adjList;
                    // While loop keeps running until there are no more edges in the list 
                    while (e != null) {
                    	// If statement that checks if the end of the edge has not been visited and if the weight is less than current min
                        if (!vVertices[e.end.label] && e.weight < minWeight) {
                        	// Update the new weight to min
                        	minWeight = e.weight;
                            // Update the vertex 1 with new vertex
                            v1 = j;
                            // Update vertex 2 with the other vertex thats connected to the same edge as v1
                            v2 = e.end.label;
                        }
                        // Move to next edge
                        e = e.next;
                    }
                }
            }

            // Add the edge found to the string 
            s += " (v" + v2 + " , v" + v1 + ")";
            // Change boolean to true as we visited the vertex
            vVertices[v2] = true;
            // Update the minimum weight to the new min weight of vertex 2
            minW[v2] = minWeight;
            // Update the previous vertex
            prevV[v2] = v1;
        }
        // Return output string
        return s;
    }
		//return a two-dim array that represents the adjacency matrix of the graph
	public int[][] adjMatrix(){
		// Create a new 2D array with the dimensions of size and size
		int[][] arr = new int[size][size];
        
        // For loop that goes through every row and column of the 2D array
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
            	// Fill every value in the array with infinity
            	arr[i][j] = infinity;
            }
        }
        
        // For loop used to go through the edges 
        for(int i=0; i<size; i++) {
        	// Get the first edge for the vertex
            Edge current = vArray[i].adjList;
            // While loop used if there are more edges
            while(current != null) {
            	// If so, assign the weight of the edge to the cell i x e1.end.label 
            	arr[i][current.end.label] = current.weight;
            	// Move to the next edge
                current = current.next;
            }
        }
        // Return 2D array
        return arr;
	}//end method
}//end class
