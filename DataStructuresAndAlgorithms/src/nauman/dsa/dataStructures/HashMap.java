package nauman.dsa.dataStructures;

public class HashMap {
	public HashMap() {
		
	}
	
	public void put(int key, int value) {
		System.out.println("Inserted Key " + key + "And Value" + value);
	}
	
	public void get(int key) {
		System.out.println("Retrived key" + key);
	}
	
	public void remove(int key) {
		System.out.println("Key has been removed "+ key);
	}
}
