package cn.geekhall.designpattern.strategy.sample2;

/**
 * WechatPay
 *
 * @author yiny
 * @date 2023/1/15 21:59
 */
public class WechatPay extends Payment{
    @Override
    public String getName() {
        return "微信支付";
    }

    @Override
    protected double queryBalance(String uid) {
        return 256;
    }
}
