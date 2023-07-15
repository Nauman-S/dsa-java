package nauman.dsa.dataStructures;

public interface BalancedBST <K,V>{
    public void insert(K key, V value);
    public V search(K key);

    public boolean contains(K key);
    public K successor(K key);
    public K predecessor(K key);
    public boolean delete (K key);

    public int size();

    public boolean isEmpty();
}
