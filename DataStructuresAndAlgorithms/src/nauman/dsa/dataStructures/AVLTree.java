package nauman.dsa.dataStructures;

import java.util.Comparator;
import java.util.Map;

/*
 * Implementation notes.
 *
 * 1). Every Node Stores the height of the Tree. Upon insertion, all heights along insertion path are updated.
 * 2). Height Balance is defined as : |v.left.height - v.right.height| <= 1  ( this property in-turn provides h <= 1.44log(n) )
 * 3). An AVLTree IS ALSO a BST. i.e. left Subtree < root < right Subtree
 * 4). Right Rotations in AVL Tree Node requires that it must have a leftChild. Used for left Heavy Trees.
 * 5). Left Rotations in AVL Tree Node requires that it must have a rightChild. Used for Right Heavy Trees.
 * 6). Worst Case you need 2 rotations after an insertion
 * 7). Deletion Requires O(log(n)) rotations in worst case
 */
public class AVLTree<K,V> implements BalancedBST<K,V>{
    Comparator<K> comparator;
    int size;

    TreeNode <K,V> root;
    public AVLTree(Comparator<K> comparator) {
        this.comparator = comparator;
        this.size = 0;
        this.root = null;
    }

    @Override
    public void insert(K key, V value) {
        if (contains(key)) {
            return;
        }
        insertNewNode(key, value);
        size++;
    }

    @Override
    public V search(K key) {
        TreeNode<K,V> node = find(key, root);
        return node == null? null: node.getValue();
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public K successor(K key) {
        return null;
    }

    @Override
    public K predecessor(K key) {
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private TreeNode<K,V> find(K key, TreeNode<K,V> current) {
        if (current == null) return null;

        int comparison = comparator.compare(key, current.key);
        if (comparison == 0) {
            return current;
        } else if (comparison < 0) {
            return find(key, current.leftChild);
        } else {
            return find(key, current.rightChild);
        }
    }

    private void insertNewNode(K key, V value) {
        TreeNode<K,V> node = new TreeNode<>(key, value);
        if (isEmpty()) {
            root = node;
        } else {
            insertion(root, node);
        }
    }

    public TreeNode<K,V> insertion (TreeNode<K,V> current, TreeNode<K,V> node) {
        if (comparator.compare(node.key, current.key) < 0) {
            if (current.leftChild == null) {
                current.leftChild = node;
            } else {
                current.leftChild = insertion(current.leftChild, node);
            }
        } else {
            if (current.rightChild == null) {
                current.rightChild = node;
            } else {
                current.rightChild = insertion(current.rightChild, node);
            }
        }

        updateHeight(current);
        if (!isHeightBalanced(current)) {
            return balanceTree(current);
        }
        return current;
    }

    public TreeNode<K,V> balanceTree(TreeNode<K,V> node) {
        if (isLeftHeavy(node)) {
            if (isRightHeavy(node.leftChild)) {
                node.leftChild = leftRotate(node.leftChild);
            }
            return rightRotate(node);
        } else {
            if (isLeftHeavy(node.rightChild)) {
                node.rightChild = rightRotate(node.rightChild);
            }
            return leftRotate(node);
        }
    }

    public TreeNode<K,V> leftRotate(TreeNode<K,V> A) {
        TreeNode<K,V> B = A.rightChild;
        A.rightChild = B.leftChild;
        B.leftChild = A;
        updateHeight(A);
        updateHeight(B);
        return B;
    }

    public TreeNode<K,V> rightRotate(TreeNode<K,V> A) {
        TreeNode<K,V> B = A.leftChild;
        A.leftChild = B.rightChild;
        B.rightChild = A;
        updateHeight(A);
        updateHeight(B);
        return B;
    }

    public boolean isLeftHeavy(TreeNode<K,V> node) {
        return heightDifferential(node) >= 1;
    }

    public boolean isRightHeavy(TreeNode<K,V> node) {
        return heightDifferential(node) <=- 1;
    }


    public void updateHeight(TreeNode<K,V> node) {
        node.height = Math.max(leftHeight(node), rightHeight(node)) + 1;
    }

    public boolean isHeightBalanced (TreeNode<K,V> node) {
       return Math.abs(heightDifferential(node)) <= 1;
    }

    public int heightDifferential(TreeNode<K,V> node) {
        return leftHeight(node) - rightHeight(node);
    }

    public int leftHeight(TreeNode<K,V> node) {
        return node.leftChild != null ? node.leftChild.height: -1;
    }

    public int rightHeight(TreeNode<K,V> node) {
        return node.rightChild != null ? node.rightChild.height : -1;
    }

    static class TreeNode <K,V> implements Map.Entry<K,V> {
        TreeNode <K,V> leftChild;
        TreeNode <K,V> rightChild;
        int height;

        K key;
        V value;
        public TreeNode (K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 0;
            this.leftChild = null;
            this.rightChild = null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }
}
