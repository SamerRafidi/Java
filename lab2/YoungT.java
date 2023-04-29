package lab2;

public class YoungT {
	private int[][] arr;
	private int finite;
	private int infinity;
	
	public YoungT(int k, int n, int infinity) {
		
		// If k is less than 10, make k = 10
		if (k < 10) {k = 10;}
		// If n is less than 10, make n = 10
		if (n < 10) {n = 10;}
		// If infinity is less than 100, make infinity = 100
		if (infinity < 100) {infinity = 100;}
		
		this.finite = 0;
		this.infinity = infinity;
		// Initializes matrix
		this.arr = new int[k][n];
		// Create a nested for loop that goes through each row and column and makes the value infinity (empty).
		for(int i = 0; i < k; i++) {
			for(int j = 0; j <n; j++) {
				arr[i][j] = infinity;
			}
		}
	}
    public YoungT(int[][] a) {
    	// Initialize values for the rows and columns
    	int k = a.length;
    	int n = a[0].length;
    	//Create a variable with the first value in the matrix (top left)
        int start = a[0][0];
        // Iterate through the matrix
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
            	// If the value of the element is larger than the value of the top left
                if (a[i][j] > start) {
                	// make the start equal to the current value
                    start = a[i][j];
                }
            }
        }
        // Set infinity to 10 x the largest array element
        this.infinity = 10 * start;
        // As explained in the tutorial, we want to allocate new memory and copy the data from the input array into the new array
     	// We start by creating a new array
        this.arr = new int[k][n];
        this.finite = 0;
        // In the tutorial, we were told to insert items 1 a time. To do so, I will create a nested loop to iterate through all the values
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
            	// copy all the old values into the new array
                this.arr[i][j] = a[i][j];
                this.finite++;
            }
        }
    }
    
	public int getNumElem() {
		return finite; 
	}
	
	public int getInfinity() {
		return infinity;
	}
	
	public boolean isEmpty() {
		// Checks if the first element is infinity or if there is 0 finite values
        if (arr[0][0] == infinity || finite == 0) {
        	// Return true if the statements above are met (matrix is empty)
            return true;
        }
        // Return false if the matrix is not empty
        return false;
    }
	public boolean isFull() {
		// Checks if the last value of the youngT matrix does not equal infinity (empty)
		if (arr[arr.length-1][arr[0].length-1] != infinity) {
			// If it is not infinity, return true which means its full
			return true;
		}
		// Return false otherwise 
		return false;
	}
	
	public boolean insert(int x){
		// Checks if the tableau is full or if x is larger than or equal to infinity
		if (isFull() || x >= infinity) {
			// If so, return false
			return false;
		}
		// Last index
		int i = arr.length - 1;
		int j = arr[0].length - 1;
		// Assign x to the last element in the tableau
		arr[i][j] = x;
		// Initialize row and col to store values for future swaps
		int row = i;
		int col = j;
		// Initialize temp to store values temporarily
		int temp;
		// While loop that keeps running as long as i and j are above 0 and the current value is not infinity (empty)
		while(infinity > arr[i][j] && (i > 0 || j > 0)) {
			// Current value gets 
			temp = arr[row][col];
			arr[row][col] = arr[i][j];
			arr[i][j] = temp;
			i = row;
			j = col;
			// If statement used to check if the value at the current index is less than the index above it 
			if (i-1>=0 && arr[i][j] < arr[i-1][j]) {
				// If so, update the row to be one row above since it is smaller
				row = i - 1;
				col = j;
			}
			// If statement used to check if the value at the current index is less than the index to the left of it
			if (i - 1 >= 0 && arr[row][col] < arr[i][j-1]) {
				row = i;
				// If so, update the column to the one to the left of it since it is smaller
				col = j-1;
			}
		}
		// Increment finite because there is a new value in the tableau
		finite++;
		// Return true because the insertion is complete
		return true;
		
	}
    public int readMin() throws RuntimeException {
    	// Calls isEmpty method. If true, throw exception
        if (isEmpty()) {
            throw new RuntimeException("YoungT is empty");
        }
        // returns the first element in the matrix which is the min value in a youngT 
        return arr[0][0];
    }

    public int deleteMin() throws RuntimeException {
    	// If the youngT is empty, throw an exception
        if (isEmpty()) {
            throw new RuntimeException("Tableau is empty");
        }
        // Initialize a variable which tracks the smallest element (top left of matrix)
        int start = arr[0][0];
        // This replaces the smallest element with an infinity
        arr[0][0] = getInfinity();
        // Initialize i and j which store the current position of the matrix
        int i = 0;
        int j = 0;
        // While loop that starts in the top left of the matrix
        while (i < arr.length && j < arr[0].length) {
        	// Initialize variables that represent the new positions (in this case, the current position)
            int nextI = i;
            int nextJ = j;
            // checks if there is a smaller element below the current position
            if (i + 1 < arr.length && arr[i+1][j] < arr[nextI][nextJ]) {
            	// If so, update the position of i to the one below
                nextI = i + 1;
                nextJ = j;
            }
            // Checks if there is a smaller element to the right of the current position
            if (j + 1 < arr[0].length && arr[i][j+1] < arr[nextI][nextJ]) {
                nextI = i;
                // If so, update the position of j to the one on the right
                nextJ = j + 1;
            }
            // Checks if the current position is the smallest
            if (i == nextI && j == nextJ) {
            	// If so, leave the for loop
                break;
            }
            // Update the current positions to the new ones found 
            arr[i][j] = arr[nextI][nextJ];
            // Reset i and j 
            i = nextI;
            j = nextJ;
        }
        // 
        arr[i][j] = getInfinity();
        finite--;
        // Return the smallest element
        return start;
    }
    
	public boolean find(int x) throws RuntimeException{
		// If the tableau is empty
		if(isEmpty()) {
			throw new RuntimeException("Tableau is empty");
		}
		// Checks if the value we are looking for is larger than or equal to infinity
		else if (x >= infinity) {
			throw new RuntimeException("x is larger than or equal to infinity");
		}
		// Starts in the top right in the matrix
		// i represents last column
		int i = arr.length -1;
		// j represents first row
		int j = 0;
		String s = "";
		// Keeps iterating if not back at the first column and and j is not at the last row. Basically iterating until the full matrix is checked
		while (i >= 0 && j < arr[0].length) {
			// if x is found in the matrix, return true
			if (arr[i][j] == x) {
				// Print the elements compared with x
				System.out.println(s);
				return true;
			}
			// If x is smaller than the value we are at, move left
			else if (arr[i][j] > x) {
				// Add the elements compared with x to the string
				s += arr[i][j] + ", ";
				// Move left
				i--;
			}
			else {
				// Add the elements compared with x to the string
				s += arr[i][j] + ", ";
				// Move down
				j++;
			}
		}
		// Print the elements compared with x
		System.out.println(s);
		// Return false if x isn't in the tableau
		return false;
		
	}

    public String toString() {
    	// Initializes variables for rows and columns. Keeps it organized
    	int k = arr.length;
    	int n = arr[0].length;
    	// Create empty string so we can append values in
        String s = "";
        // Iterates through the whole matrix
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
            	if (arr[i][j] == infinity) {
            		s += "inf";
            	}
            	else {
            		// Adds the values from the matrix into the string
            		s += arr[i][j];
            	}
                // Checks to make sure we aren't at the last element in the row
                if (j < n - 1) {
                	// If so, add a comma and a space. This is so we don't have an extra comma at the end.
                	s += ", ";
                }
            }
            // Checks to make sure we aren't at the last row
            if (i < k - 1) {
            	// If so, add a comma and a space
            	s += ", ";
            }
        }
        // Return the string
        return s;
    }
    
}
