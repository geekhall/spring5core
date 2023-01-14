package cn.geekhall.designpattern.singleton;

/**
 * LazySimpleSingleton
 *
 * @author yiny
 * @date 2023/1/14 22:22
 */
public class LazySimpleSingleton {
    private static LazySimpleSingleton instance = null;

    private LazySimpleSingleton() {
    }

    public synchronized static LazySimpleSingleton getInstance() {
        if (instance == null) {
            instance = new LazySimpleSingleton();
        }
        return instance;
    }
}
