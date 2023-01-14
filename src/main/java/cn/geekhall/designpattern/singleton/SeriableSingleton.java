package cn.geekhall.designpattern.singleton;

import java.io.Serializable;

/**
 * SeriableSingleton
 *
 * @author yiny
 * @date 2023/1/14 22:46
 */
public class SeriableSingleton implements Serializable {
    private static final SeriableSingleton instance = new SeriableSingleton();

    private SeriableSingleton() {
    }

    public static SeriableSingleton getInstance() {
        return instance;
    }

    /**
     * 重写readResolve方法，防止反序列化时生成新的对象。
     * @return
     */
    private Object readResolve(){
        return instance;
    }
}
