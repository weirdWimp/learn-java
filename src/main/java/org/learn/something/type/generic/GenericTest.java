package org.learn.something.type.generic;

import java.util.List;

public class GenericTest {

    /**
     * 直接使用存在编译问题；虽然意图上确实不需要明确指定 list 的参数类型，只使用了一次，
     * 但是如果直接该方法，方法签名中的 list 使用通配符，该 list 将不能调用 set 方法，接受一个 get 出来的 Object 对象
     * 通配符 Collection 除了 null, 不能接受任何其它类型的对象，因为 list 的参数类型是未知的，直接放进一个 Object 对象，
     * 是类型不安全的
     */
    private static void unUsedSwap(List<?> list, int i, int j) {
        // list.set(i, list.set(j, list.get(i)));
    }

    /**
     * 不直接使用 swapHelper 方法的原因在于
     *
     * @param list
     * @param i
     * @param j
     */
    public static void swap(List<?> list, int i, int j) {
        swapHelper(list, i, j);
    }

    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

}
