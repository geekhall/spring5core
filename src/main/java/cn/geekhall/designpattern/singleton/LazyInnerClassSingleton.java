package cn.geekhall.designpattern.singleton;

/**
 * LazyInnerClassSingleton
 * 兼顾饿汉式的内存浪费，也兼顾synchronized性能问题，
 * 内部类一定是要在方法调用之前初始化，巧妙地避免了线程安全问题。
 *
 * @author yiny
 * @date 2023/1/14 22:37
 */
public class LazyInnerClassSingleton {
    private LazyInnerClassSingleton() {
        if (LazyHolder.LAZY != null) {
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    /**
     * 每一个关键字都不是多余的
     * static 是为了使单例的空间共享
     * final 保证这个方法不会被重写、重载
     *
     * @return
     */
    public static final LazyInnerClassSingleton getInstance() {
        // 在返回结果以前，一定会先加载内部类
        return LazyHolder.LAZY;
    }

    // 默认不加载
    private static class LazyHolder {
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
