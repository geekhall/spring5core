package cn.geekhall.demo.service.impl;

import cn.geekhall.demo.service.IDemoService;

/**
 * DemoService
 *
 * @author yiny
 * @date 2023/1/16 20:57
 */
public class DemoService implements IDemoService {
    @Override
    public String get(String name) {
        return "My name is " + name;
    }
}
