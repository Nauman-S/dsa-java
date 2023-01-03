package nauman.dsa.dataStructures;

public class ArrayDeque<E> implements Deque<E> {
	
	private int DEFAULT_INITIAL_CAPACITY = 10;
	
	private int front;
	
	private int back;
	
	private int size;
	
	private Object [] elements;
	
	public ArrayDeque () {
		this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
		size = 0;
	}
	
	
	@Override
	public void addFirst(E item) {
		front = front - 1 < 0 ? elements.length - 1 : front -1;
		elements[front] = item;
		size++;
		if (front == back) {
			grow();
		}
	}

	@Override
	public void addLast(E item) {
		elements[back] = item;
		back = (back+1) % elements.length;
		size++;
		if (front == back) {
			grow();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peekFirst() {
		if (isEmpty()) {
			throw new ArrayIndexOutOfBoundsException(0 + " >= " +
                    0);
		}
		return (E) elements[front];
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peekLast() {
		if (isEmpty()) {
			throw new ArrayIndexOutOfBoundsException(0 + " >= " +
                    0);
		}
		return (E)elements[back - 1 < 0 ? elements.length - 1 : back -1];
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E pollFirst() {
		if (isEmpty()) {
			throw new ArrayIndexOutOfBoundsException(0 + " >= " +
                    0);
		}
		E res = (E) elements[front];
		front = (front+1) % elements.length;
		size--;
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E pollLast() {
		if (isEmpty()) {
			throw new ArrayIndexOutOfBoundsException(0 + " >= " +
                    0);
		}
		back = back - 1 < 0 ? elements.length - 1 : back - 1;
		size--;
		return (E) elements[back];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return front == back;
	}
	
	public void grow() {
		int newCapacity = elements.length >>> 1 + elements.length;
		Object [] arr = new Object [newCapacity];
		int index = 0;
		for (int i = front; i != back; i = (i+1)%elements.length, index++) {
			arr[index] = elements[i];
		}
		front = 0;
		back = index;
		elements = arr;
	}

}
