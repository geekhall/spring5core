package cn.geekhall.designpattern.prototype.clone;

import java.util.List;
import java.util.ArrayList;

/**
 * PrototypeTest
 *
 * @author yiny
 * @date 2023/1/15 13:53
 */
public class PrototypeTest {
    public static void main(String[] args) {
        ConcretePrototypeA concretePrototypeA = new ConcretePrototypeA();
        concretePrototypeA.setAge(18);
        concretePrototypeA.setName("prototype");
        List hobbies = new ArrayList();
        hobbies.add("football");
        concretePrototypeA.setHobbies(hobbies);
        System.out.println("concretePrototypeA = " + concretePrototypeA);

        Client client = new Client(concretePrototypeA);
        ConcretePrototypeA concretePrototypeClone = (ConcretePrototypeA) client.startClone(concretePrototypeA);
        System.out.println("concretePrototypeClone = " + concretePrototypeClone);
        System.out.println("克隆对象中的引用类型地址值：" + concretePrototypeClone.getHobbies());
        System.out.println("原对象中的引用类型地址值：" + concretePrototypeA.getHobbies());
        System.out.println("对象地址比较：" + (concretePrototypeClone.getHobbies() == concretePrototypeA.getHobbies()));
    }
}
