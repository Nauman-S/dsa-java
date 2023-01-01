package nauman.dsa.dataStructures;

public class ArrList<E> implements List<E> {
	
	private static final int DEFAULT_INITIAL_CAPACITY = 10;

	private Object [] elements;
	
	private int size;

	public ArrList(int initialCapacity) {
		
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal Capacity: "+  initialCapacity);
		}
		
		this.elements = new Object [initialCapacity];
		this.size = 0;
	}
	
	public ArrList() {
		this.elements = new Object [DEFAULT_INITIAL_CAPACITY];
		this.size = 0;
	}

	@Override
	public void add(E item) {
		if (size + 1 > elements.length) {
			grow();
		}
		elements[size] = item;
		size++;
	}

	@Override
	public void add (E item, int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(index);
		}
		if (size + 1 > elements.length) {
			grow();
		}
		
		for (int i = size - 1; i >= index ; i--) {
			elements[i+1] = elements[i];
		}
		elements[index] = item;
		size++;
	}

	@Override
	public int size() {
		
		return size;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		return (E)elements[index];
	}

	@Override
	public void remove(int index) {
		for (int i = index + 1; i <size ; i--) {
			elements[i-1] = elements[i];
		}
		size--;
	}

	@Override
	public void clear() {
		elements = new Object [DEFAULT_INITIAL_CAPACITY];
		size = 0;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E [] toArray() {
		Object [] arr = new Object [size];
		
		for (int i = 0; i < size; i++) {
			arr[i] = elements[i];
		}
		
		return (E [])arr;
	}
	
	private void grow() {
		int newCapacity;
		if (elements.length < DEFAULT_INITIAL_CAPACITY) {
			newCapacity = DEFAULT_INITIAL_CAPACITY;
		} else {
			newCapacity = elements.length + elements.length >> 2;
		}
		
		Object [] newElements = new Object [newCapacity];
		
		
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
	}

}
