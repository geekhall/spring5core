package cn.geekhall.designpattern.singleton;

/**
 * LazyDoubleCheckSingleton
 *
 * @author yiny
 * @date 2023/1/14 22:35
 */
public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton instance = null;

    private LazyDoubleCheckSingleton() {
    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new LazyDoubleCheckSingleton();
                    // 1.分配内存给这个对象
                    // 2.初始化对象
                    // 3.设置instance指向刚分配的内存地址
                    // 以上三步顺序可能会被重排序，导致线程A执行到第三步时，instance已经不为空了，但是对象还没有初始化完成。
                    // 所以，需要加上volatile关键字，禁止指令重排序。
                }
            }
        }
        return instance;
    }
}
