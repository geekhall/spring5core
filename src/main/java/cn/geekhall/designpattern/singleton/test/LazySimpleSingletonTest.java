package cn.geekhall.designpattern.singleton.test;

/**
 * LazySimpleSingletonTest
 *
 * @author yiny
 * @date 2023/1/14 22:23
 */
public class LazySimpleSingletonTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());
        t1.start();
        t2.start();
        System.out.println("End");
    }
}
