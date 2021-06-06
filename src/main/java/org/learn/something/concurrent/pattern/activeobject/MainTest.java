package org.learn.something.concurrent.pattern.activeobject;

public class MainTest {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        try {
            new MakeStringThread("Alice", activeObject).start();
            new MakeStringThread("Bob", activeObject).start();
            new DisplayThread("Jerry", activeObject).start();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // ignore
        } finally {
            activeObject.shutdown();
        }
    }

}
