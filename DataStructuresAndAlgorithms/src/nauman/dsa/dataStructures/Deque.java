package nauman.dsa.dataStructures;

public interface Deque<E> {
	
	public abstract void addFirst(E item);
	
	public abstract void addLast(E item);
	
	public abstract E peekFirst();
	
	public abstract E peekLast();
	
	public abstract E pollFirst();
	
	public abstract E pollLast();
	
	public abstract int size();
	
	public abstract boolean isEmpty();
}
