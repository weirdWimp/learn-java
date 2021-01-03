package org.learn.something.type.tenum;

import java.util.Collection;
import java.util.EnumSet;


public class ExtendableEnum {

    /**
     * 用接口模拟可伸缩的枚举
     */
    private interface Operation {
        double apply(double a, double b);
    }

    private enum BaseOperation implements Operation {
        DIVIDE("+") {
            @Override
            public double apply(double a, double b) {
                return a + b;
            }
        };

        private String optStr;

        BaseOperation(String optStr) {
            this.optStr = optStr;
        }

        @Override
        public String toString() {
            return optStr;
        }
    }

    private enum AdvanceOperation implements Operation {
        POWER("+") {
            @Override
            public double apply(double a, double b) {
                return Math.pow(a, b);
            }
        };

        private String optStr;

        AdvanceOperation(String optStr) {
            this.optStr = optStr;
        }

        @Override
        public String toString() {
            return optStr;
        }
    }

    private static <T extends Enum<T> & Operation> void test(Class<T> type, double x, double y) {
        for (T t : type.getEnumConstants()) {
            System.out.println(t.apply(x, y));
        }
    }

    public static void main(String[] args) {
        test(BaseOperation.class, 1, 2);
        test(AdvanceOperation.class, 2, 2);

        EnumSet<BaseOperation> baseOperations = EnumSet.allOf(BaseOperation.class);
        for (BaseOperation baseOperation : baseOperations) {
            System.out.println(baseOperation);
        }
    }


}
