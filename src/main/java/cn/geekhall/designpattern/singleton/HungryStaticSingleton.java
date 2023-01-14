package cn.geekhall.designpattern.singleton;

/**
 * HungryStaticSingleton
 *
 * @author yiny
 * @date 2023/1/14 22:21
 */
public class HungryStaticSingleton {
    private static final HungryStaticSingleton instance;

    static {
        instance = new HungryStaticSingleton();
    }

    private HungryStaticSingleton() {
    }

    public static HungryStaticSingleton getInstance() {
        return instance;
    }
}
