package cn.geekhall.designpattern.strategy.sample1;

/**
 * PromotionActivity
 *
 * @author yiny
 * @date 2023/1/15 21:46
 */
public class PromotionActivity {
    private IPromotionStrategy promotionStrategy;

    public PromotionActivity(IPromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void execute(){
        promotionStrategy.doPromotion();
    }
}
