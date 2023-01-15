package cn.geekhall.designpattern.strategy.sample1;

/**
 * CouponStrategy
 *
 * @author yiny
 * @date 2023/1/15 21:44
 */
public class CouponStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("优惠券策略");
    }
}
