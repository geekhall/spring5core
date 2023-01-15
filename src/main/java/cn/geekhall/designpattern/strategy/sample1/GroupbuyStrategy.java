package cn.geekhall.designpattern.strategy.sample1;

/**
 * GroupbuyStrategy
 *
 * @author yiny
 * @date 2023/1/15 21:45
 */
public class GroupbuyStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("团购策略");
    }
}
