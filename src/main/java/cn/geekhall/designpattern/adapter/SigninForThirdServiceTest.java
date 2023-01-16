package cn.geekhall.designpattern.adapter;

/**
 * SigninForThirdServiceTest
 *
 * @author yiny
 * @date 2023/1/16 14:33
 */
public class SigninForThirdServiceTest {
    public static void main(String[] args) {
        SinginForThirdService service = new SinginForThirdService();
        service.loginForQQ("test-openid");
    }
}
