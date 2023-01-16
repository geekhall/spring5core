package cn.geekhall.designpattern.adapter;

/**
 * LoginForSinaAdapter
 *
 * @author yiny
 * @date 2023/1/16 14:43
 */
public class LoginForSinaAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForSinaAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
