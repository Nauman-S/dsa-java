package nauman.dsa.dataStructures;

import java.util.Comparator;

/*
 * Implementation notes.
 * 
 * 1). parentNode >= childNode (MaxHeap)
 * 2). All levels full except last
 * 3). All nodes as far left as possible
 * 
 * Internally we represent a MaxHeap as an array.
 */

public class MaxHeap<E> implements Heap<E> {
	
	private Comparator<E> comp;
	
	private int INITIAL_CAPACITY = 8;
	
	private Object[] heap;
	
	private int size;
	
	public MaxHeap (Comparator<E> comp) {
		this.comp = comp;
		this.heap = new Object [INITIAL_CAPACITY];
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peek() {
		if (size > 0) return (E) heap[0];
		return null;
	}

	@Override
	public E poll() {
		if (size == 0) return null;
		E item = (E)heap[0];
		swap(0 , --size);
		heap[size] = null;
		siftDown(0);
		return item;
	}

	@Override
	public void add(E item) {
		if (++size == heap.length) {
			grow(heap.length << 1);
		}
		heap[size-1] = item;
		siftUp(size-1);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	private void grow(int newCapacity) {
		Object [] oldHeap = heap;
		heap = new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			heap[i] = oldHeap[i];
		}
	}
	
	@SuppressWarnings("unchecked")
	private void siftUp(int index) {
		int parentIndex;
		while (index != 0) {
			parentIndex = parent(index);
			if (comp.compare((E) heap[parentIndex], (E)heap[index]) < 0) {
				swap(parentIndex, index);
			}else {
				return;
			}
			index = parentIndex;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void siftDown(int index) {
		int child = leftChild(index);;
		while (child < size) {
			if (child + 1 < size && comp.compare((E)heap[child],(E) heap[child+1]) < 0) {
				child++;
			}
			
			if (comp.compare((E)heap[index], (E)heap[child]) < 0) {
				swap(index, child);
			}else {
				break;
			}
			index = child;
			child = leftChild(index);
		}
	}
	
	private int parent (int index) {
		if (index == 0) return -1;
		return (index - 1) >>> 1;
	}
	
	private int leftChild (int index) {
		return (index << 1) + 1;
	}
	
	private void swap (int index1, int index2) {
		Object temp;
		temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}

}
