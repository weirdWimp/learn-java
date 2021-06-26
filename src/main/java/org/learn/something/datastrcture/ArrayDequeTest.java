package org.learn.something.datastrcture;

import java.util.ArrayDeque;

/**
 * @author guo
 * @date 2021/6/26
 */
public class ArrayDequeTest {

    public static void main(String[] args) {
        // asStack();

        asQueue();
    }

    /**
     * 作为栈使用，栈这种数据结构，只能在栈顶操作，插入（入栈）和删除（出栈）只在双端队列的一端进行操作
     */
    private static void asStack() {
        // as stack use, LIFO

        // init with a 16 elements array
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        // when the array is full, grow to double size and make the array copy
        // and this will happen when this push operation fill array
        for (int i = 0; i < 17; i++) {
            System.out.println("push:" + (i + 1));
            stack.push(i + 1); // addFirst 由于底层会自动扩容，所以其实 addFirst/add/addLast 方法不会抛出异常
        }

        // pop use removeFirst, when the stack is empty, exception will be trowed
        for (int i = 0; i < 18; i++) {
            System.out.println("pop:" + stack.pop()); // removeFirst
        }
    }

    /**
     * 作为一个普通的队列使用，普通的队列的操作就是，出队，入队，查看队首元素，此时，双端队列的一端作为队尾，一端作为队首
     * 队首进行出队（poll/remove），队尾入队（offer/add）, 查看队首元素（peek/element）
     */
    private static void asQueue() {
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < 17; i++) {
            System.out.println("offer " + i);
            queue.offer(i); // offerLast 队尾入队
        }

        for (int i = 0; i < 18; i++) {
            System.out.println("poll:" + queue.poll()); // pollFirst 队首出队，队列为空时， poll = null
        }
    }
}
