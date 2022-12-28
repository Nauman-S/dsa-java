package nauman.dsa.algorithms.sorting;

public class QuickSort {
	public static void sort(int [] input) {
		quickSort(input, 0, input.length-1);
	}
	
	private static void quickSort(int [] arr, int start, int end) {
		if (end - start < 1) {
			return;
		}
		int pivotIndex = partition(arr, start, end);
		quickSort(arr, start, pivotIndex - 1);
		quickSort(arr, pivotIndex + 1, end);
		
	}
	
	private static int partition(int [] arr, int start, int end) {
		int pivot = arr[start];
		int lt = start+1;
		int temp;
		for (int i = lt; i < end+1 ; i++) {
			if (arr[i] < pivot) {
				temp = arr[i];
				arr[i] = arr[lt];
				arr[lt] = temp;
				lt++;
			}
		}
		lt--;
		temp = arr[lt];
		arr[lt] = pivot;
		arr[start] = temp;
		return lt;
		
	}
}
