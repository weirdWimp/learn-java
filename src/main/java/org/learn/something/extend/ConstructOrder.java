package org.learn.something.extend;

public class ConstructOrder {

    private abstract static class Father {

        public Father() {
            System.out.println("father constructor:" + getId());
        }

        public int getId() {
            return 10;
        }
    }

    private static class Son extends Father {

        private int id;

        public Son(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }
    }

    public static void main(String[] args) {
        Son son = new Son(1000);
    }

}
