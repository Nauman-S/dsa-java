package nauman.dsa.dataStructures;

import java.util.Arrays;

public class HashMap {
	private int [] arr;
	public HashMap() {
		arr = new int [10000000];
		Arrays.fill(arr, -1);
	}
	
	public void put(int key, int value) {
		arr[key] = value;
	}
	
	public int get(int key) {
		return arr[key];
	}
	
	public void remove(int key) {
		arr[key] = -1;
	}
}
