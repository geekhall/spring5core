package cn.geekhall.designpattern.strategy.sample1;

import java.util.HashMap;
import java.util.Map;

/**
 * PromotionStrategyFactory
 *
 * @author yiny
 * @date 2023/1/15 21:48
 */
public class PromotionStrategyFactory {
    private static final IPromotionStrategy EMPTY = new EmptyStrategy();
    private static final IPromotionStrategy COUPON = new CouponStrategy();
    private static final IPromotionStrategy CASHBACK = new CashbackStrategy();
    private static final IPromotionStrategy GROUPBUY = new GroupbuyStrategy();

    private static Map<String, IPromotionStrategy> PROMOTION_STRATEGY_MAP = new HashMap<>();
    static {
        PROMOTION_STRATEGY_MAP.put(PromotionKey.COUPON, COUPON);
        PROMOTION_STRATEGY_MAP.put(PromotionKey.CASHBACK, CASHBACK);
        PROMOTION_STRATEGY_MAP.put(PromotionKey.GROUPBUY, GROUPBUY);
    }
    private PromotionStrategyFactory() {
    }

    private interface PromotionKey{
        String COUPON = "COUPON";
        String CASHBACK = "CASHBACK";
        String GROUPBUY = "GROUPBUY";
    }

    public static IPromotionStrategy getPromotionStrategy(String promotionKey) {
        IPromotionStrategy promotionStrategy = PROMOTION_STRATEGY_MAP.get(promotionKey);
        return promotionStrategy == null ? EMPTY : promotionStrategy;
    }
}
