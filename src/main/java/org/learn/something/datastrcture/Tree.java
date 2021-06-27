package org.learn.something.datastrcture;

import java.util.ArrayList;
import java.util.List;

/**
 * 子节点链存储实现
 *
 * @author guo
 * @date 2021/6/26
 */
public class Tree<T> {

    private static final int DEFAULT_TREE_SIZE = 16;

    public static class SonNode {
        private int pos;
        private SonNode nxt;

        public SonNode(int pos, SonNode nxt) {
            this.pos = pos;
            this.nxt = nxt;
        }

        @Override
        public String toString() {
            return "SonNode{" +
                    "pos=" + pos +
                    ", nxt=" + nxt +
                    '}';
        }
    }

    public static class Node<T> {
        private T data;
        private SonNode first;

        public Node(T data) {
            this.data = data;
            this.first = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", first=" + first +
                    '}';
        }
    }

    private int treeSize;

    private Node<T>[] nodes;

    private int nodeNums;

    public Tree(T data) {
        this(data, DEFAULT_TREE_SIZE);
    }

    public Tree(T data, int treeSize) {
        this.treeSize = treeSize;
        nodes = new Node[treeSize];
        nodes[0] = new Node<>(data);
        nodeNums++;
    }

    public void addNode(T data, Node<T> parent) {
        for (int i = 0; i < treeSize; i++) {
            if (nodes[i] == null) {
                nodes[i] = new Node<>(data);
                nodeNums++;

                SonNode son = parent.first;
                if (son == null) {
                    parent.first = new SonNode(i, null);
                    return;
                }

                while (son.nxt != null) {
                    son = son.nxt;
                }

                son.nxt = new SonNode(i, null);
                return;
            }
        }

        throw new RuntimeException("没有空间添加节点");
    }

    public List<Node<T>> listChild(Node<T> node) {
        SonNode son = node.first;
        ArrayList<Node<T>> childNodes = new ArrayList<>();
        while (son != null) {
            childNodes.add(nodes[son.pos]);
            son = son.nxt;
        }
        return childNodes;
    }

    public void printAllChild(Node<T> node) {
        SonNode son = node.first;
        while (son != null) {
            System.out.println(nodes[son.pos]);
            printAllChild(nodes[son.pos]);
            son = son.nxt;
        }
    }

    public int position(Node<T> node) {
        for (int i = 0; i < treeSize; i++) {
            if (nodes[i] == node) {
                return i;
            }
        }
        throw new RuntimeException("not found node " + node);
    }

    public Node<T> getRoot() {
        return nodes[0];
    }

    public int deep() {
        if (nodeNums == 0) {
            return 0;
        }
        return privateDeep(nodes[0]);
    }

    public Node<T> getNode(int index) {
        return nodes[index];
    }

    private int privateDeep(Node<T> node) {
        if (node.first == null) {
            return 1;
        }

        SonNode son = node.first;
        int max = 0;
        while (son != null) {
            int tmp = privateDeep(nodes[son.pos]);
            if (tmp > max) {
                max = tmp;
            }
            son = son.nxt;
        }
        return max + 1;
    }

    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("A");
        tree.addNode("B", tree.getRoot());
        tree.addNode("C", tree.getRoot());
        tree.addNode("D", tree.getRoot());

        Node<String> node1 = tree.getNode(1);
        tree.addNode("E", node1);
        tree.addNode("F", node1);

        Node<String> node5 = tree.getNode(5);
        tree.addNode("G", node5);
        tree.addNode("H", node5);

        System.out.println(tree.deep());

        System.out.println(tree.listChild(node1));

        tree.printAllChild(tree.getRoot());
    }
}
