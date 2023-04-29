package lab2;

public class MaxMHeap {
    private int m;
    private int size;
    private int[] keys;

    private void buildHeap() {
    	// Iterates starting from the middle of the heap to the root
        for (int i = (size/2) - 1; i >= 0; i--) {
        	// Calls heapify to check that the heap properties are applied
            Heapify(i);
        }
    }
    private void Heapify(int node) {
    	// Initializes variable for the largest current node 
        int largest = node;
        // Iterates through ranging from i to m (maximum value)
        for (int i = 1; i <= m; i++) {
        	// This equation can calculate the child given its parent node.
        	// m is the maximum children, node is the parent. i is a value being iterated. This equation was created using the lab manual
            int child = m * node + i;
            // Checks if the child is in the array and if the value of the child is bigger than the current largest value
            if (child < size && keys[child] > keys[largest]) {
            	// If so, "child" becomes the new largest value
                largest = child;
            }
        }
        
        // checks if the largest node is not the node itself
        if (largest != node) {
        	// If so, the current node value gets saved in a temp variable
            int temp = keys[node];
            // The largest value in the array switches positions with node
            keys[node] = keys[largest];
            // The temp variable (largest node) becomes the new node
            keys[largest] = temp;
            Heapify(largest);
        }
        
    }
    
    public MaxMHeap(int k, int n) {
    	// If k (value of m) is less than 2 
        if (k<2) {
        	// Set k to 3
            k = 3;
            // the value of k is assigned to m
            this.m = k;
        }
        // if n (size of the array) is less than 10
        if (n<10) {
        	// set n to 10
            n = 10;
        }
        // keys array is now initialized with size n
        keys = new int[n];
        // initializes size
        size = 0;
    }
    public MaxMHeap(int k, int[] a) {
    	// m is set to the value of k
    	this.m = k;
    	// if k is less than 2
        if (k<2) {
        	// assign m to 3 by default.
            this.m = 3;
        }
        // the size of the array "a" is stored in variable size
        size = a.length;
        // creates a new array "keys" with the size equal to array a (the input array)
        keys = new int[size];
        // Iterates from 0 to the value of size
        for (int i = 0; i < size; i++) {
        	// All the values are copied from the array "a" to "keys"
        	keys[i] = a[i];
        }
        // Call this method to put the new values of "keys" into the heap
        buildHeap();
    }
    
    public int getM() {
    	// Returns the value of m
        return m;
    }

    public int getSize() {
    	// Returns the size of the array
        return size;
    }

    public void insert(int newNode) {
    	// Checks if the size of the array equals the length of the array
        if (size == keys.length) {
        	// if so, a new array is created with more space for new elements
            int[] newArr = new int[keys.length * 2];
            // Iterate through the for loop from 0 to length of the array
            for (int i = 0; i < keys.length; i++) {
            	// Copying all the values from the old array into the new one 
                newArr[i] = keys[i];
            }
            
            keys = newArr;
        }
        // Insert a new node at the end of the array keys
        keys[size] = newNode;
        // Increase the size of the array by 1
        size++;
        int node = size - 1;
        // Checks if the node is bigger than the parent and does not equal 0 (is not the root). If so it swaps with the parent
        while (node != 0 && keys[node] > keys[parent(node)]) {
        	// The node value gets stored into a temp variable. This is done so we don't lose this value when it gets replaced in the array after
            int temp = keys[node];
            // The overwrites the node value with its parents value. The node moves up the array
            keys[node] = keys[parent(node)];
            // temp (original node) becomes the new parent node. The line above and this line swap the value positions
            keys[parent(node)] = temp;
            // the new node becomes the parent node which is then checked.
            node = parent(node);
        }
    }
    // Create a parent that helps with the insert function
    private int parent(int node) {
        return (node - 1) / m;
    }

    public int readMax() {
    	// If size is 0, this means the array is empty and there is no heap
        if (size == 0) {
        	// Throw exception if the heap is empty
            throw new RuntimeException("This heap is empty");
        }
        // Returns the first element in the array which represents the largest number in a max heap
        return keys[0];
    }

    public int deleteMax() {
    	// If size is 0, the heap is empty
        if (size == 0) {
            throw new RuntimeException("The heap is empty.");
        }
        // max is a variable which represents the first number in the array which is the maximum in a max heap
        int max = keys[0];
        // the root of the node (keys[0]) is replaced with the last node in the heap which is the property of a heap as learned in the lecture
        keys[0] = keys[size - 1];
        // The size of the heap is decreased as the max node is removed from the heap.
        size--;
        Heapify(0);
        // Max value is stored
        return max;
    }

    public String toString() {
    	// Initializes empty string "s" which allows us to store values later on.
        String s = "";
        // Initializes variable "depth" which is used to track what depth we are on in the tree (depth 0 is root, depth 1 is children of the root)
        int depth = 0;
        // Initializes variable "nodes" which is used to track how many nodes are in the depth x
        int nodes = 1;
        // for loop used to iterate through all the nodes in the heap
        for (int i = 0; i < size; i++) {
        	// if i == nodes, we know that we reached the end of this depth
            if (i == nodes) {
            	// If the statement is true, move on to the next depth by iterating depth by 1 
                depth++;
                // this equation calculates the amount of nodes in the next depth. 
                nodes += Math.pow(m, depth);
                // Adds a comma and a space between each value 
                s += ", ";
            } 
            // if i is not 0, we know that we are not at the beginning of the string
            else if (i != 0) {
            	// add a comma and a space 
                s += ", ";
            }
            // add the values in the array to the string
            s += keys[i];
        }
        // return the string
        return s;
    }

    public static void sortArray(int k, int[] a) {
    	// Create a new max heap with the given inputs
        MaxMHeap heap = new MaxMHeap(k, a);
        // Create variable size which is the length of the array - 1 
        int size = a.length-1;
        // While loop that keeps being ran to remove the maximum key and storing it into the array
        while (size>=0) {
        	a[size] = heap.deleteMax();
        	size--;
        }
    }
}
