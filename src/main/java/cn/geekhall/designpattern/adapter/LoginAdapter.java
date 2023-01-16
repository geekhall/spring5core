package cn.geekhall.designpattern.adapter;

/**
 * LoginAdapter
 *
 * @author yiny
 * @date 2023/1/16 14:38
 */
public interface LoginAdapter {
    boolean support(Object adapter);
    ResultMsg login(String id, Object adapter);
}
