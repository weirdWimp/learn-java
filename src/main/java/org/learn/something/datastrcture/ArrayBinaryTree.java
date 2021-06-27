package org.learn.something.datastrcture;

import java.util.Objects;

/**
 * 二叉树的顺序存储实现，对于非满二叉树，有较大的空间浪费
 *
 * @author guo
 * @date 2021/6/27
 */
public class ArrayBinaryTree<T> {

    private static final int DEFAULT_DEEP = 8;

    private Object[] dataArray;

    private int deep;

    private int size;

    public ArrayBinaryTree() {
        deep = DEFAULT_DEEP;
        size = (1 << deep) - 1;
        dataArray = new Object[size];
    }

    public ArrayBinaryTree(int deep) {
        this.deep = deep;
        size = (1 << deep) - 1;
        dataArray = new Object[size];
    }

    public ArrayBinaryTree(int deep, T data) {
        this.deep = deep;
        size = (1 << deep) - 1;
        dataArray = new Object[size];
        dataArray[0] = data;
    }

    /**
     * @param index 父节点索引
     * @param data  要添加节点的数据
     * @param left  是否添加为父节点的左子节点
     */
    public void add(int index, T data, boolean left) {
        if (index < 0 || index > (size - 1)) {
            throw new IndexOutOfBoundsException("Illegal parent index " + index);
        }

        Object parent = dataArray[index];
        Objects.requireNonNull(parent, index + " has no node");

        if (2 * index + 1 >= size) {
            throw new RuntimeException("full array");
        }

        if (left) {
            dataArray[2 * index + 1] = data;
        } else {
            dataArray[2 * index + 2] = data;
        }
    }

    /**
     * 判断二叉树是否为空树
     *
     * @return
     */
    public boolean empty() {
        return dataArray[0] == null;
    }

    /**
     * 返回根节点
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public T root() {
        return (T) dataArray[0];
    }

    /**
     * 返回非根节点的父节点
     *
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public T parent(int index) {
        return (T) dataArray[(index - 1) / 2];
    }

    /**
     * 返回某个节点的左子节点
     *
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public T left(int index) {
        if (2 * index + 1 >= size) {
            throw new RuntimeException("illegal index " + index);
        }
        return ((T) dataArray[2 * index + 1]);
    }

    /**
     * 返回某个节点的右子节点
     *
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public T right(int index) {
        if (2 * index + 2 >= size) {
            throw new RuntimeException("illegal index " + index);
        }
        return ((T) dataArray[2 * index + 2]);
    }

    public int deep() {
        return deep;
    }

    public int pos(T data) {
        // 广度遍历
        for (int i = 0; i < size; i++) {
            if (dataArray[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }
}
