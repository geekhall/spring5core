package cn.geekhall.designpattern.adapter;

/**
 * IPasswordForThird
 *
 * @author yiny
 * @date 2023/1/16 14:47
 */
public interface IPassportForThird {
    ResultMsg loginForQQ(String id);

    ResultMsg loginForWechat(String id);

    ResultMsg loginForToken(String token);

    ResultMsg loginForTelephone(String telephone, String code);

    ResultMsg loginForRegist(String username, String password);
}
