package cn.geekhall.designpattern.delegate.sample1;

/**
 * Boss
 *
 * @author yiny
 * @date 2023/1/15 18:36
 */
public class Boss {
    public void command(String command, IEmployee leader){
        leader.doing(command);
    }
}
