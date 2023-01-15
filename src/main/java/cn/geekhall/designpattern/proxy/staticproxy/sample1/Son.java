package cn.geekhall.designpattern.proxy.staticproxy.sample1;

/**
 * Son
 *
 * @author yiny
 * @date 2023/1/15 14:22
 */
public class Son implements IPerson{
    @Override
    public void findLove() {
        System.out.println("儿子要求：肤白貌美大长腿");
    }
}
