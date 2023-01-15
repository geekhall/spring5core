package cn.geekhall.designpattern.proxy.dynamicproxy.jdk;

import cn.geekhall.designpattern.proxy.staticproxy.sample1.IPerson;

/**
 * JDKProxyTest
 *
 * @author yiny
 * @date 2023/1/15 14:54
 */
public class JDKProxyTest {
    public static void main(String[] args) {
        try {
            IPerson obj = (IPerson)new JDKMeipo().getInstance(new Customer());
            obj.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
