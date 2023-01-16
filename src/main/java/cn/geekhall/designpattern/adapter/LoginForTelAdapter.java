package cn.geekhall.designpattern.adapter;

/**
 * LoginForTelAdapter
 *
 * @author yiny
 * @date 2023/1/16 14:44
 */
public class LoginForTelAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTelAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
