package nauman.dsa.dataStructures;

public interface List <E> { 
	public abstract void add(E item);
	public abstract void add(E item, int index);
	public abstract int size();
	public abstract E get(int index);
	public abstract void remove(int index);
	public abstract void clear();
	public abstract boolean isEmpty();
	public abstract E [] toArray();
}
