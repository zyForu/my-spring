package spring.study.ioc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zy
 * @date 2024/3/24 14:34
 */
public class ReflectUtils {

    public static Object getInstance(Class<?> clazz) {
        try {
            // 使用无参构造函数；如果使用有参构造函数如何处理呢？
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void assignProperty(Object obj, String property, Object value) {
        Class<?> aClass = obj.getClass();
        try {
            Field field = aClass.getDeclaredField(property);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void invokeMd(Object obj, String md) {
        Class<?> aClass = obj.getClass();
        try {
            aClass.getMethod(md).invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getClass(String classNm) {
        try {
            Class<?> aClass = Class.forName(classNm);
            return aClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Class<?> getType(Class<?> clazz, String propertyNm) {
        try {
            Field field = clazz.getDeclaredField(propertyNm);
            field.setAccessible(true);
            return field.getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }


}
