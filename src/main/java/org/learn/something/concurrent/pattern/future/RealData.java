package org.learn.something.concurrent.pattern.future;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class RealData implements Data {

    private final static Random random = new Random();

    private String content;

    public RealData(char character, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("count must great than 0");
        }

        System.out.println(Thread.currentThread().getName() + " making content start: char:" + character + " count:" + count);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(character);
        }

        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            // do not care
        }

        content = builder.toString();
        System.out.println(Thread.currentThread().getName() + " making content end: char:" + character + " count:" + count);
    }

    @Override
    public String getContent() throws ExecutionException {
        return content;
    }
}
