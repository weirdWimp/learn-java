package org.learn.something.type;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class ClassTest {

    public static void main(String[] args) {
        // classObjTest();
        // classLoaderTest();
        // proxyTest();
        methodHandleTest();

        Object[] objects = new Long[10];
    }


    public static void classObjTest() {
        Class<?> clz = Void.TYPE;
        clz = int.class;
        clz = Integer.class;
        clz = Integer.TYPE;
        clz = ClassTest.class;

        System.out.println(" clz: " + clz.getName());
        for (Method method : clz.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType() == Deprecated.class) {
                    System.out.println("Deprecated method Name() = " + method.getName());
                }
            }
        }
    }

    @Deprecated
    public void test() {

    }

    /**
     * 从url中加载类，加载的是编译后的class文件（字节码文件），加载后，会生成包含元数据的Class对象，
     * 即类对象，（中间包含了一系列检查、校验、准备工作、静态初始化工作等），此时，类就完全加载好了，可以
     * 使用了
     */
    public static void classLoaderTest() {
        String urlStr = "file://E:/DevelopmentKit/idea-workspace/java-workspace/jdbc-tutorial/target/classes/";
        try (URLClassLoader loader = new URLClassLoader(new URL[]{new URL(urlStr)})) {
            //loadClass() 方法的参数是类文件的二进制名
            final Class<?> clz = loader.loadClass("com.candy.datasource.AcquireConnectionToDB");
            System.out.println("clz = " + clz.getName());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态代理
     */
    public static void proxyTest() {
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                final String methodName = method.getName();
                if (methodName.equals("clear")) {
                    System.out.println("clear operation");
                    return null;
                }
                return method.invoke(proxy, args);
            }
        };

        List list = (List) Proxy.newProxyInstance(ClassTest.class.getClassLoader(), new Class[]{List.class}, handler);

        System.out.println("list instanceof Proxy = " + (list instanceof Proxy));
        list.clear();

        //handler中未处理的方法，会出现异常
        // list.add("");
    }

    /**
     * 方法句柄，和反射类似，但提供了比反射更清晰、更面向对象的方式，遵循修饰符的访问机制（lookup对象创建的上下文有关)
     */
    public static void methodHandleTest() {
        MethodType methodType = MethodType.methodType(int.class, String.class);

        //基于当前执行方法获取上下文Lookup对象
        //Lookup对象只会返回创建这个对象的上下文中可以访问的方法
        //此时的上下文可以访问这个类中定义的length方法
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            //查找需要的方法
            final MethodHandle lengthMethod = lookup.findVirtual(ClassTest.class, "length", methodType);
            //调用方法句柄
            final int result = (int) lengthMethod.invoke(new ClassTest(), "wrong me");
            System.out.println("result = " + result);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    public int length(@NotNull String input) {
        return input.length();
    }
}
