package com.morirain.flowmemo.utils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/30
 */


public class SingletonFactory {

    private static Map<Class, Object> sInstances = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <E> E getInstance(Class<E> className) {
        Object instance = sInstances.get(className);
        if (instance == null) {
            synchronized (SingletonFactory.class) {
                instance = sInstances.get(className);
                if (instance == null) {
                    try {
                        instance = className.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    sInstances.put(className, instance);
                }
            }
        }
        return (E) instance;
    }
}
