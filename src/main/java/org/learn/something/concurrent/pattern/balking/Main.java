package org.learn.something.concurrent.pattern.balking;

public class Main {

    public static void main(String[] args) {
        Data data = new Data("data", "empty");
        new AutoSaveThread("AutoSave", data).start();
        new ChangeThread("CallSave", data).start();
    }

}
