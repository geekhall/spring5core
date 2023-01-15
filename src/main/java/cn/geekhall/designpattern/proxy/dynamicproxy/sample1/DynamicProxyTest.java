package cn.geekhall.designpattern.proxy.dynamicproxy.sample1;

import cn.geekhall.designpattern.proxy.staticproxy.sample2.IOrderService;
import cn.geekhall.designpattern.proxy.staticproxy.sample2.Order;
import cn.geekhall.designpattern.proxy.staticproxy.sample2.OrderServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DynamicProxyTest
 *
 * @author yiny
 * @date 2023/1/15 15:03
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        try {
            Order order = new Order();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse("2023/01/15");
            System.out.println("【DynamicProxyTest】date.getTime() = " + date.getTime());
            order.setCreateTime(date.getTime());

            IOrderService orderService = (IOrderService) new OrderServiceDynamicProxy().getInstance(new OrderServiceImpl());
            orderService.createOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
