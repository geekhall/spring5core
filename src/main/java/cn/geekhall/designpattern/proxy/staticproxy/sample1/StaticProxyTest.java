package cn.geekhall.designpattern.proxy.staticproxy.sample1;

/**
 * StaticProxyTest
 *
 * @author yiny
 * @date 2023/1/15 14:24
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        Son son = new Son();
        Father father = new Father(son);
        father.findLove();
    }
}
