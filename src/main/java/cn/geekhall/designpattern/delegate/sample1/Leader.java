package cn.geekhall.designpattern.delegate.sample1;

import java.util.HashMap;
import java.util.Map;

/**
 * Leader
 *
 * @author yiny
 * @date 2023/1/15 18:34
 */
public class Leader implements IEmployee {
    private Map<String, IEmployee> targets = new HashMap<>();

    public Leader() {
        targets.put("加密", new EmployeeA());
        targets.put("登录", new EmployeeB());
    }

    @Override
    public void doing(String command) {
        targets.get(command).doing(command);
    }
}
