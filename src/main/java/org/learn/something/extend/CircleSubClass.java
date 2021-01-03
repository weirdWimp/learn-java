package org.learn.something.extend;

public class CircleSubClass extends Circle {



    public class SubNoneStaticInnerClass extends NoneStaticInnerClass {

        public SubNoneStaticInnerClass(String filed) {
            super(filed);
        }

        public void subPrint() {
            super.getFiled();
            getR();
        }
    }


}
