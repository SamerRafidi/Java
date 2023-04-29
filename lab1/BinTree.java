package lab1;

import java.util.ArrayList;

public class BinTree {
	
	private TNode root = new TNode(null, null, null);
	public BinTree(String[] a) throws IllegalArgumentException{
        TNode n = root;
        // Iterates through the length of the string
        for (int i = 0; i < a.length; i++){
            n = root;
            for (int j = 0; j < a[i].length(); j++){
            	// Checks if the string is 0, we know this is a left node
                if (a[i].charAt(j) == '0') {
                	// First condition checks if node is null and j < length of string -1 . 
                	//This means that the string has not been iterated through all the way which means we are missing nodes
                    if (n.left == null && j < a[i].length() - 1) {
                    	// If so, create a new node
                        n.left = new TNode(null, null, null);
                        // Assign new node to n
                        n = n.left;
                    }
                    // Second condition checks if the node is null and j == length of string -1. 
                    //This means the string is on the last node
                    else if (n.left == null && j == a[i].length() - 1) {
                    	// If so, create a new node with "c" + i which is the codeword that the node represents (example c0, c1). 
                        n.left = new TNode("c" + i, null, null);
                        n = n.left;
                    }
                    // Third condition is if the left is not a null. 
                    else if (n.left != null && n.left.data == null && j < a[i].length() - 1){
                        n = n.left;
                    }
                    else {
                        throw new IllegalArgumentException("Prefix condition violated!");
                    }
                }
                // Checks if the string is 1, we know this is a right node
                else if (a[i].charAt(j) == '1') {
                	// First condition checks if node is null and j < length of string -1 . This means that the string has not been iterated through all the way which means we are missing nodes
                    if (n.right == null && j < a[i].length() - 1) {
                    	// If so, create a new node
                        n.right = new TNode(null, null, null);
                       // Assign new node to n
                        n = n.right;
                    }
                    // Second condition checks if the node is null and j == length of string -1. This means the string is on the last node
                    else if (n.right == null && j == a[i].length() - 1) {
                    	// If so, create a new node with "c" + i which is the codeword that the node represents (example c0, c1). 
                        n.right = new TNode("c" + i, null, null);
                        n = n.right;
                    }
                    // Third condition is if the left is not a null.  
                    else if (n.right != null && n.right.data == null && j < a[i].length() - 1){
                        n = n.right;
                    }
                    else {
                        throw new IllegalArgumentException("Prefix condition violated!");
                    }
                }
                else {
                    throw new IllegalArgumentException("Invalid argument!");
                }
            }
        }
    }
	
	public void printTree() {
		printTree(root);
	}
	private void printTree(TNode t) {
		if (t!=null) {
			printTree(t.left);
			if (t.data == null)
				System.out.print("I ");
			else
				System.out.print(t.data + " ");
			printTree(t.right);
		}
		
	}
	public int height() {
		if (root == null) {
			return 0;
		}
		// Recursive call on the height.
		return height(root);
	}
	
	private int height(TNode node) {
		// If root is null, this means the node is at the end.
		if (node == null) {
			return -1;
		}
		// Calculates the height of the left node and right node
		int LHeight = height(node.left);
		int RHeight = height(node.right);

		// Returns the height of the tree from whichever side is highest.+1 accounts for the leaf node
		return 1 + Math.max(LHeight, RHeight);
	}
	
	public ArrayList<String> getCodewords(){
		// Initialization for codewords
        ArrayList<String> codewords = new ArrayList<String>();
        // Call initial recursive call.
        getCodewords(root, "", codewords);
        return codewords;
    }

    private void getCodewords(TNode n, String s, ArrayList<String> codewords){
    	// if node is null, return which is the exit condition. 
        if (n == null){
        	return;
        }
        // if data is not null, add it to the array
        if (n.data != null){
                codewords.add(s);
         }
         //Traverses left and right to find codewords. 
         getCodewords(n.left, s + "0", codewords);
         getCodewords(n.right, s + "1", codewords);
        }
    
    public String[] convert() {
    	// Creates a type string with the equation for max # of nodes in a tree with height(root). Uses (int) because math.pow returns a double but we want an integer
        String[] converted = new String[(int) Math.pow(2, height(root)+1)];
        convert(root, 1, converted);
        return converted;
    }
    private void convert(TNode n, int i, String[] converted){
    	// If node is not null, check if the data is null. if so, add "I" into the string array at index i 
        if (n != null){
            if (n.data == null){
                converted[i] = "I";
            } 
            // If data is not null, add the data to the string array at index i
            else{
                converted[i] = n.data;
            }
            // Recursive call. 2*i and (2*i) + 1 are equations to find the index of left and right nodes in the array. 
            convert(n.left, 2*i, converted);
            convert(n.right, (2*i) + 1, converted);
        }
    }
    public String encode(ArrayList<String> a){
    	// Creates a string s = "" to help with the addition later on
        String s = "";
        TNode n;
        // for loop used to iterate through the array list
        for (int i = 0; i < a.size(); i++){
            n = root;
            // while loop used to check if the data is null. 
            while(n.data == null){
            	// If the array list at index i equals the data in the left node. Add a 0 the codeword
                if((a.get(i)).equals(n.left.data)){
                    s = s + "0";
                    n = n.left;
                }
                // if the array at index i doesn't equal the data in the left node, it is equal to right so a 1 is added to the codeword
                else{
                    s = s + "1";
                    n = n.right;
                }
            }
        }
        return s;
    }
    public ArrayList<String> decode(String s) throws IllegalArgumentException {
        ArrayList<String> decoded = new ArrayList<String>();

        TNode n = root;
        // For loop used to iterate through the string
        for(int i = 0; i < s.length(); i++){
        	// If there is a 0 in the string, then we know that it will go to the left.
            if (s.charAt(i) == '0'){
                n = n.left;
            }
            // If there is a 1 in the string, then we know that it will go to the right
            else if (s.charAt(i) == '1'){
                n = n.right;
            }
            // If the character is not a 0 or 1, throw an exception.
            else{
                throw new IllegalArgumentException("Input is not binary");
            }
            // Checks if the node is a leaf (no children) by checking if both sides are null
            if (n.left == null && n.right == null){
            	// If so, add the data to decoded
                decoded.add(n.data);
                // Reset node
                n = root;
            }
        }
        // Checks if the node is not at the root
        if (n != root){
            throw new IllegalArgumentException("Input stream cannot be parsed into a sequence of codewords");
        }
        
        return decoded;
    }
    public String toString(){
    	// Initialize variable s
    	String s = "";
    	// Get the codewords from function getCodewords()
    	ArrayList<String> CDS = getCodewords();
    	// Iterate through CDS and put them into a string
    	for(String codeword : CDS) {
    		s = s + codeword + " ";;
    	}
    	return s;
    }
}




