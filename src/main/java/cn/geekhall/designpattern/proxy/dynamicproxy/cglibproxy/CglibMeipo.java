package cn.geekhall.designpattern.proxy.dynamicproxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CglibMeipo
 *
 * @author yiny
 * @date 2023/1/15 18:20
 */
public class CglibMeipo implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) throws Exception{
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object obj = methodProxy.invokeSuper(o, objects);
        after();
        return obj;
    }

    private void before(){
        System.out.println("【CglibMeipo】我是媒婆：我要给你找对象，现在已经确认你的需求");
        System.out.println("【CglibMeipo】开始物色");
    }
    private void after(){
        System.out.println("【CglibMeipo】如果合适的话，就准备办事");
    }
}
