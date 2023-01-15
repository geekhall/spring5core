package cn.geekhall.designpattern.strategy.sample2;

import java.util.HashMap;
import java.util.Map;

/**
 * PayState
 *
 * @author yiny
 * @date 2023/1/15 22:01
 */
public class PayStrategy {
    public static final String ALI_PAY = "AliPay";
    public static final String JD_PAY = "JDPay";
    public static final String UNION_PAY = "UnionPay";
    public static final String WECHAT_PAY = "WechatPay";
    public static final String DEFAULT_PAY = ALI_PAY;

    private static Map<String, Payment> payStrategy = new HashMap<>();
    static {
        payStrategy.put(ALI_PAY, new AliPay());
        payStrategy.put(JD_PAY, new JDPay());
        payStrategy.put(UNION_PAY, new UnionPay());
        payStrategy.put(WECHAT_PAY, new WechatPay());
    }
    public  static Payment get(String payKey){
        if (!payStrategy.containsKey(payKey)){
            return payStrategy.get(DEFAULT_PAY);
        }
        return payStrategy.get(payKey);
    }
}
