package cn.geekhall.designpattern.singleton;

/**
 * HungrySingleton
 *
 * @author yiny
 * @date 2023/1/14 22:21
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}

