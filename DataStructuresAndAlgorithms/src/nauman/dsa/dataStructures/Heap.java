package nauman.dsa.dataStructures;

public interface Heap<E> {
	public abstract E peek();
	public abstract E poll();
	public void add(E item);
	public int size();
	public boolean isEmpty();
}
