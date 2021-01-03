package org.learn.something.concurrent.pattern.future;

public class Host {

    public Data request(final char c, final int count) {
        System.out.println("Request Method start: " + c + " " + count);
        final FutureData futureData = new FutureData();

        new Thread(() -> {
            RealData realData = null;
            try {
                realData = new RealData(c, count);
                futureData.setRealData(realData);
            } catch (Exception e) {
                futureData.setException(e);
            }
        }).start();

        System.out.println("Request Method end: " + c + " " + count);
        return futureData;
    }

}
