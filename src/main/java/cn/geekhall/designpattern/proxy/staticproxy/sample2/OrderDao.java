package cn.geekhall.designpattern.proxy.staticproxy.sample2;

/**
 * OrderDao
 *
 * @author yiny
 * @date 2023/1/15 14:36
 */
public class OrderDao {
    public int insert(Order order){
        System.out.println("OrderDao创建Order成功！");
        return 1;
    }
}
