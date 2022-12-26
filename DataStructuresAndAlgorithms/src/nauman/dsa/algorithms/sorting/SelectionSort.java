package nauman.dsa.algorithms.sorting;

/** 
 * Implementation of Selection Sort Algorithm
 * 
 * Properties:
 * Space-Complexity: O(1) therefore In-Place
 * Time-Complexity: O(n^2)
 * Best-Case:O(n^2)
 * Not Stable Sort
 * 
 * @author     Nauman
 * @version	   1.0
 */

public class SelectionSort	 {
	
	public static void sort(int[] input) {
		int smallest, temp;
		for (int i = 0; i < input.length-1; i ++) {
			smallest = i;
			for (int j = i+1; j < input.length; j++) {
				if (input[j] < input[smallest]) {
					smallest = j;
				}
			}
			temp = input[i];
			input[i] = input[smallest];
			input[smallest] = temp;
		}
	
	}
	
}