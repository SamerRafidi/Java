package lab4;
public class Queue<size> {
	// Store the number of elements in the queue
    private int size;
    // Stores the index of the head in the queue
    private int head;
    // Stores the index of the tail in the queue
    private int tail;
    // Stores all the elements in the queue as an array
    private Vertex[] vertices;

    public Queue(int size) {
    	// Create an array with the given size "size"
        this.vertices = new Vertex[size];
        // Initialize head index to the first element
        this.head = 0;
        // Initialize the tail index to the last element (-1 because its empty)
        this.tail = -1;
        // Initialize the size of the array to 0 (its empty)
        this.size = 0;
    }
    
    //isFull returns true if the size == the lengths of the array (full).
    public boolean isFull() {
        return size == vertices.length;
    }

    // isEmptyy returns true if the size == 0 (empty)
    public boolean isEmpty() {
        return size == 0;
    }

    //enqueue adds elements to the array (queue)
    public void enqueue(Vertex vertex) {
    	// Increments the tail 
    	tail++;
    	// Adds the element to the end of the queue
        vertices[tail] = vertex;
        // Increments the size of the array (queue)
        size++;
    }

    // dequeue removes an element from the array and returns it
    public Vertex dequeue() {
    	// Saves the value of the head
        Vertex vertex = vertices[head];
        // Sets the value of the first element to null to ensure we don't lose that memory
        vertices[head] = null;
        // Increment the head
        head++;
        // Decrement the size of the array (queue)
        size--;
        // Returns the value that was lost
        return vertex;
    }
}


