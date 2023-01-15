package cn.geekhall.designpattern.strategy.sample1;

/**
 * EmptyStrategy
 *
 * @author yiny
 * @date 2023/1/15 21:45
 */
public class EmptyStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("无优惠策略");
    }
}
