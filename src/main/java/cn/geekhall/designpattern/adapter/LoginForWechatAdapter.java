package cn.geekhall.designpattern.adapter;

/**
 * LoginForWechatAdapter
 *
 * @author yiny
 * @date 2023/1/16 14:46
 */
public class LoginForWechatAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForWechatAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
