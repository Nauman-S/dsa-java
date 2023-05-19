package nauman.dsa.dataStructures;

import java.util.Objects;

public class HashMap <K,V> {
	
	/*
     * Implementation notes.
     * 
     * 
     */
	
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	private int currentCapacity;
	
    /**
     * The number of key-value mappings inside this map
     */
	private int size;
	
	private Node<K,V> [] arr;
	
	public HashMap() {
		this.currentCapacity = DEFAULT_INITIAL_CAPACITY;
		this.arr = new Node [currentCapacity];
		this.size = 0;
	}
	
    /**
     * Rehashes all the nodes based on the new capacity
     *
     * @param key the key to be found
     * @return the value, or null if not found
     */
	public void rehash(int newCapacity) {
		currentCapacity = newCapacity;
		
		Node<K,V> [] oldNodes = arr;
		arr = new Node [currentCapacity];
		
		for (Node<K,V> node: oldNodes) {
			Node<K,V> current = node;
			while (current != null) {
				int index = getIndex(current.hash); 
				Node<K,V> n1 = arr[index];
				Node<K,V> np = null;
				while (n1 != null) {
					np = n1;
					n1 = n1.next;
				}
				if (np == null) {
					arr[index] = new Node<K,V> (current.hash, current.key, current.value,null);
				} else {
					np.next = new Node<K,V> (current.hash, current.key, current.value,null);
				}
	
			}
		}
		
		
	}
	
	
    /**
     * If key exists in bucket update its value else insert into bucket
     *
     * @param key the key to be found
     * @return the value, or null if not found
     */
	public void put(K key, V value) {
		int hash = key.hashCode();
		int index = getIndex(hash);
		Node<K,V> current = arr[index];
		Node<K,V> prev = null;
		
		while (current != null) {
			if (current.key == key) {
				current.setValue(value);
				return;
			}
			prev = current;
			current = current.next;
		}
		
		if (prev == null) {
			arr[index] = new Node<K,V> (hash, key, value,null);
		} else {
			prev.next = new Node<K,V> (hash, key, value, null);
		}
		
		size++;
		
		if ((float)size/currentCapacity > DEFAULT_LOAD_FACTOR) {
			rehash(currentCapacity*2);
		}
	}
	
	
    /**
     * Hashes key, finds it in bucket and returns its value if found
     *
     * @param key the key to be found
     * @return the value, or null if not found
     */
	public V get(K key) {
		Node<K,V> current = arr[getIndex(key.hashCode())];
		while (current != null) {
			if (current.getKey().equals(key)) {
				return current.getValue();
			}
			current = current.next;
		}
		return null;
	}
	
	
    /**
     * Remove key from bucket if present
     *
     * @param key the key to be found
     * @return the value, or null if not found
     */
	public V remove(K key) {
		int index = getIndex(key.hashCode());
		Node <K,V> current = arr[index];
		Node <K,V> prev = null;
		
		while (current != null) {
			if (current.getKey().equals(key)) {
				break;
			}
			prev = current;
			current = current.next;
		}
		
		if (current != null) {
			size --;
			if (prev != null) {
				prev.next = current.next;
			} else {
				arr[index] = current.next;
			}
			return current.getValue();
		}
		return null;
	}
	
	public int getIndex(int hashCode) {
		int hash = hashCode ^ (hashCode >>> 16);
		int index = (currentCapacity - 1) & hash;

		return index;
	}
	
	//return Objects.hashCode(key) % currentCapacity;

	
	static class Node <K,V> {
		final int hash;
		final K key;
		V value;
		Node<K,V> next;
		Node(int hash, K key, V value, Node<K,V> next) {
			this.hash = key.hashCode();
			this.key = key;
			this.value = value;
			this.next = null;
		}
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
		
		public void setNext( Node <K,V> next) {
			this.next = next;
		}
	}
	
	public int size() {
		return size;
	}
	
}
