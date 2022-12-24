package nauman.dsa.algorithms.sorting;

/** 
 * Implementation of Bubble Sort Algorithm
 * 
 * Properties:
 * Space-Complexity: O(1) therefore In-Place
 * Time-Complexity: O(n^2)
 * Best-Case: O(n)
 * Stable Sort
 * 
 * @author     Nauman
 * @version	   1.0
 */
public class BubbleSort {
	public static int [] sort(int [] input) {
		
		int temp;
		boolean isSorted;
		for (int iteration = 0; iteration < input.length-1; iteration++) {
			isSorted = true;
			for (int index = 1; index < input.length-iteration; index++) {
				if (input[index] < input[index-1]) {
					temp = input[index];
					input[index] = input[index-1];
					input[index-1] = temp;
					isSorted = false;
				}
			}
			if (isSorted) {
				break;
			}
		}
		
		return input;
	}
}
