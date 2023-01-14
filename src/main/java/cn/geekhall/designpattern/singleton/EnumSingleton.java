package cn.geekhall.designpattern.singleton;

/**
 * EnumSingleton
 * 枚举式单例，线程安全，防止反序列化。Effective Java作者Josh Bloch提倡的方式。
 *
 * @author yiny
 * @date 2023/1/14 23:01
 */
public enum EnumSingleton {
    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
