package nauman.dsa.algorithms.sorting;

/** 
 * Implementation of Radix Sort Algorithm
 * 
 * Properties:
 * Space-Complexity: O(n) 
 * Time-Complexity: O(nd) where d is the maximum number of digits in a number
 * Stable Sort
 * 
 * Radix sort is useful where d < logn this occurs when the input array is large however the largest number has 
 * lesser than log(n) digits
 * 
 * @author     Nauman
 * @version	   1.0
 */
public class RadixSort {
	public static void sort(int [] input) {
		if (input.length < 1) {
			return;
		}
		int max = Math.abs(input[0]);
		for (int i = 1; i < input.length; i++) {
			max = Math.abs(input[i]) > max ? Math.abs(input[i]) : max;
		}
		
		int pow = 1;
		while (max!= 0) {
			countingSort(input, pow);
			max/=10;
			pow*=10;
		}	
		negativeNumberSort(input);
	}
	
	public static void countingSort (int [] input, int exp) {
		int [] count = new int [10];
		
		for (int i = 0; i < input.length; i++) {
			count [Math.abs((input[i]/exp)) % 10] ++;
		}
		
		for (int i = 1; i < count.length; i++) {
			count[i] += count[i-1];
		}
		
		int [] sorted = new int [input.length];
		
		for (int i = input.length - 1; i > -1; i--) {
			sorted[count[Math.abs((input[i]/exp)) % 10] - 1] = input[i];
			count[Math.abs((input[i]/exp)) % 10]--;
		}
		
		for (int i = 0 ; i < input.length; i++) {
			input[i] = sorted[i];
		}
	}
	
	public static void negativeNumberSort(int [] input) {
		int [] temp = new int [input.length];
		int index = temp.length-1;
		
		for (int i = input.length - 1; i > -1 ; i--) {
			if (input[i] >= 0) {
				temp[index] = input[i];
				index--;
			}
		}
		
		index = 0;
		for (int i = input.length - 1; i > -1 ; i--) {
			if (input[i] < 0) {
				temp[index] = input[i];
				index++;
			}
		}
		
		for (int i = input.length - 1; i > -1 ; i--) {
			input[i] = temp[i];
		}
	}
}
