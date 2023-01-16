package cn.geekhall.designpattern.adapter;

/**
 * PassortTest
 *
 * @author yiny
 * @date 2023/1/16 14:55
 */
public class PassportTest {
    public static void main(String[] args) {
        IPassportForThird passportForThird = new PassportForThirdAdapter();
        passportForThird.loginForQQ("123");
    }
}
