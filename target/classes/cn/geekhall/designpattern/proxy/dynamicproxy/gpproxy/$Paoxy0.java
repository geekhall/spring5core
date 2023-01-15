package cn.geekhall.designpattern.proxy.dynamicproxy.gpproxy;
import cn.geekhall.designpattern.proxy.staticproxy.sample1.IPerson;
import java.lang.reflect.*;
public class $Proxy0 implements cn.geekhall.designpattern.proxy.staticproxy.sample1.IPerson{
GPInvocationHandler h;
public $Proxy0(GPInvocationHandler h) { 
this.h = h;
}
public void findLove() {
try{
Method m = cn.geekhall.designpattern.proxy.staticproxy.sample1.IPerson.class.getMethod("findLove", new Class[]{});
this.h.invoke(this, m, new Object[]{});
}catch (Error _ex) { }catch (Throwable e){
throw new UndeclaredThrowableException(e);
}}}
