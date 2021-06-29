package org.learn.something.datastrcture;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * 哈夫曼树，即带权路径最短的二叉树
 * <p>
 * 节点带权路径：该节点到跟节点的路径长度 * 该节点的权值
 *
 * @author guo
 * @date 2021/6/27
 */
public class HuffmanTree<T> {

    @Data
    private static class Node<T> {
        T data;
        double weight; // 节点权重值
        Node<T> left;
        Node<T> right;

        public Node(T data, double weight) {
            this.data = data;
            this.weight = weight;
        }
    }


    public static <T> Node<T> createTree(List<Node<T>> nodes) {
        while (nodes.size() > 1) {
            nodes.sort((n1, n2) -> Double.compare(n2.getWeight(), n1.getWeight()));
            Node<T> left = nodes.get(nodes.size() - 1);
            Node<T> right = nodes.get(nodes.size() - 2);

            Node<T> parent = new Node<>(null, left.weight + right.weight);
            parent.left = left;
            parent.right = right;

            nodes.remove(nodes.size() - 1);
            nodes.remove(nodes.size() - 2);

            nodes.add(parent);
        }

        return nodes.get(0);
    }
}
