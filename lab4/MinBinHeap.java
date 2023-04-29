package lab4;

public class MinBinHeap {
	private Vertex[] heap;
	private int size = 0;
	//Constructor: set the key of vertex with label r to 0 and place that vertex
	//at the root; set the keys of the remaining vertices
	//to logical infinity and copy them in the heap;
	//for each vertex initialize heapIndex appropriately
	public MinBinHeap(Graph g, int r) {
		// Initialize size of graph into variable n
        int n = g.size;
        // Initialize heap with size n + 1
        heap = new Vertex[n + 1];

        // For loop used to fill the heap array with all vertices.
        for (int i = 0; i < n; i++) {
        	// Only gets initialized if the vertex isn't the root
            if (i != r) {
            	// Add the vertex to the heap
                heap[i+1] = g.vArray[i];
                // Sets the heapIndex of the vertex to i+1
                heap[i+1].heapIndex = i+1;
                // Sets the key to infinity
                heap[i+1].key = Graph.infinity;
            }
        }

        // Initializing the root
        // Adds the root to the heap
        heap[1] = g.vArray[r];
        // Sets the heapIndex of the vertex to 1
        heap[1].heapIndex = 1;
        // Sets the key to 0
        heap[1].key = 0;

        // Heapify for all the values starting from parents to root
        for (int i = n/2; i >= 1; i--) {
        	// Calls PercDown 
        	PercDown(i);
        }
    //end constructor
    }
    public MinBinHeap(int maxVertices) {
        heap = new Vertex[maxVertices + 1];
        size = 0;

        // Initialize the heap array to null values
        for (int i = 0; i < heap.length; i++) {
            heap[i] = null;
        }
    }
	//extractMin: return the vertex with the smallest key and remove it from
	//the heap; note that every time a change is made in the heap,
	//the heapIndex of any vertex involved in the change has to be updated
    // Returns if the heap is empty
	Vertex extractMin() {
        if (isEmpty()) {
            return null;
        }
		// Store the minimum vertex (heap[1]) in a new variable x
		Vertex x = heap[1];
		// Replace the root with the last vertex (size) then decrease the size
		heap[1] = heap[size--];
		heap[1].heapIndex = 1;
		// Calls PercDown which ensures the heap properties still exist
		PercDown(1);
		// Returns the min vertex
		return x;
	//End method
	}
	
	//decreasesKey: decrease the key of Vertex at index i in the heap;
	//newKey is the new value of the key; newKey is smaller than the old key
	//note that the heap may need to be reorganized
	void decreaseKey(int i, int newKey) {
		// Sets newKey at index i
		heap[i].key = newKey;
		// While loop that checks if the vertex is not at the root and if the current key is smaller than the parents (i/2) key
		while (i > 0 && heap[i].key < heap[(i-1)/2].key) {
			// Swap the vertex with its parent node using swap function
			swap(i, (i-1)/2);
			// Updates the index to parent index
			i = (i-1)/2;
		}
	//end method
	}
	
	
	public void PercDown(int i) {
		// 2*i is used to get the index of the left child
		int child = 2*i;
		// Stores current element in temp variable
		Vertex temp= heap[i];
		// While loop used to check if the current node has a child. 
		while (child <= size) {
			// If true, checks if the current node has a right child and if its key is less than left child key
			if (child != size && heap[child+1] != null && heap[child + 1].key < heap[child].key) {
				// Increment child which means it checks right child
				child++;
			}
			// If statements used to check if the childs key is less than that temp key (current node)
			if (heap[child] != null && heap[child].key < temp.key) {
				// If so, swap the current node with the child node
				heap[i] = heap[child];
				if (heap[i] != null) { 
					heap[i].heapIndex = i;
				}
				else {
					break;
				}
				// Update the index of the current node to the index of the childs node
				i = child;
				//get the index of the left child of the new current node
				child = 2*i;
			}
			// Else, break out of the loop if current node is in the position we are expecting it
			else break;
		}
		// Place the original child (new temp) into its expected position.
		heap[i] = temp;
		if (heap[i] != null) {
			// Update the heap index of the new node
			heap[i].heapIndex = i;			
		}
	}
	
	// Create swap which is used to swap 2 elements in the heaps
    private void swap(int f_elem, int s_elem) {
    	// Initialize temp vertex variable to store values
        Vertex temp;
        // Assign temp variable with the first element. This is used to save that value so we can reassign it later
        temp = heap[f_elem];
        // Swap 1st elem with 2nd elem
        heap[f_elem] = heap[s_elem];
        // swap 2nd elem with temp value (temp value is the old 1st elem that was stored earlier)
        heap[s_elem] = temp;
        // update heapindex of first elem
        heap[f_elem].heapIndex = f_elem;
        // update heapindex of 2nd elem
        heap[s_elem].heapIndex = s_elem;
    }
    public boolean isEmpty() {
        // Check if the heap is empty
        return size == 0;
    }
	public String toString(){
		// Initialize the output string
		String s = "\n The heap size is " + size + "\n The items’ labels are: \n";
		// Iterates through all the vertices in heap
		for(int i=1; i < size+1; i++) {
			// Add the label and "key" to the string
			s += heap[i].label + " key: ";
			// Add the key and new line to the string
			s += heap[i].key + "\n";
		} //end for loop
		// Return the output string
		return s;
	//End method
	}
// End class
}
