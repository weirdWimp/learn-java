package org.learn.something.concurrent.pattern.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JavaFutureMain {


    private static class JFutureData extends FutureTask<RealData> implements Data {
        public JFutureData(Callable<RealData> callable) {
            super(callable);
        }

        @Override
        public String getContent() throws ExecutionException {
            String content = null;
            try {
                content = get().getContent();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return content;
        }
    }

    private static class JHost {
        public Data request(final char c, final int count) {
            System.out.println("Request Method start: " + c + " " + count);

            JFutureData jFutureData = new JFutureData(() -> new RealData(c, count));
            new Thread(jFutureData).start();

            System.out.println("Request Method end: " + c + " " + count);
            return jFutureData;
        }
    }


    public static void main(String[] args) {
        JHost jHost = new JHost();

        Data data1 = jHost.request('#', 20);
        Data data2 = jHost.request('$', 10);
        Data data3 = jHost.request('*', 50);

        System.out.println("Main Thread other job start");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main Thread other job end");

        try {
            System.out.println("data1.getContent() = " + data1.getContent());
            System.out.println("data2.getContent() = " + data2.getContent());
            System.out.println("data3.getContent() = " + data3.getContent());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
