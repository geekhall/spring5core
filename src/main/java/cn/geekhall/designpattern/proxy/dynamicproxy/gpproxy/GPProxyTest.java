package cn.geekhall.designpattern.proxy.dynamicproxy.gpproxy;

import cn.geekhall.designpattern.proxy.dynamicproxy.jdk.Customer;
import cn.geekhall.designpattern.proxy.staticproxy.sample1.IPerson;

/**
 * GPProxyTest
 *
 * @author yiny
 * @date 2023/1/15 16:53
 */
public class GPProxyTest {
    public static void main(String[] args) {
        try {
            IPerson obj = (IPerson) new GPMeipo().getInstance(new Customer());
            System.out.println(obj.getClass());
            obj.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
