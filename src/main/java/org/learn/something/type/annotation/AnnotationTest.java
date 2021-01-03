package org.learn.something.type.annotation;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationTest {


    public static void main(String[] args) {
        // aliasAnnotationTest();
        exceptionAwareTest();
    }

    private static void exceptionAwareTest() {
        try {
            Class clazz = Class.forName("org.learn.something.type.annotation.ClassUseAnnotation");
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                if (declaredMethod.isAnnotationPresent(ExceptionAware.class)) {
                    try {
                        declaredMethod.invoke(null);
                    } catch (InvocationTargetException e) {

                        Class<? extends Exception>[] exTypes = declaredMethod.getAnnotation(ExceptionAware.class).value();
                        for (Class<? extends Exception> exType : exTypes) {
                            if (exType.isInstance(e.getCause())) {
                                System.out.println(declaredMethod.getName() + "() 方法抛出了预期的异常: " + exType.getCanonicalName());
                            }
                        }
                    } catch (Exception e) {
                        // 其他异常，如方法需要参数，方法非静态方法，不能直接调用
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void aliasAnnotationTest() {
        try {
            Class clazz = Class.forName("org.learn.something.type.annotation.ClassUseAnnotation");
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                if (declaredMethod.isAnnotationPresent(AliasMethod.class)) {
                    try {

                        declaredMethod.invoke(null);
                        AliasMethod aliasMethod = declaredMethod.getAnnotation(AliasMethod.class);
                        System.out.println("method has alias: " + String.join(",", aliasMethod.value()) + "\n");

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Illegal Access or Invoke exception:" + e.getCause());
                        e.printStackTrace();

                    } catch (Exception e) {
                        // 其他异常，如方法需要参数，方法非静态方法，不能直接调用
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
