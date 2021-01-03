package org.learn.something.type.tenum;

public enum Operation {

    PLUS("+") {
        @Override
        public double apply(double a, double b) {
            return a + b;
        }
    },

    MINUS("-") {
        @Override
        public double apply(double a, double b) {
            return a - b;
        }
    },

    TIMES("*") {
        @Override
        public double apply(double a, double b) {
            return a * b;
        }
    },

    DIVIDE("/") {
        @Override
        public double apply(double a, double b) {
            return a / b;
        }
    };

    private String optStr;

    Operation(String optStr) {
        this.optStr = optStr;
    }

    @Override
    public String toString() {
        return optStr;
    }

    /**
     * 特定于枚举常量的抽象方法，由每一个枚举举常量进行实现，避免使用 switch-case 语句编写容易出错的代码
     *
     * @param a
     * @param b
     * @return
     */
    public abstract double apply(double a, double b);

    // 维护成本较高的实现方式
    // public double apply(double a, double b) {
    //     double res;
    //     switch (this) {
    //         case PLUS:
    //             res = a + b;
    //             break;
    //         case MINUS:
    //             res = a - b;
    //             break;
    //         default:
    //             throw new UnsupportedOperationException(this.toString());
    //     }
    //     return res;
    // }
}
