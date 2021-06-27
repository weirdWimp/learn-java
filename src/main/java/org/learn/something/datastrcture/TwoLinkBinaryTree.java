package org.learn.something.datastrcture;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * 二叉树的二叉链表实现
 *
 * @author guo
 * @date 2021/6/27
 */
public class TwoLinkBinaryTree<T> {

    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private Node<T> root;

    public TwoLinkBinaryTree() {
        this.root = new Node<>();
    }

    public TwoLinkBinaryTree(T data) {
        this.root = new Node<>(data);
    }

    public Node<T> addNode(Node<T> parent, T data, boolean isLeft) {
        Objects.requireNonNull(parent);

        if (isLeft && parent.left != null) {
            throw new RuntimeException("none null left");
        }

        if (!isLeft && parent.right != null) {
            throw new RuntimeException("none null right");
        }

        Node<T> newNode = new Node<>(data);
        if (isLeft) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        return newNode;
    }

    public boolean empty() {
        return root.data == null;
    }

    public Node<T> root() {
        if (empty()) {
            throw new RuntimeException("empty binary tree");
        }
        return root;
    }

    /**
     * 查找某个非根节点的父节点，二叉连边存储需要遍历二叉树才能找到
     *
     * @param node
     * @return
     */
    private T innerParent(Node<T> node, Node<T> treeRootNode) {
        if (treeRootNode == null) {
            return null;
        }

        Node<T> left = treeRootNode.left;
        Node<T> right = treeRootNode.right;

        if (left == null && right == null) {
            return null;
        }

        if (left == node || right == node) {
            return treeRootNode.data;
        }

        T parentData = innerParent(node, left);
        if (parentData == null) {
            parentData = innerParent(node, right);
        }
        return parentData;
    }

    /**
     * 查找某个非根节点的父节点，二叉连边存储需要遍历二叉树才能找到
     *
     * @param node
     * @return
     */
    public T parent(Node<T> node) {
        return innerParent(node, root);
    }

    private int deep(Node<T> node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return 1;
        } else {
            int leftDeep = deep(node.left);
            int rightDeep = deep(node.right);
            int deep = Math.max(leftDeep, rightDeep);
            return deep + 1;
        }
    }

    public List<Node<T>> preIterator() {
        return preIterator(root);
    }

    /**
     * 深度优先遍历：先遍历 DLR
     *
     * @param node
     * @return
     */
    private List<Node<T>> preIterator(Node<T> node) {
        List<Node<T>> nodes = new ArrayList<>();
        if (node == null) {
            return nodes;
        }

        nodes.add(node);
        Node<T> left = node.left;
        if (left != null) {
            nodes.addAll(preIterator(left));
        }

        Node<T> right = node.right;
        if (right != null) {
            nodes.addAll(preIterator(right));
        }

        return nodes;
    }

    public List<Node<T>> inIterator() {
        return inIterator(root);
    }

    /**
     * 深度优先遍历：中序遍历 LDR
     *
     * @param node
     * @return
     */
    private List<Node<T>> inIterator(Node<T> node) {
        List<Node<T>> nodes = new ArrayList<>();
        if (node == null) {
            return nodes;
        }

        Node<T> left = node.left;
        if (left != null) {
            nodes.addAll(inIterator(left));
        }

        nodes.add(node);

        Node<T> right = node.right;
        if (right != null) {
            nodes.addAll(inIterator(right));
        }

        return nodes;
    }


    public List<Node<T>> postIterator() {
        return postIterator(root);
    }

    /**
     * 深度优先遍历：后序遍历 LRD
     *
     * @param node
     * @return
     */
    private List<Node<T>> postIterator(Node<T> node) {
        List<Node<T>> nodes = new ArrayList<>();
        if (node == null) {
            return nodes;
        }

        Node<T> left = node.left;
        if (left != null) {
            nodes.addAll(inIterator(left));
        }

        Node<T> right = node.right;
        if (right != null) {
            nodes.addAll(inIterator(right));
        }

        nodes.add(node);
        return nodes;
    }

    /**
     * 广度优先遍历，基于一个 FIFO 队列
     *
     * @return
     */
    public List<Node<T>> breadIterator() {
        Queue<Node<T>> queue = new ArrayDeque<>();
        List<Node<T>> list = new ArrayList<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            list.add(node);

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return list;
    }


    public int deep() {
        return deep(root);
    }

    public static void main(String[] args) {
        TwoLinkBinaryTree<String> binTree = new TwoLinkBinaryTree<>("A");
        Node<String> root = binTree.root();
        binTree.addNode(root, "B", true);
        binTree.addNode(root, "C", false);

        Node<String> b = root.left;
        Node<String> c = root.right;

        binTree.addNode(b, "D", true);
        Node<String> d = b.left;

        binTree.addNode(d, "H", true);

        Node<String> h = d.left;

        System.out.println(binTree.parent(h));
        System.out.println(binTree.deep());

        for (Node<String> stringNode : binTree.breadIterator()) {
            System.out.println(stringNode.data);
        }
    }
}
