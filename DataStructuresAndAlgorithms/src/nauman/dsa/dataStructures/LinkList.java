package nauman.dsa.dataStructures;

public class LinkList<E> implements List<E> {
	
	private int size;
	private ListNode<E> head;
	
	private class ListNode<T> {
		T item;
		ListNode<T> next;
		
		public ListNode(T item) {
			this.item = item;
			this.next = null;
		}
		
		public ListNode () {}
	}
	
	public LinkList () {
		this.head = new ListNode<>();
		this.size = 0;
	}

	@Override
	public void add(E item) {
		ListNode<E> curr = head;
		for (int i = 0; i < size; i++) {
			curr = curr.next;
		}
		curr.next = new ListNode<E>(item);
		size++;
	}

	@Override
	public void add(E item, int index) {
		if (index< 0 || index > size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		ListNode <E> curr = head;
		ListNode <E> temp;
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		temp = curr.next;
		curr.next = new ListNode <> (item);
		curr.next.next = temp;
		size++;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public E get(int index) {
		if (index< 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		ListNode <E> curr = head;
		for (int i = 0; i <= index; i++) {
			curr = curr.next;
		}
		return curr.item;		
	}

	@Override
	public void remove(int index) {
		if (index< 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		
		for (ListNode<E> curr = head; curr != null; curr = curr.next,index--) {
			if (index == 0) {
				curr.next = curr.next.next;
				break;
			}
		}
		
		return;
	}

	@Override
	public void clear() {
		head.next = null;
		size = 0;
		
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray() {
		Object [] res = new Object [size];
		int counter = 0;
		for (ListNode<E> curr = head; curr!= null; curr = curr.next, counter++) {
			res[counter] = curr.item;
		}
		return (E[]) res;
	}

}
