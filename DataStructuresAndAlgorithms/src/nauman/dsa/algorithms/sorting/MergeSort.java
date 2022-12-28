package nauman.dsa.algorithms.sorting;

/** 
 * Implementation of Insertion Sort Algorithm
 * 
 * Properties:
 * Space-Complexity: O(n) 
 * Time-Complexity: O(nlogn)
 * Best-case: O(nlogn)
 * Stable Sort
 * 
 * @author     Nauman
 * @version	   1.0
 */
public class MergeSort {
	public static void sort(int [] input) {
		mergeSort(input, 0, input.length-1);
	}
	
	private static void mergeSort(int [] input, int leftIndex, int rightIndex) {
		if (rightIndex - leftIndex < 1) {
			return;
		}
		int mid = (rightIndex + leftIndex)/2;
		mergeSort(input, leftIndex, mid);
		mergeSort(input, mid+1, rightIndex);
		
		
		merge(input, leftIndex, mid,rightIndex);
	}
	
	public static void merge(int [] input, int startLeft, int mid, int endRight) {
		int startLeftStore = startLeft;
		int [] res = new int [endRight - startLeft + 1];
		int index, endLeft, startRight ;
		
		startRight = (endLeft = mid) + 1;
		index = 0;
		
		while (startLeft <= endLeft && startRight <= endRight) {
			if (input[startLeft] <= input[startRight]) {
				res[index] = input[startLeft] ;
				startLeft++;
			} else {
				res[index] = input[startRight];
				startRight++;
			}
			index++;
		}
		
		while (startLeft <= endLeft) {
			res[index] = input[startLeft];
			startLeft++;
			index++;
		}
		
		while (startRight <= endRight) {
			res[index] = input[startRight];
			startRight++;
			index++;
		}
		for (index = 0; index < res.length; index ++) {
			input[startLeftStore+index] = res[index];
		}
	}
}
