package cn.geekhall.designpattern.strategy.sample2;

/**
 * UnionPay
 *
 * @author yiny
 * @date 2023/1/15 22:00
 */
public class UnionPay extends Payment{
    @Override
    public String getName() {
        return "银联支付";
    }

    @Override
    protected double queryBalance(String uid) {
        return 150;
    }
}
