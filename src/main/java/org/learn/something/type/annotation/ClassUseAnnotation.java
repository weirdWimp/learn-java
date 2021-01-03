package org.learn.something.type.annotation;

public class ClassUseAnnotation {

    @AliasMethod({"doBusiness", "process"})
    public static void work() {
        System.out.println("working....");
    }

    @AliasMethod({"relax", "ease"})
    @ExceptionAware(RuntimeException.class)
    public static void rest() {
        System.out.println("rest ....");
        throw new RuntimeException("rest exception");
    }
}
