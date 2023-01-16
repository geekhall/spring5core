package cn.geekhall.demo.mvc.action;

import cn.geekhall.demo.annotation.GKAutowired;
import cn.geekhall.demo.annotation.GKController;
import cn.geekhall.demo.annotation.GKRequestMapping;
import cn.geekhall.demo.annotation.GKRequestParam;
import cn.geekhall.demo.service.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DemoAction
 *
 * @author yiny
 * @date 2023/1/16 20:58
 */

@GKController
@GKRequestMapping("/demo")
public class DemoAction {

    @GKAutowired
    private IDemoService demoService;

    @GKRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp, @GKRequestParam("name") String name){
        String result = demoService.get(name);
        try {
            resp.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GKRequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse resp, @GKRequestParam("a") Integer a, @GKRequestParam("b") Integer b){
        try {
            resp.getWriter().write(a + "+" + b + "=" + (a + b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GKRequestMapping("/square")
    public void square(HttpServletRequest req, HttpServletResponse resp, @GKRequestParam("a") Integer a){
        try {
            resp.getWriter().write(a + "的平方是：" + (a * a));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GKRequestMapping("/remove")
    public void remove(HttpServletRequest req, HttpServletResponse resp, @GKRequestParam("id") Integer id){
        // ...
    }
}
