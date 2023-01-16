package cn.geekhall.designpattern.adapter;

/**
 * SigninService
 *
 * @author yiny
 * @date 2023/1/16 12:12
 */
public class SigninService {
    public ResultMsg regist(String username, String password){
        return new ResultMsg(200, "注册成功", new Member());
    }

    public ResultMsg login(String username, String password){
        return null;
    }
}
