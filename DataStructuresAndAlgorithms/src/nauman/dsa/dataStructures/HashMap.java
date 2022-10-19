package nauman.dsa.dataStructures;

import java.math.BigInteger;

public class HashMap {
	
	/*
     * Implementation notes.
     *
     * This map uses Multiplicative Hashing to generate the hash of a number
     * 
     * m = number of buckets
     * p = A large fixed prime number such that p >= m
     * For the purpose of this implementation we take p as being 2147483647 = 2^31 - 1 (which happens to be prime) 
     * 
     * Random Integer a in range [1,p-1]
     * Random Integer b in range [0,p-1]
     * 
     * h(c) = hash of Integer c
     * h(c) = ((a*c + b) mod p) mod m
     * 
     * Can be proven that for fixed m,p and randomly chosen a,b the probabilitiy of hash collision is 1/m 
     * Hence, its a Universal Hash Function
     * 
     * 
     */
	
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	private static final int PRIME = 2147483647;
	
	private int currentCapacity;
	
    /**
     * The number of key-value mappings inside this map
     */
	private int size;
	
	private Node [] arr;
	
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
		
		Node [] oldNodes = arr;
		arr = new Node [currentCapacity];
		
		for (Node node: oldNodes) {
			Node current = node;
			while (current != null) {
				put(current.key, current.value);
			}
		}
		
	}
	
	
    /**
     * If key exists in bucket update its value else insert into bucket
     *
     * @param key the key to be found
     * @return the value, or null if not found
     */
	public void put(int key, int value) {
		int index = getHash(key);
		Node current = arr[index];
		Node prev = null;
		
		while (current != null) {
			if (current.key == key) {
				current.value = value;
				return;
			}
			prev = current;
			current = current.next;
		}
		
		if (prev == null) {
			arr[index] = new Node(key, value);
		} else {
			prev.next = new Node(key, value);
		}
		
		size++;
		
		if (size > currentCapacity) {
			rehash(currentCapacity*2);
		}
	}
	
    /**
     * Hashes key, finds it in bucket and returns its value if found
     *
     * @param key the key to be found
     * @return the value, or null if not found
     */
	public Integer get(int key) {
		Node current = arr[getHash(key)];
		while (current != null) {
			if (current.key == key) {
				return current.value;
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
	public void remove(int key) {
		int index = getHash(key);
		Node current = arr[index];
		Node prev = null;
		
		while (current != null) {
			if (current.key == key) {
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
		}
	}
	

	//Assume is universal hash function; Is not really
	public int getHash(int key) {
		BigInteger c = BigInteger.valueOf(key);
		BigInteger p = BigInteger.valueOf(PRIME);
		BigInteger m = BigInteger.valueOf(currentCapacity);
		BigInteger index = c.multiply(p).mod(m);
		return index.intValue();
	}
	
//	public int getHash(int key) {
//		return Integer.hashCode(key) % currentCapacity;
//	}
	
	private class Node {
		Node next;
		int key;
		int value;
		Node(int key, int value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}
	}
	
}
