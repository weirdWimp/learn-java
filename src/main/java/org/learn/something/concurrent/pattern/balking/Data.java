package org.learn.something.concurrent.pattern.balking;

public class Data {

    private final String name;

    private String content;

    private boolean changed;

    public Data(String name, String content) {
        this.name = name;
        this.content = content;
        changed = true;
    }

    public synchronized void change(String newContent) {
        this.content = newContent;
        changed = true;
    }

    public synchronized void save() {
        if (!changed) {
            return;
        }
        doSave();
        changed = false;
    }

    private void doSave() {
        // save to file
        System.out.println(Thread.currentThread().getName() + " do save " + content);
    }


}
