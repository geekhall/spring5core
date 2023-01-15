package cn.geekhall.designpattern.strategy.sample2;

/**
 * Alipay
 *
 * @author yiny
 * @date 2023/1/15 21:57
 */
public class AliPay extends Payment {
    @Override
    public String getName() {
        return "支付宝";
    }

    @Override
    protected double queryBalance(String uid) {
        return 900;
    }
}

