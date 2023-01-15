package cn.geekhall.designpattern.proxy.staticproxy.sample2;

/**
 * OrderServiceImpl
 *
 * @author yiny
 * @date 2023/1/15 14:37
 */
public class OrderServiceImpl implements IOrderService {
    private OrderDao orderDao;

    public OrderServiceImpl() {
        if (orderDao == null) {
            orderDao = new OrderDao();
        }
    }

    @Override
    public int createOrder(Order order) {
        System.out.println("OrderServiceImpl调用OrderDao创建Order");
        return orderDao.insert(order);
    }
}
