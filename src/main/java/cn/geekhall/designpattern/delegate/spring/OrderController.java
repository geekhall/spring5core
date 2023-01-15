package cn.geekhall.designpattern.delegate.spring;

/**
 * OrderController
 *
 * @author yiny
 * @date 2023/1/15 20:56
 */
public class OrderController {
    public void getOrderById(String oid){
        System.out.println("查询订单，订单id=" + oid);
    }
}
