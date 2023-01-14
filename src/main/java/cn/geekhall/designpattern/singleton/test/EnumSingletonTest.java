package cn.geekhall.designpattern.singleton.test;

import cn.geekhall.designpattern.singleton.EnumSingleton;

import java.io.*;

/**
 * EnumSingletonTest
 *
 * @author yiny
 * @date 2023/1/14 23:03
 */
public class EnumSingletonTest {
    public static void main(String[] args) {
        EnumSingleton instance = null;
        EnumSingleton instance2 = EnumSingleton.getInstance();

        instance2.setData(new Object());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("EnumSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance2);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("EnumSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            instance = (EnumSingleton) ois.readObject();
            ois.close();

            System.out.println(instance.getData());
            System.out.println(instance2.getData());
            System.out.println(instance.getData() == instance2.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
