package cn.geekhall.designpattern.singleton.test;

import cn.geekhall.designpattern.singleton.LazySimpleSingleton;

/**
 * ExectorThread
 *
 * @author yiny
 * @date 2023/1/14 22:23
 */
public class ExectorThread implements Runnable {
    @Override
    public void run() {
        LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + ":" + singleton);
    }
}
