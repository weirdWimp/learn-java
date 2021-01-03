package org.learn.something.extend;

public class Guacamole extends BurritoOptionalExtra {
    private static final double price = 1.2;

    public Guacamole(Burrito burrito) {
        super(burrito, price);
    }
}
