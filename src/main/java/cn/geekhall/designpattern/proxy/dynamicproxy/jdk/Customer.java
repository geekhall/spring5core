package cn.geekhall.designpattern.proxy.dynamicproxy.jdk;

import cn.geekhall.designpattern.proxy.staticproxy.sample1.IPerson;

/**
 * Customer
 *
 * @author yiny
 * @date 2023/1/15 14:53
 */
public class Customer implements IPerson {
    @Override
    public void findLove() {
        System.out.println("高富帅");
        System.out.println("身高180cm");
        System.out.println("有房有车");
    }
}
