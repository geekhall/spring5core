package cn.geekhall.designpattern.adapter;

/**
 * LoginForTokenAdapter
 *
 * @author yiny
 * @date 2023/1/16 14:45
 */
public class LoginForTokenAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTokenAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
