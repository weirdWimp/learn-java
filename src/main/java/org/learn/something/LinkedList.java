package org.learn.something;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

    public static final int INIT_SIZE = 10;

    private Node<T> head;

    private static class Node<T> {
        private T value;

        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public void add(T t) {
        Node<T> node = new Node<>(t);
        if (head == null) {
            head = node;
        } else {
            head.setNext(node);
        }
    }

    @Override
    public Iterator<T> iterator() {
        /**
         * 局部类和外层实例关联
         * 1 能访问外层类的任何成员（包括私有成员）
         * 2 能访问同一个作用域中声明为final的的任何局部变量、方法参数、异常参数
         * （这是因为局部类的生命周期可能比定义它的方法的执行时间长很多）
         */
        class InnerIterator implements Iterator<T> {
            Node<T> current;

            //可以访问外层类私有字段
            public InnerIterator() {
                current = head;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                T value = current.getValue();
                current = current.getNext();
                return value;
            }
        }

        return new InnerIterator();
    }
}
