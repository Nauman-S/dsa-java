package nauman.dsa.dataStructures;

import java.util.Comparator;
import java.util.Map;

/*
 * Quick Recap of AVLTree Properties
 *
 * 1). Every Node Stores the height of the Tree. Upon insertion, all heights along insertion path are updated.
 * 2). Height Balance is defined as : |v.left.height - v.right.height| <= 1  ( this property in-turn provides h <= 1.44log(n) )
 * 3). An AVL Tree is ALSO a BST. i.e. left All nodes in Subtree < root < All nodes in right Subtree
 * 4). Right Rotations in AVL Tree for left Heavy imbalanced Nodes. This is Simple Case 1.
 * 5). Left Rotations in AVL Tree for Right Heavy imbalanced Nodes. This is Simple Case 2.
 * 6). 1 special subcases for each of the above 2 Simple cases that requires up to 2 rotations for an imbalance node.
 *     Worst Case 1   :  Left Heavy (node) + Right Heavy (node.leftChild)
 *     Solution       :  Left Rotate (node.LeftChild) => Right Rotate (node)
 *     Worst Case 2   :  Right Heavy (node) + Left Heavy (node.rightChild)
 *     Solution       :  Right Rotate (node.rightChild) => Left Rotate(node)
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
        return search(key) != null;
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
    public K searchMin() {
        if (root == null) return null;
        return root.searchMin().getKey();
    }

    @Override
    public K searchMax() {
        if (root == null) return null;
        return root.searchMax().getKey();
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
        TreeNode<K,V> newNode = new TreeNode<>(key, value);
        if (isEmpty()) {
            root = newNode;
        } else {
            root = insertion(root, newNode);
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

        public TreeNode<K,V> searchMin() {
            if (this.leftChild == null) return this;
            return this.leftChild.searchMin();
        }

        public TreeNode<K,V> searchMax() {
            if (this.rightChild == null) return this;
            return this.rightChild.searchMax();
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
