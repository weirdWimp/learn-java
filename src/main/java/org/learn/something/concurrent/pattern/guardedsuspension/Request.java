package org.learn.something.concurrent.pattern.guardedsuspension;

public class Request {

    private String name;

    public Request(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Request[" + name + "]";
    }
}
