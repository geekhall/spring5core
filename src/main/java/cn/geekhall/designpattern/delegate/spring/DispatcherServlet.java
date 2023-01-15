package cn.geekhall.designpattern.delegate.spring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * DispatcherServlet
 *
 * @author yiny
 * @date 2023/1/15 20:56
 */
public class DispatcherServlet extends HttpServlet {
    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 从request中取出URL，自己去匹配一个HandlerMapping
        // 根据一个HandlerMapping获得一个HandlerAdapter
        // 解析某一个Handler的某一个Method的形参和返回值之后，统一封装为ModelAndView对象
        // 通过HandlerAdapter实现调用Handler的Method
        // 通过ViewResolver实现动态模板的解析
        // 通过ViewResult实现模板的渲染（直接输出字符串，也可以放到ModelAndView中）
        String url = request.getRequestURI();
        String mid = request.getParameter("mid");
        System.out.println("url = " + url);
        url = url.replace("/spring5core/", "");
        if ("getMemberById".equals(url)){   // http://localhost:8080/spring5core/getMemberById?mid=1
            new MemberController().getMemberById(mid);
        } else if ("getOrderById".equals(url)){ // http://localhost:8080/spring5core/getOrderById?mid=1
            new OrderController().getOrderById(mid);
        } else if ("logout".equals(url)){ // http://localhost:8080/spring5core/logout
            new SystemController().logout();
        } else {
            // 404
            response.getWriter().write("404 Not Found!");
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("500 Exception, Detail : " + Arrays.toString(e.getStackTrace()));
        }
    }
}
