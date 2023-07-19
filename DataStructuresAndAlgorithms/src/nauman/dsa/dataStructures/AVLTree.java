package nauman.dsa.dataStructures;

import java.util.Comparator;
import java.util.Map;
import java.util.Stack;

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
 * 8). The key for successor need not exist to find successor
 * 9). Deletion may take up to log(n) rotations - for every ancestor of deleted node, you must check if it is heigh balanced via rotations
 *
 * AVL vs RedBlack Tree
 * AVL is faster on search
 * Red black is faster on insert and delete (Esp delete)
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
        if (root == null) {
            return null;
        }
        Stack<TreeNode<K,V>> history = generateHistoryToClosestNodeWithKey(key, root);
        TreeNode<K,V> successor = findSuccessorFromClosestNode(key, history);

        return successor == null ? null: successor.key;
    }

    @Override
    public K predecessor(K key) {
        if (root == null) {
            return null;
        }
        Stack<TreeNode<K,V>> history = generateHistoryToClosestNodeWithKey(key, root);
        TreeNode<K,V> predecessor = findPredecessorFromClosestNode(key, history);
        return predecessor == null ? null: predecessor.key;
    }

    @Override
    public boolean delete(K key) {
        Stack<TreeNode<K,V>> pathToNode =  generateHistoryToClosestNodeWithKey(key, root);
        if (pathToNode.isEmpty() || comparator.compare(pathToNode.peek().key, key)!=0 ) return false;
        performDeletion(pathToNode);
        balanceAllNodes(pathToNode);
        size--;
        return true;
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

    private Stack<TreeNode<K,V>> generateHistoryToClosestNodeWithKey(K key, TreeNode<K,V> node) {
        Stack<TreeNode<K,V>> history = new Stack<>();
        int comparison;
        while (node != null) {
            history.push(node);
            comparison = comparator.compare(key, node.key);
            if (comparison < 0) {
                node = node.leftChild;
            } else if(comparison > 0) {
                node = node.rightChild;
            } else {
                break;
            }
        }
        return history;
    }

    public TreeNode <K,V> findSuccessorFromClosestNode(K key, Stack<TreeNode<K,V>> history) {
        if (comparator.compare(key, history.peek().key) < 0)  {
            return history.peek();
        } else if(history.peek().rightChild != null) {
            return history.peek().rightChild.searchMin();
        } else {
            TreeNode<K,V> node = history.pop();

            while (!history.isEmpty()) {
                if (comparator.compare(node.getKey(), history.peek().key) < 0 ){
                    return history.pop();
                }
                node = history.pop();
            }
            return null;
        }
    }

    public TreeNode <K,V> findPredecessorFromClosestNode(K key, Stack<TreeNode<K,V>> history) {
        if (comparator.compare(key, history.peek().key) > 0)  {
            return history.peek();
        } else if(history.peek().leftChild != null) {
            return history.peek().leftChild.searchMax();
        } else {
            TreeNode<K,V> node = history.pop();

            while (!history.isEmpty()) {
                if (comparator.compare(node.getKey(), history.peek().key) > 0 ){
                    return history.pop();
                }
                node = history.pop();
            }
            return null;
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

    public void performDeletion (Stack<TreeNode<K,V>> pathToDeletedNode) {
        TreeNode<K,V> nodeToDelete = pathToDeletedNode.peek();
        if (nodeToDelete.leftChild == null && nodeToDelete.rightChild == null) {
            deleteNodeWithNoChildren(pathToDeletedNode);
        } else if(nodeToDelete.leftChild == null || nodeToDelete.rightChild == null) {
            deleteNodeWithSingleChild(pathToDeletedNode);
        } else {
            deleteNodeWithBothChildren(pathToDeletedNode);
        }
    }

    public void deleteNodeWithNoChildren(Stack<TreeNode<K,V>> pathToDeletedNode) {
        TreeNode<K,V> nodeToDelete = pathToDeletedNode.pop();
        if (pathToDeletedNode.isEmpty()) {
            root = null;
        }else if (pathToDeletedNode.peek().leftChild == nodeToDelete){
            pathToDeletedNode.peek().leftChild = null;
        } else {
            pathToDeletedNode.peek().rightChild = null;
        }
    }

    public void deleteNodeWithSingleChild(Stack<TreeNode<K,V>> pathToDeletedNode) {
        TreeNode<K,V> nodeToDelete = pathToDeletedNode.pop();
        TreeNode<K,V> childNode = nodeToDelete.leftChild!=null ? nodeToDelete.leftChild : nodeToDelete.rightChild;
        if (pathToDeletedNode.isEmpty()) {
            root = childNode;
        } else {
            TreeNode<K,V> parentNode = pathToDeletedNode.pop();
            if (parentNode.leftChild == nodeToDelete) {
                parentNode.leftChild = childNode;
            } else {
                parentNode.rightChild = childNode;
            }
        }
    }

    public void deleteNodeWithBothChildren (Stack<TreeNode<K,V>> pathToDeletedNode) {
        TreeNode<K,V> nodeToDelete = pathToDeletedNode.peek();
        updateHistoryToSuccessor(pathToDeletedNode);
        TreeNode<K,V> successor = pathToDeletedNode.peek();
        nodeToDelete.key = successor.key;
        nodeToDelete.value = successor.value;
        if (successor.leftChild != null || successor.rightChild != null) {
            deleteNodeWithSingleChild(pathToDeletedNode);
        } else {
            deleteNodeWithNoChildren(pathToDeletedNode);
        }
    }

    public void updateHistoryToSuccessor (Stack<TreeNode<K,V>> pathToDeletedNode) {
        TreeNode<K,V> nodesOnPathToSuccessor = pathToDeletedNode.peek().rightChild;
        while (nodesOnPathToSuccessor != null) {
            pathToDeletedNode.push(nodesOnPathToSuccessor);
            nodesOnPathToSuccessor = pathToDeletedNode.peek().leftChild;
        }
    }

    public void balanceAllNodes (Stack<TreeNode<K,V>> pathToDeletedNode) {
        TreeNode<K,V> node;
        TreeNode<K,V> parent = pathToDeletedNode.pop();
        while (parent != null) {
            node = parent;
            parent = pathToDeletedNode.isEmpty() ? null : pathToDeletedNode.pop();
            updateHeight(node);
            if (isHeightBalanced(node)) {
                continue;
            }
            if (parent == null) {
                root = balanceTree(node);
            } else {
                if (parent.leftChild == node) {
                    parent.leftChild = balanceTree(node);
                } else {
                    parent.rightChild = balanceTree (node);
                }
            }

        }
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
