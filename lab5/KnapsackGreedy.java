package lab5;
import java.util.*;
public class KnapsackGreedy {
    private int num; // number of items
    private int targetWeight; // capacity of knapsack
    private int[] value; // values of items
    private int[] weight; // weights of items

    // Constructor to initialize instance fields
    public KnapsackGreedy(String input) {
    	// Create scanner object to read input
        Scanner scanner = new Scanner(input);
        // Reads first two ints and store them as num and W 
        num = scanner.nextInt();
        targetWeight = scanner.nextInt();
        // Create 2 new array for value and weight. Length of array is num
        value = new int[num];
        weight = new int[num];
        // For loop used to iterate through each item in the input
        for (int i = 0; i < num; i++) {
        	// Store each value of their value and weights
        	value[i] = scanner.nextInt();
        	weight[i] = scanner.nextInt();
        }
    }

    public static String fractional(String input) {
    	// Create a knapsackgreedy object using the input string
        KnapsackGreedy knapsack = new KnapsackGreedy(input);
        // Store number of items and maximum weight of the knapsack in n and W
        int n = knapsack.num;
        int W = knapsack.targetWeight;
        // Call sort method to sort items by the value to weight ratio
        int[] ind = sort(knapsack.value, knapsack.weight, n);

        // Initialize total value, total weight, and chosen variables.
        double totalValue = 0;
        double totalWeight = 0;
        double[] picked = new double[n];
        // For loop to go through the items
        for (int i = 0; i < n; i++) {
        	// Get the index of current item
            int index = ind[i];
            // Checks if the item can fit
            if (knapsack.weight[index] <= W - totalWeight) {
            	// Add the item to the total value and weight
            	picked[index] = 1;
                totalValue += knapsack.value[index];
                totalWeight += knapsack.weight[index];
            } 
            else {
            	// Else, calculate the amount of weight left in the knapsack
                double Weight_left = W - totalWeight;
                // Calculate the fraction of the item that can be added into the knapsack
                double fraction = Weight_left / knapsack.weight[index];
                // Chosen[index] is set to the fraction that can be added
                picked[index] = fraction;
                // Update totalValue by adding the weight and value of the item that is being added
                totalValue += fraction * knapsack.value[index];
                totalWeight += fraction * knapsack.weight[index];
                // Break out of the loop since knapsack is full
                break;
            }
        }
        // Call toString method 
        return toString(picked, totalValue, totalWeight, n);
    }
    public static String greedy01(String input) {
    	// Create a knapsackgreedy object using the input string
        KnapsackGreedy knapsack = new KnapsackGreedy(input);
        // Store number of items and maximum weight of the knapsack in n and W
        int n = knapsack.num;
        int W = knapsack.targetWeight;
        
        // Call sort method to sort items by the value to weight ratio
        int[] ind = sort(knapsack.value, knapsack.weight, n);

        // Initialize total value, total weight, and chosen variables.
        double totalValue = 0;
        double totalWeight = 0;
        double[] picked = new double[n];
        // For loop to go through the items
        for (int i = 0; i < n; i++) {
        	// Get the index of current item
            int index = ind[i];
            // Checks if the item can fit
            if (knapsack.weight[index] <= W - totalWeight) {
            	picked[index] = 1;
                // Add the item to the total value and weight
                totalValue += knapsack.value[index];
                totalWeight += knapsack.weight[index];
            }
        }
        
        // Call toString method 
        return toString(picked, totalValue, totalWeight, n);
    }
    
    private static int[] sort(int[] value, int[] weight, int n) {
        // Create a new array with length n which represents the value to weight ratio 
        double[] VWratio = new double[n];
        // for loop that runs through each item and calculates the value to weight ratio. 
        for (int i = 0; i < n; i++) {
        	// Calculate ratio
        	VWratio[i] = value[i] / (double) weight[i];
        }
        // Create a new array indices with length n
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        // nested for loop used to sort items by their value to weight ratio
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
            	// If the value to weight ratio is lower
                if (VWratio[i] < VWratio[j]) {
                	// temp save the original value
                    double temp = VWratio[i];
                    // Swap the value from index j to the i as its bigger
                    VWratio[i] = VWratio[j];
                    // Return the temp value back to j
                    VWratio[j] = temp;
                    
                    // Swap indices 
                    int tempIndex = indices[i];
                    indices[i] = indices[j];
                    indices[j] = tempIndex;
                }
            }
        }
        // Return indices
		return indices;
    }
    
    public static String toString(double[] chosen, double totalValue, double totalWeight, int n) {
    	// Initialize output string
    	String output = "";
    	// Loop through index and add a comma and a space
    	for (int i = 0; i < n; i++) {
    	    output += chosen[i] + ", ";
    	}
    	// Add the total value and total weight to output string
    	output += "total value = " + totalValue + ", " + "total weight = " + totalWeight;
    	
    	// Return output string
    	return output;
    }
}

