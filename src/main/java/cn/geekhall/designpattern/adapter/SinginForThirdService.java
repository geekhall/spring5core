package cn.geekhall.designpattern.adapter;

/**
 * SinginForThirdService
 *
 * @author yiny
 * @date 2023/1/16 13:47
 */
public class SinginForThirdService extends SigninService{
    public ResultMsg loginForQQ(String openId) {
        return loginForRegistor(openId, null);
    }

    public ResultMsg loginForWechat(String openId) {
        return null;
    }

    public ResultMsg loginForToken(String token) {
        return null;
    }

    public ResultMsg loginForTelephone(String telephone, String code) {
        return null;
    }

    public ResultMsg loginForRegistor(String username, String password) {
        super.regist(username, null);
        return super.login(username, null);
    }
}
