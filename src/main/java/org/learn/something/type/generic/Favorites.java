package org.learn.something.type.generic;

import java.util.HashMap;
import java.util.Map;

public class Favorites {

    private Map<Class<?>, Object> favorites = new HashMap<>();

    /**
     * <code>
     * <p>
     * Class clazz = String.class;
     * Integer i = 1;
     * putFavorite(clazz, i);
     * </code>
     * <p>
     * 使用原生类型，这里编译运行不会有错，客户端恶意调用，造成类型不安全； Get 进行强制转换时会出现异常
     * 所以 put 时需要判断 instance 是否是 clazz 类型的实例， 使用 Class.cast 方法进行再次转换
     *
     * @param clazz
     * @param instance
     * @param <T>
     */
    public <T> void putFavorite(Class<T> clazz, T instance) {
        favorites.put(clazz, clazz.cast(instance));
    }

    public <T> T getFavorite(Class<T> clazz) {
        return clazz.cast(favorites.get(clazz));
    }
}
