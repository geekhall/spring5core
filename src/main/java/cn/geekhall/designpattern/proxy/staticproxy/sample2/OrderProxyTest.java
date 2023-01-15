package cn.geekhall.designpattern.proxy.staticproxy.sample2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OrderProxyTest
 *
 * @author yiny
 * @date 2023/1/15 14:43
 */
public class OrderProxyTest {
    public static void main(String[] args) {
        Order order = new Order();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = sdf.parse("2023/01/15");
            order.setCreateTime(date.getTime());
            IOrderService orderService = new OrderServiceStaticProxy(new OrderServiceImpl());
            orderService.createOrder(order);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
