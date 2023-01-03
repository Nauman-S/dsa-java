package nauman.dsa.dataStructures;

public class ArrayQueue <E> implements Queue<E> {
	
	private int DEFAULT_INITIAL_CAPACITY = 10;
	
	private int front;
	
	private int back;
	
	private Object [] elements;
	

	public ArrayQueue() {
		super();
		front = back = 0;
		elements = new Object [DEFAULT_INITIAL_CAPACITY];
	}

	@Override
	public void enqueue(E item) {
		if ((back+1)%elements.length == front) {
			grow();
		}
		elements[back] = item;
		back = (back+1)%elements.length;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new ArrayIndexOutOfBoundsException(0 + " >= " +
                    0);
		}
		
		E item = (E)elements[front];
		front = (front+1)%elements.length;
		return item;
	}

	@Override
	public boolean isEmpty() {
		return front == back;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peek() {
		if (isEmpty()) {
			throw new ArrayIndexOutOfBoundsException(0 + " >= " +
                    0);
		}
		
		return (E) elements[front];
	}
	
	public void grow() {
		int newCapacity = elements.length >>> 1 + elements.length;
		
		Object [] arr = new Object [newCapacity];
		int index = 0;
		for (int i = front ; i != back; i = (i+1)%elements.length, index++) {
			arr[index] = elements[i];
		}
		elements = arr;
		front = 0;
		back = index;
		return;
	}

}
