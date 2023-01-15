package cn.geekhall.designpattern.proxy.staticproxy.sample2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OrderServiceStaticProxy
 *
 * @author yiny
 * @date 2023/1/15 14:40
 */
public class OrderServiceStaticProxy implements IOrderService {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private IOrderService orderService;

    public OrderServiceStaticProxy(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public int createOrder(Order order) {
        before();
        Long time = order.getCreateTime();
        System.out.println("【OrderServiceStaticProxy】time in createOrder = " + time);
        int dbRouter = Integer.parseInt(yearFormat.format(new Date(time)));
        System.out.println("【OrderServiceStaticProxy】静态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据");
        DynamicDataSourceEntry.set(dbRouter);
        int result = orderService.createOrder(order);
        after();
        return result;
    }

    @Override
    public Long getCreateTime() {
        return orderService.getCreateTime();
    }

    private void before(){
        System.out.println("Proxy before method.");
    }

    private void after(){
        System.out.println("Proxy after method.");
    }
}
