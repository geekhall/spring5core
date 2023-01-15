package cn.geekhall.designpattern.delegate.sample1;

/**
 * DelegateTest
 *
 * @author yiny
 * @date 2023/1/15 20:53
 */
public class DelegateTest {
    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.command("加密", new Leader());
        boss.command("登录", new Leader());
    }
}
