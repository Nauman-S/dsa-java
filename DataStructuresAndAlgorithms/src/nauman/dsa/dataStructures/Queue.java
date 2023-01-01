package nauman.dsa.dataStructures;

public interface Queue<E> {
	
	public abstract void enqueue(E item);
	
	public abstract E dequeue();
	
	public boolean isEmpty();
	
	public E peek();
}
