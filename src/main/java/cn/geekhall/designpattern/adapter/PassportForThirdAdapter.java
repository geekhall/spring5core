package cn.geekhall.designpattern.adapter;

/**
 * PassportForThirdAdapter
 *
 * @author yiny
 * @date 2023/1/16 14:49
 */
public class PassportForThirdAdapter extends SigninService implements IPassportForThird {
    public ResultMsg loginForQQ(String id) {
        System.out.println("PassportForThirdAdapter.loginForQQ: id = " + id);
        return processLogin(id, LoginForQQAdapter.class);
    }

    @Override
    public ResultMsg loginForWechat(String id) {
        return processLogin(id, LoginForWechatAdapter.class);
    }

    @Override
    public ResultMsg loginForToken(String token) {
        return processLogin(token, LoginForTokenAdapter.class);
    }

    @Override
    public ResultMsg loginForTelephone(String telephone, String code) {
        return processLogin(telephone, LoginForTelAdapter.class);
    }

    @Override
    public ResultMsg loginForRegist(String username, String password) {
        return null;
    }

    private ResultMsg processLogin(String key, Class<? extends LoginAdapter> clazz)  {
        System.out.println("PassportForThirdAdapter.processLogin: key = " + key);
        try {
            LoginAdapter adapter = clazz.newInstance();
            if (adapter.support(adapter)){
                return adapter.login(key, adapter);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
