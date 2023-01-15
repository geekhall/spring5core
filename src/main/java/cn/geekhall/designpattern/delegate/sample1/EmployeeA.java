package cn.geekhall.designpattern.delegate.sample1;

/**
 * EmployeeA
 *
 * @author yiny
 * @date 2023/1/15 18:33
 */
public class EmployeeA implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("我是员工A，我现在开始干" + command + "工作");
    }
}
