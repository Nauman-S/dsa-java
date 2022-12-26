package nauman.dsa.algorithms.sorting;

/** 
 * Implementation of Insertion Sort Algorithm
 * 
 * Properties:
 * Space-Complexity: O(1) therefore In-Place
 * Time-Complexity: O(n^2)
 * Best-case: O(n)
 * Stable Sort
 * 
 * @author     Nauman
 * @version	   1.0
 */
public class InsertionSort {
	public static void sort (int [] input) {
		int temp;
		for (int i = 1; i < input.length; i++) {
			temp = input[i];
			int j = i - 1;
			for (; j >= 0 && input[j] > temp; j--) {
				input[j+1] = input[j];
			}
			input[j+1] = temp; 
		}
	}
}
