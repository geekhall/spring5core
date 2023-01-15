package cn.geekhall.designpattern.proxy.dynamicproxy.gpproxy;

import java.lang.reflect.Method;

/**
 * GPInvocationHandler
 *
 * @author yiny
 * @date 2023/1/15 16:08
 */
public interface GPInvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
