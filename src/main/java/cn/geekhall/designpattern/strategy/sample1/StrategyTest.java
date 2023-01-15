package cn.geekhall.designpattern.strategy.sample1;

/**
 * StrategyTest
 *
 * @author yiny
 * @date 2023/1/15 21:46
 */
public class StrategyTest {
    public static void main(String[] args) {
//        PromotionActivity activity618 = new PromotionActivity(new CouponStrategy());
//        PromotionActivity activity1111 = new PromotionActivity(new CashbackStrategy());
//
//        activity618.execute();
//        activity1111.execute();
        String promotionKey = "GROUPBUY";
        PromotionActivity activity = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy(promotionKey));
        activity.execute();

    }
}
