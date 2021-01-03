package org.learn.something.extend;

public class Circle implements Comparable<Circle> {

    private static final String PI = "3.141592653";

    /**
     * static inner type: Enum, Annotation, Interface
     */
    private enum Order {
        ASC, DESC
    }

    /**
     * 静态成员类型，与外部类的任何实例都不关联
     * 静态成员类型，可以访问所在类型的所有静态成员，包括私有成员, 其他静态成员类型
     * 静态成员类型可以定义嵌入任何深度的其他静态成员类型中，但是不能嵌套在其他类型中定义
     */
    public static class StaticInnerClass {
        private String pi = PI;

        public StaticInnerClass() {
        }

        public String getPi() {
            return pi;
        }
    }

    /**
     * 只有类才能作为非静态成员类，类似于实例字段或实例方法
     * 非静成员类的实例始终和外层类的一个实例关联
     * 能访问外层类的所有字段和方法，包括静态和非静态， private 的
     *
     * 不能包含任何静态字段、静态方法，但是可以包含 static final的常量
     *
     * 成员类中可以包含成员类，并且嵌套的层级不限
     */
    public class NoneStaticInnerClass {
        private String filed;

        private static final String s = "";

        public NoneStaticInnerClass(String filed) {
            String r = Circle.this.r;
            this.filed = filed;
        }

        public String getFiled() {
            return filed;
        }
    }

    protected String r;

    public Circle() {
    }

    public Circle(String r) {
        this.r = r;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    /**
     * 所在类可以访问静态成员类型中的所有成员，包括私有成员
     */
    public void description() {
        StaticInnerClass innerClass = new StaticInnerClass();
        String pi = innerClass.pi;
        System.out.println("Circle description");
    }

    @Override
    public int compareTo(Circle o) {
        Order asc = Order.ASC;
        return 0;
    }
}
