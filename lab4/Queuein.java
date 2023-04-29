package lab4;

public class Queue {
	private int[] queue;
	private int start;
	private int end;
	private int current_size;
	
	// Creates a new queue
	public Queue(int size) {
		queue = new int[size];
		start = 0;
		end = -1;
		current_size = 0;
	}
	// Checks if the size of the queue is 0, if so, return true
	public boolean isEmpty() {
		return current_size == 0;
	}
	
	// Checks if the size of the queue is = to the max size of the queue, if so, return true
	public boolean isFull() {
		return current_size == queue.length;
	}
	
	public void enqueue(int element) {
		// Checks if queue is full. If so, throw an exception
		if (isFull()) {
			throw new RuntimeException("This queue is full");
		}
		// Moves the last element up by 1
		end = end + 1;
		// Adds the new element to the end of the queue
		queue[end] = element;
		// increment the current size of queue 
		current_size++;
	}
	public int dequeue() {
		// Checks if queue is empty, if so, thrown an exception
		if (isEmpty()) {
			throw new RuntimeException("This queue is empty");
		}
		// Make element take in the value at the start of queue
		int element = queue[start];
		// Increment the start by 1 to remove it
		start = start + 1;
		// Decrement size of queue 
		current_size--;
		// Return the value that was removed
		return element;
	}

}
