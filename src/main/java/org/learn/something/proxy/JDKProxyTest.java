package org.learn.something.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyTest {

    interface IService {
        void doBusiness();
    }

    static class IServiceAImpl implements IService {
        @Override
        public void doBusiness() {
            System.out.println("IServiceAImpl do business");
        }
    }

    static class IServiceAHandler implements InvocationHandler {

        private Object realObj;

        public IServiceAHandler(Object realObj) {
            this.realObj = realObj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("######> Before executing " + method.getName() + "<######");
            Object result = method.invoke(realObj, args);
            System.out.println("######> After executing " + method.getName() + "<######");
            return result;
        }
    }


    /**
     * use -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true to save proxy class definition
     */
    public static void main(String[] args) {
        IServiceAImpl iServiceA = new IServiceAImpl();
        IService iServiceAProxy = (IService) Proxy.newProxyInstance(IService.class.getClassLoader(),
                new Class[]{IService.class}, new IServiceAHandler(iServiceA));
        iServiceAProxy.doBusiness();

        System.out.println(Proxy.isProxyClass(iServiceAProxy.getClass()));
    }


    /* use JD-GUI to decompile $Proxy.class file */

    // package org.learn.something.proxy;

    // import java.lang.reflect.InvocationHandler;
    // import java.lang.reflect.Method;
    // import java.lang.reflect.Proxy;
    // import java.lang.reflect.UndeclaredThrowableException;
    //
    //     final class $Proxy0 extends Proxy implements JDKProxyTest.IService {
    //         private static Method m1;
    //
    //         private static Method m2;
    //
    //         private static Method m0;
    //
    //         private static Method m3;
    //
    //         public $Proxy0(InvocationHandler paramInvocationHandler) {
    //             super(paramInvocationHandler);
    //         }
    //
    //         public final boolean equals(Object paramObject) {
    //             try {
    //                 return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
    //             } catch (Error|RuntimeException error) {
    //                 throw null;
    //             } catch (Throwable throwable) {
    //                 throw new UndeclaredThrowableException(throwable);
    //             }
    //         }
    //
    //         public final String toString() {
    //             try {
    //                 return (String)this.h.invoke(this, m2, null);
    //             } catch (Error|RuntimeException error) {
    //                 throw null;
    //             } catch (Throwable throwable) {
    //                 throw new UndeclaredThrowableException(throwable);
    //             }
    //         }
    //
    //         public final int hashCode() {
    //             try {
    //                 return ((Integer)this.h.invoke(this, m0, null)).intValue();
    //             } catch (Error|RuntimeException error) {
    //                 throw null;
    //             } catch (Throwable throwable) {
    //                 throw new UndeclaredThrowableException(throwable);
    //             }
    //         }
    //
    //         public final void doBusiness() {
    //             try {
    //                 this.h.invoke(this, m3, null);
    //                 return;
    //             } catch (Error|RuntimeException error) {
    //                 throw null;
    //             } catch (Throwable throwable) {
    //                 throw new UndeclaredThrowableException(throwable);
    //             }
    //         }
    //
    //         static {
    //             try {
    //                 m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
    //                 m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
    //                 m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
    //                 m3 = Class.forName("org.learn.something.proxy.JDKProxyTest$IService").getMethod("doBusiness", new Class[0]);
    //                 return;
    //             } catch (NoSuchMethodException noSuchMethodException) {
    //                 throw new NoSuchMethodError(noSuchMethodException.getMessage());
    //             } catch (ClassNotFoundException classNotFoundException) {
    //                 throw new NoClassDefFoundError(classNotFoundException.getMessage());
    //             }
    //         }
    //     }


}
