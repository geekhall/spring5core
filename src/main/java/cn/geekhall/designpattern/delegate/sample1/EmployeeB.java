package cn.geekhall.designpattern.delegate.sample1;

/**
 * EmployeeB
 *
 * @author yiny
 * @date 2023/1/15 18:34
 */
public class EmployeeB implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("我是员工B，我现在开始干" + command + "工作");
    }
}
