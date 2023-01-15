package cn.geekhall.designpattern.delegate.spring;

/**
 * MemberController
 *
 * @author yiny
 * @date 2023/1/15 20:55
 */
public class MemberController {
    public void getMemberById(String mid){
        System.out.println("查询会员，会员id=" + mid);
    }
}
