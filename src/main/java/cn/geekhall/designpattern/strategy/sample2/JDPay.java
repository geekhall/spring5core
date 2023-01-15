package cn.geekhall.designpattern.strategy.sample2;

/**
 * JDPay
 *
 * @author yiny
 * @date 2023/1/15 22:00
 */
public class JDPay extends Payment{
    @Override
    public String getName() {
        return "京东白条";
    }

    @Override
    protected double queryBalance(String uid) {
        return 200;
    }
}
