package cn.geekhall.designpattern.strategy.sample1;

/**
 * CashbackStrategy
 *
 * @author yiny
 * @date 2023/1/15 21:45
 */
public class CashbackStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("返现策略");
    }
}
