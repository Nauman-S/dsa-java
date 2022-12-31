package nauman.dsa.dataStructures;

public class Stack <E> {
	private static final int DEFAULT_CAPACITY = 10;
	
	private int size;
	
	private Object [] elements;
	
	public Stack () {
		size = 0;
		elements = new Object [DEFAULT_CAPACITY];
	}
	
	public void push (E item) {
		if (size + 1 > elements.length) {
			grow();
		}
		
		elements[size] = item;
		size++;
	}
	
	
	@SuppressWarnings("unchecked")
	public E poll () {
		if (size == 0) {
			throw new ArrayIndexOutOfBoundsException(size + " >= " +
                    size);
		}
		size--;
		return (E)elements[size];
	}
	
	@SuppressWarnings( "unchecked" )
	public E peek() {
		if (size == 0) {
			throw new ArrayIndexOutOfBoundsException(size + " >= " +
                    size);
		}
		
		return (E)elements[size-1];
	}
	
	public void clear () {
		size  = 0;
		elements = new Object [DEFAULT_CAPACITY];
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	public void grow () {
		int newCapacity = elements.length + elements.length >> 2;
		Object [] arr = new Object[newCapacity];
		
		for (int i = 0; i <size; i++) {
			arr[i] = elements[i];
		}
		elements = arr;
	}
}
