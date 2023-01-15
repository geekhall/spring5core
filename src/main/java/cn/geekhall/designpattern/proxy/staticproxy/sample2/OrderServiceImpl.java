package cn.geekhall.designpattern.proxy.staticproxy.sample2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * OrderServiceImpl
 *
 * @author yiny
 * @date 2023/1/15 14:37
 */
public class OrderServiceImpl implements IOrderService {
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private OrderDao orderDao;
    public Long getCreateTime() {
        Long time =Long.valueOf(yearFormat.format(new Date()));
        System.out.println("【OrderServiceImpl】getCreateTime :" + time);
        return time;
    }

    public OrderServiceImpl() {
        if (orderDao == null) {
            orderDao = new OrderDao();
        }
    }

    @Override
    public int createOrder(Order order) {
        System.out.println("【OrderServiceImpl】OrderServiceImpl调用OrderDao创建Order");
        return orderDao.insert(order);
    }
}
