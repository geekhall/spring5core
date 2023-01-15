package cn.geekhall.designpattern.proxy.dynamicproxy.cglibproxy;

import cn.geekhall.designpattern.proxy.dynamicproxy.jdk.Customer;

/**
 * CglibMeipoTest
 *
 * @author yiny
 * @date 2023/1/15 18:23
 */
public class CglibMeipoTest {
    public static void main(String[] args) {
        try {
            Customer obj = (Customer) new CglibMeipo().getInstance(Customer.class);
            obj.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
