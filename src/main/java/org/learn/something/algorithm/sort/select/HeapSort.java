package org.learn.something.algorithm.sort.select;

import java.util.Arrays;

/**
 * @author guo
 * @date 2021/7/3
 */
public class HeapSort {

    public static <T extends Comparable<? super T>> void heapSort(T[] data) {
        // 需要建 n-1 次堆
        for (int i = 0; i < data.length - 1; i++) {
            buildMaxHead(data, data.length - i - 1);
            System.out.println(Arrays.toString(data));
            swap(data, 0, data.length - 1 - i);
        }
    }

    /**
     * 建立大顶堆
     * 1. 转换为一颗完全二叉树，此处的数组可以看作是完全二叉树的顺序存储，所以此步骤可忽略
     * 2. 从完全二叉树的最后一个非叶子节点开始，最后一个叶子节点的索引为 (len-1), 父节点索引为 (len-2)/2, 如果子节点的值大于父节点的值，
     * 则将加大的子节点与父节点进行交换，如果交换后，该子节点还有字节点，继续向下调整
     *
     * @param data
     * @param lastIndex
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void buildMaxHead(T[] data, int lastIndex) {
        // (lastIndex - 1)/2 为最后一个非叶子节点索引，比这个索引小的，也都是非叶子节点
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {

            // k 保存当前正在判断的节点
            int k = i;

            // 左子节点存在
            while (k * 2 + 1 <= lastIndex) {
                // 总是记录子节点中值较大的索引
                int biggerIndex = k * 2 + 1;

                // 右子节点存在
                if (biggerIndex < lastIndex) {
                    // 右子节点的值较大
                    if (data[biggerIndex].compareTo(data[biggerIndex + 1]) < 0) {
                        biggerIndex++;
                    }
                }

                if (data[k].compareTo(data[biggerIndex]) < 0) {
                    swap(data, k, biggerIndex);

                    // biggerIndex 赋予 k, 重新下次循环
                    // 重新保证 k 节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }

    public static <T extends Comparable<? super T>> void swap(T[] data, int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] integers = {100, 23, 2, 29, 10, 3, 87, 25};
        System.out.println("before:" + Arrays.toString(integers));
        heapSort(integers);
        System.out.println("after:" + Arrays.toString(integers));
    }

}
