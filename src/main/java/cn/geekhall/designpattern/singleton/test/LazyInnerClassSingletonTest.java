package cn.geekhall.designpattern.singleton.test;

import cn.geekhall.designpattern.singleton.LazyInnerClassSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * LazyInnerClassSingletonTest
 *
 * @author yiny
 * @date 2023/1/14 22:41
 */
public class LazyInnerClassSingletonTest {
    public static void main(String[] args) {
        Class<?> clazz = LazyInnerClassSingleton.class;

        try {
            Constructor c = clazz.getDeclaredConstructor(null);
            // 强制访问
            c.setAccessible(true);

            // 暴力初始化
            Object o1 = c.newInstance();
            Object o2 = c.newInstance();

            System.out.println(o1 == o2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}