package org.learn.something.extend;

public class Eclipse extends Circle {

    public Eclipse() {
    }

    @Override
    public void description() {
        Circle circle = new Circle();
        System.out.println("Eclipse description");
        System.out.println("Hello");
    }

    public void test(Circle circle) {
        String r = circle.r;
    }

}
