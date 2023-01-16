package cn.geekhall.designpattern.adapter;

/**
 * LoginForOOAdapter
 *
 * @author yiny
 * @date 2023/1/16 14:39
 */
public class LoginForQQAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        System.out.println("LoginForQQAdapter.support: " + adapter);
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        System.out.println("LoginForQQAdapter.login : id = " + id);
        return null;
    }
}
