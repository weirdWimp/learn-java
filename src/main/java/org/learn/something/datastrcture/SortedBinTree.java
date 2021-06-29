package org.learn.something.datastrcture;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序二叉树
 *
 * @author guo
 * @date 2021/6/29
 */
public class SortedBinTree<T extends Comparable<T>> {

    private static class Node<T> {
        private T data;

        private Node<T> parent;

        private Node<T> left;

        private Node<T> right;

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node<T> parent, Node<T> left, Node<T> right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    private Node<T> root;

    public SortedBinTree() {
    }

    public SortedBinTree(T data) {
        this.root = new Node<>(data);
    }

    public void add(T data) {
        if (root == null) {
            root = new Node<>(data);
            return;
        }

        Node<T> current = root;
        boolean isLeft = false;
        Node<T> parent = null;

        Node<T> newNode = new Node<>(data);

        while (current != null) {
            parent = current;
            if (data.compareTo(current.data) < 0) {
                current = current.left;
                isLeft = true;
            } else {
                current = current.right;
                isLeft = false;
            }
        }

        newNode.parent = parent;
        if (isLeft) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public Node<T> getNode(T t) {
        if (root == null) {
            throw new IllegalArgumentException("empty tree");
        }

        Node<T> current = root;
        while (current != null) {
            int cmp = t.compareTo(current.data);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    public void remove(T t) {
        Node<T> node = getNode(t);
        if (node == null) {
            throw new RuntimeException("Element " + t + " not exists");
        }

        Node<T> left = node.left;
        Node<T> right = node.right;

        boolean leftExist = left != null;
        boolean rightExist = right != null;
        boolean isRoot = node == root;


        /*
         * 被删除的节点是一个叶子节点
         * */
        if (!leftExist && !rightExist) {
            if (isRoot) {
                root = null;
            } else {
                boolean isFatherLeft = node == node.parent.left;
                if (isFatherLeft) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                node.parent = null;
            }
            return;
        }


        /*
         * 要删除的节点只有左子树，将左子树作为其父节点的左子树或右子树（取决于要删除的节点是其父节点的左子树还是右子树）
         * */
        if (leftExist && !rightExist) {
            if (isRoot) {
                left.parent = null;
                node.left = null;
                root = left;
            } else {
                boolean isFatherLeft = node == node.parent.left;
                left.parent = node.parent;
                if (isFatherLeft) {
                    node.parent.left = left;
                } else {
                    node.parent.right = left;
                }
                node.parent = null;
                node.left = null;
            }
            return;
        }

        /*
         * 要删除的节点只有右子树，将右子树作为其父节点的左子树或右子树（取决于要删除的节点是其父节点的左子树还是右子树）
         * */
        if (!leftExist && rightExist) {
            if (isRoot) {
                right.parent = null;
                node.right = null;
                root = right;
            } else {
                boolean isFatherLeft = node == node.parent.left;
                right.parent = node.parent;
                if (isFatherLeft) {
                    node.parent.left = right;
                } else {
                    node.parent.right = right;
                }
                node.parent = null;
                node.right = null;
            }
            return;
        }

        /*
         * 左子树和右子树都存在
         * 1. 将要删除的节点 p 的左子树 pL 作为 p 的父节点 q 的左或右子节点（取决于 p 是 q 的左子节点还是右子节点）
         * 2. 将 p 的右子树 pR 作为 p 的中序前驱节点 s 的右子节点 （ p 的中序前驱节点，也就是 p 的左子树中节点值小于 p 的节点值最大的一个值，左子树中最靠右下的节点）
         * */
        Node<T> pre = pre(node);
        if (isRoot) {
            left.parent = null;
            root.left = null;
            pre.right = root.right;
            root = left;
        } else {
            boolean isFatherLeft = node == node.parent.left;
            if (isFatherLeft) {
                node.parent.left = left;
            } else {
                node.parent.right = left;
            }
            left.parent = node.parent;
            pre.right = right;
            right.parent = pre;

            node.parent = null;
            node.left = null;
            node.right = null;
        }
    }

    /**
     * 查找某个节点的中序前驱节点
     *
     * @return
     */
    private Node<T> pre(Node<T> node) {
        if (node.left == null) {
            return null;
        }

        Node<T> p = node.left;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    @Override
    public String toString() {
        return "SortedBinTree{" +
                "root=" + root +
                '}';
    }

    /**
     * 深度优先遍历：中序遍历 LDR, 排序二叉树的中序遍历结果是有序的
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

    public static void main(String[] args) {
        SortedBinTree<Integer> sortedBinTree = new SortedBinTree<>(15);
        sortedBinTree.add(9);
        sortedBinTree.add(25);
        sortedBinTree.add(6);
        sortedBinTree.add(12);
        sortedBinTree.add(10);
        sortedBinTree.add(18);
        sortedBinTree.add(35);
        sortedBinTree.add(5);
        sortedBinTree.add(7);
        sortedBinTree.add(8);


        sortedBinTree.remove(18);
        sortedBinTree.remove(25);

        sortedBinTree.inIterator(sortedBinTree.root).forEach(node -> System.out.println(node.data));
        System.out.println("");
    }
}
