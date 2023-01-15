package cn.geekhall.designpattern.proxy.dynamicproxy.sample1;

import cn.geekhall.designpattern.proxy.staticproxy.sample2.DynamicDataSourceEntry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OrderServiceDynamicProxy
 *
 * @author yiny
 * @date 2023/1/15 14:57
 */
public class OrderServiceDynamicProxy implements InvocationHandler {
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private Object target;

    public Object getInstance(Object target){
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) throws Throwable {
        beforeMethod(method.getName());
        Object object = method.invoke(target, args);
        afterMethod(method.getName());
        return object;
    }

    private void beforeMethod(String methodName){
        System.out.println("【OrderServiceDynamicProxy】调用之前，方法名：" + methodName);
        try {
            Long time = (Long) target.getClass().getMethod("getCreateTime").invoke(target);
            System.out.println("【OrderServiceDynamicProxy】time = " + time );
            int dbRouter = Math.toIntExact(time);
            System.out.println("【OrderServiceDynamicProxy】静态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据.");
            DynamicDataSourceEntry.set(dbRouter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afterMethod(String methodName){
        System.out.println("【OrderServiceDynamicProxy】调用之后，方法名：" + methodName);
    }
}
