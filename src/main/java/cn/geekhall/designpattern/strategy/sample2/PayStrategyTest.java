package cn.geekhall.designpattern.strategy.sample2;

/**
 * PayStrategyTest
 *
 * @author yiny
 * @date 2023/1/15 22:16
 */
public class PayStrategyTest {
    public static void main(String[] args) {
        Order order = new Order("1", "2020031400000001", 324.45);
        PayState result = order.pay(PayStrategy.ALI_PAY);
        System.out.println(result);
    }
}
