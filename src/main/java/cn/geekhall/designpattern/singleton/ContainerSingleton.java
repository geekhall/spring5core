package cn.geekhall.designpattern.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ContainerSingleton
 * 容器式单例，适用于单例对象较多的情况。
 * 优点：不用担心单例对象过多，导致内存溢出。
 * 缺点：不够优雅，需要手动注册单例对象。非线程安全。
 *
 * @author yiny
 * @date 2023/1/14 23:08
 */
public class ContainerSingleton {
    private ContainerSingleton() {
    }
    private static Map<String, Object> ioc = new ConcurrentHashMap<>();

    public static Object getBean(String className) {
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object obj = null;
                try {
                    obj = Class.forName(className).newInstance();
                    ioc.put(className, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return obj;
            } else {
                return ioc.get(className);
            }
        }
    }

}
