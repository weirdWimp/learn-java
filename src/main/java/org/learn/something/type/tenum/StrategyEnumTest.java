package org.learn.something.type.tenum;

/**
 * 策略枚举
 */
public class StrategyEnumTest {

    public enum PayRollDay {
        MONDAY(PayType.WEEKDAY),
        TUESDAY(PayType.WEEKDAY),
        SATURDAY(PayType.WEEKEND);

        private PayType payType;

        PayRollDay(PayType payType) {
            this.payType = payType;
        }

        public double pay(double hours, double rate) {
            return payType.pay(hours, rate);
        }

        /**
         * 策略枚举，PayRollDay 多个枚举常量共享相同的行为，使用基于枚举常量的特定方法实现会存在重复代码，模板代码
         */
        private enum PayType {

            WEEKDAY() {
                @Override
                double overtimePay(double hours, double rate) {
                    return hours <= HOURS_PER_SHIFT ? 0 :
                            (hours - HOURS_PER_SHIFT) * rate / 2;
                }
            },

            WEEKEND() {
                @Override
                double overtimePay(double hours, double rate) {
                    return hours * rate / 2;
                }
            };

            private static final int HOURS_PER_SHIFT = 8;

            double pay(double hours, double rate) {
                double basePay = hours * rate;
                return basePay + overtimePay(hours, rate);
            }

            abstract double overtimePay(double hours, double rate);
        }
    }

    public static void main(String[] args) {
        // 10 * 10 + (10 - 8) * 5 = 110
        System.out.println(PayRollDay.MONDAY.pay(10, 10));
    }
}
