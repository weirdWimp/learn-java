package org.learn.something.type.generic;


import com.google.common.collect.Lists;
import org.learn.something.extend.Circle;
import org.learn.something.extend.Eclipse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GenericMethodTest {

    public static void main(String[] args) {


        Eclipse eclipse1 = new Eclipse();
        eclipse1.setR("Love");

        Eclipse eclipse2 = new Eclipse();
        eclipse2.setR("Hate");

        List<Eclipse> eclipseList = Lists.newArrayList(eclipse1, eclipse2);
        Function<Circle, CharSequence> mapFunc = Circle::getR;

        List<CharSequence> sequences = mapCollection(eclipseList, mapFunc);
        for (CharSequence sequence : sequences) {
            System.out.println(sequence);
        }

    }

    /**
     * PECS - Produce extends, Consume super
     *
     * @param list
     * @param mapFunc
     * @param <E>
     * @param <R>
     * @return
     */
    private static <E, R> List<R> mapCollection(List<? extends E> list, Function<? super E, ? extends R> mapFunc) {
        List<R> resList = new ArrayList<>();
        for (E e : list) {
            R apply = mapFunc.apply(e);
            resList.add(apply);
        }
        return resList;
    }
}
