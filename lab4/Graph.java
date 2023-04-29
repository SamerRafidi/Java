package lab4;

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
	
	// Dijkstras algorithm. Code inspired by the psuedocode given in Topic 4.3 code
	// O(E+Vlogv)
	public String dijkstraSP(int i, int j){

        //set d(v)=infinity and  prev(v) = null for all vertices v
        for(int a=0;a < size;a++){
            vArray[a].key = infinity;
            vArray[a].prev = null;
        }
        // Create an empty min-priority queue
        MinBinHeap Q = new MinBinHeap(this, i); 
        
        //set d(s)=0. This means to set the key of the starting vertex as 0
        vArray[i].key=0;
        // Save the current size of the graph into current_size
        int current_Size = size;
        //while the queue is not empty
        while(current_Size != 0){
        	// Extract the vertex with the min key
            Vertex u = Q.extractMin(); 
            // Decrement the size of the graph since we extract a vertex
            current_Size -= 1;
            // If the extracted vertex is null (means empty queue), continue
            if (u == null) {
                continue; 
            }
            // Get the first edge from the adjacency list of u (extracted vertex)
            Edge v = u.adjList; 
            // Loops through the adjacency list for all edges
            while(v != null){
            	// If the end vertex is in the queue and the edge weight + key of vertex is less than key of the end.
                if(v.end.isInQ == true && (u.key + v.weight)< v.end.key){ 
                	// decrease the key of the end vertex with the value of the edge  weight + key of vertex is less than key of the end.
                    Q.decreaseKey(v.end.heapIndex, u.key + v.weight); 
                    // Set the previous vertex to the current vertex
                    v.end.prev=u;
                }
                // Go to next edge 
                v = v.next; 
            }
        }
        return shortestPath(i,j);
    }
	// Created private method for printing the output because I was getting some weird output strings. 
	// This also helps multiple functions
	private String output(int j) {
		// Initialize string
        String output = "";
        output += "path: v";
        // If vertex j's previous is null, return null
        if (vArray[j].prev == null){ 
        	return null;
        } 
        else {
        	// Create an array that represents the vertices on the path. 
            Vertex[] path = new Vertex[size];
            // K keeps track of which index we are on
            int index = 0;
            Vertex v = vArray[j];
            // While there are more vertices to go through
            while (v != null) {
            	// Add vertex to the path
            	path[index] = v;
            	// Increment index to show more vertices have been added
                index++;
                // Move to the next vertex
                v = v.prev;
            }
            for (int m = index - 1; m >= 0; m--) {
            	// Append the label to the output string
                output += path[m].label;
                // Checks if the current vertex being appended is in the first vertex path
                if (m != 0) { 
                	// Append , v to the output string
                	output += ", v"; 
                }
            }
            output += ", weight: " + vArray[j].key;
        }
        return output;    
    }
	// O(VE)
    public String bellmanFordSP(int i, int j) {
        // initialize the array by setting previous vertex to null and the key to infinity
        for(int k=0; k<size; k++) {
            vArray[k].prev = null;
            vArray[k].key = infinity;
        }
        vArray[i].key = 0;

        // Iterate through all the vertices
        for(int k=0; k<size-1; k++) {
        	// Iterate its adjacent edges for each vertex above
            for(int l=0; l<size; l++) {
            	// Get the current vertex
                Vertex u = vArray[l];
                // Get adjacency list of vertex above
                Edge edge = u.adjList;
                // While loop that keeps running till it iterates through all the adjacency list
                while(edge != null) {
                	// The end vertex of the current edge is stored 
                    Vertex v = edge.end;
                    // If statement used to check if the u vertex is not infinity (Hasn't been reached) and if the weight is greater current vertex
                    if(u.key != infinity && v.key > u.key + edge.weight) {  
                    	// If so, update distance of the vertex
                        v.key = u.key + edge.weight;
                        // Updated previous vertex to u
                        v.prev = u;
                    }
                    // Go to new edge
                    edge = edge.next;
                }
            }
        }

        // Loop through vertices in graph
        for(int k=0; k<size; k++) {
        	// Get the k index vertex from the array 
            Vertex u = vArray[k];
            // Get adjacency list of that vertex
            Edge edge = u.adjList;
            // While loop that keeps running until it loops through all the adjacency lists
            while(edge != null) {
            	// Get the vertex at the opposite side of the edge
                Vertex v = edge.end;
                // If that edge is larger, we know a negative weight exits. 
                if(v.key > u.key + edge.weight) {
                    return "negative-weight cycle!";
                }
                // Go to next edge
                edge = edge.next;
            }
        }
        return output(j);
    }
    
    // O(V+E)
    public String dagSP(int i, int j) {
    	// Create an array of sorted vertices.
        Vertex[] sort = topologicalSort();
        // If null, we know there is a cycle and we cannot use topological sort.
        if (sort == null) {
            return null;
        }
        // Initialize vertices
        for (int k = 0; k < size; k++) {
            vArray[k].prev = null;
            vArray[k].key = infinity;
        }
        // Iterate through vertices
        for (int k = 0; k < size; k++) {
        	// Vertices in topological sort saved in u 
            Vertex u = sort[k];
            // Get adjacency list of that vertex
            Edge edge = u.adjList;
            // While loop that keeps running until the adjacency list is complete
            while (edge != null) {
                Vertex v = edge.end;
                // Checks if the weight is smaller
                if (v.key > u.key + edge.weight) {
                	// Update the key 
                    v.key = u.key + edge.weight;
                    // Set previous vertex to one above
                    v.prev = u;
                }
                // Go to next edge
                edge = edge.next;
            }
        }
        return output(j);
    }
    // O(V+E)
    public Vertex[] topologicalSort() {
	    // Initialize an array called indegrees which will keep track of the indegrees of the vertices.
        int[] indegree = new int[size];
        // For each vertex in graph
        for (Vertex v : vArray) {
        	// Go through the adjacency list 
            Edge edge = v.adjList;
            // while loop that goes through all the edges
            while (edge != null) {
            	// Increments the edge for every indegree found.
                indegree[edge.end.label]++;
                // Goes to next edge
                edge = edge.next;
            }
        }
        
        // Queue created
        Queue<Vertex> queue = new Queue(size);
        // Goes through each vertex in the graph
        for (Vertex v : vArray) {
        	// If there are 0 indegrees for that vertex
            if (indegree[v.label] == 0) {
            	// Append that vertex to the queue
                queue.enqueue(v);
            }
        }
        
        // Create a new array for sorted arrays with the size of the graph
        Vertex[] sort = new Vertex[size];
        // Initialize index at 0
        int i = 0;
        // While loop that keeps running until the queue is empty
        while (!queue.isEmpty()) {
        	//Dequeue next vertex
            Vertex u = queue.dequeue();
            // Add vertex to the array at current index
            sort[i] = u;
            // Increment index
            i++;
            // Get adjacency list of the vertex that was just dequeued
            Edge edge = u.adjList;
            // While there are more edges
            while (edge != null) {
            	// Check the vertex at the current edge
                Vertex v = edge.end;
                // Decrement the indegree of the vertex by 1 
                indegree[v.label]--;
                // If the indegrees of the vertex is 0 
                if (indegree[v.label] == 0) {
                	// Add it to queue of vertices with no indegrees
                    queue.enqueue(v);
                }
                //go to next edge
                edge = edge.next;
            }
        }
        // This checks if the number of vertices added to the array are equal to size of the graph (same amount of vertices). 
        // If they aren't equal, it means we were successful 
        if (i != size) {
            return null;
        }
        // Return sorted array
        return sort;
    }
	
	public String shortestPath(int i, int j){
		// Initializes string
	    String s="";
	    // If topological sorting is possible, do a dagSP 
	    if(topologicalSort()!= null){
	        System.out.println("dagSP, ");
	        // Appends to output string
	        s += "dagSP, "+ dagSP(i, j);
	    }
	    // If not, 
	    else{	
	    	// Check if there are any negative weights in the graph. If there aren't do a dijkstras algo.
	        int NWeight = 0;
	        // For loop that goes through all the vertices and stops as soon as NWeight isn't 0 (has a negative)
	        for(int a = 0; a < size && NWeight == 0; a++){
	        	// The adjacency list for vertex a is initialized to v
	        	Edge v = vArray[a].adjList;
	        	// while loop that goes through the adjacency list 
	            while(v != null){
	            	// If loop used to see if there are any negative weights
	            	if(v.weight>0){
	            		// If a weight is found, set Nweight to 1 and leave the loop
	            		NWeight = 1; 
	            		break;
	            	}
	            	// Go to next vertex
	            	v = v.next;
	            }
	        }
	        // If no negative weights where found. 
	        if(NWeight==0){
	        	// Run the dijkstra algorithm and append the output to string (s)
	            System.out.println("dijkstraSP, ");
	            s+="dijkstraSP, "+dijkstraSP(i, j);
	        }
	        // If negative weights are found
	        else{
	        	// Run the bellmanFord algorithm as it is much more accurate for negative weights
	            System.out.println("bellmanFordSP, ");
	            // Append output to string (s)
	            s+="bellmanFordSP, "+bellmanFordSP(i, j);;
	        }
	    }
	    // Return output string
	    return s;
	}
}//end class
