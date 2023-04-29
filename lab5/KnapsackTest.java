package lab5;

public class KnapsackTest {
	public static void main( String[] args) {
		
		String s1= "4 8 7 10 2 3 1 5 9 2";
		System.out.println("Input: " + s1);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s1));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s1));
		
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s1));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s1));
		
		String s2= "1 8 7 3";
		System.out.println();
		System.out.println("Input: " + s1);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s2));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s2));
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s2));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s2));
		
		String s3= "5 12 9 10 1 4 5 6 5 6 1 5";
		System.out.println();
		System.out.println("Input: " + s1);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s3));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s3));
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s3));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s3));

	}
	
}
