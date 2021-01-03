package org.learn.something.extend;

public abstract class BurritoOptionalExtra implements Burrito {

    private final Burrito burrito;
    private final double price;

    protected BurritoOptionalExtra(Burrito burrito, double price) {
        this.burrito = burrito;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return burrito.getPrice() + price;
    }
}
