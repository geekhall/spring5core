package cn.geekhall.designpattern.delegate.spring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DispatcherServlet
 *
 * @author yiny
 * @date 2023/1/15 20:56
 */
public class DispatcherServlet extends HttpServlet {
    private List<Handler> handlerMapping = new ArrayList<>();

    public void init() throws ServletException {
        try {
            Class<?> memberControllerClass = MemberController.class;
            Class<?> orderControllerClass = OrderController.class;
            Class<?> systemControllerClass = SystemController.class;
            handlerMapping.add(new Handler()
                    .setController(memberControllerClass.newInstance())
                    .setMethod(memberControllerClass.getMethod("getMemberById", new Class[]{String.class}))
                    .setUrl("getMemberById"));
            handlerMapping.add(new Handler()
                    .setController(orderControllerClass.newInstance())
                    .setMethod(orderControllerClass.getMethod("getOrderById", new Class[]{String.class}))
                    .setUrl("getOrderById"));
            handlerMapping.add(new Handler()
                    .setController(systemControllerClass.newInstance())
                    .setMethod(systemControllerClass.getMethod("logout", new Class[]{}))
                    .setUrl("logout"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static {
        System.out.println("DispatcherServlet init");

    }
    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 从request中取出URL，自己去匹配一个HandlerMapping
        // 根据一个HandlerMapping获得一个HandlerAdapter
        // 解析某一个Handler的某一个Method的形参和返回值之后，统一封装为ModelAndView对象
        // 通过HandlerAdapter实现调用Handler的Method
        // 通过ViewResolver实现动态模板的解析
        // 通过ViewResult实现模板的渲染（直接输出字符串，也可以放到ModelAndView中）

        // 1、通过request拿到url，每个url对应一个Servlet，url通过HandlerMapping找到这个Servlet
        String url = request.getRequestURI();
        String mid = request.getParameter("mid");
        System.out.println("url = " + url);
        url = url.replace("/spring5core/", "");
        String methodName = null;
        // 2、通过url找到对应的HandlerMapping
        Handler handler = null;
        for (Handler h : handlerMapping) {
            if (url.equals(h.getUrl())) {
                System.out.println("找到对应的HandlerMapping : " + h);
                handler = h;
                methodName = h.getMethod().getName();
                break;
            }
        }
        // 2、将具体的任务分发给Method（通过反射调用其对应的方法）
        if (handler == null) {
            System.out.println("没有找到对应的HandlerMapping");
            return;
        }
        // 3、获取Method的形参列表
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        // 4、获取request的参数
        Object[] paramValues = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class parameterType = parameterTypes[i];
            if (parameterType == HttpServletRequest.class) {
                paramValues[i] = request;
            } else if (parameterType == HttpServletResponse.class) {
                paramValues[i] = response;
            } else if (parameterType == String.class) {
                // 通过运行时的状态去拿到你传过来的参数
                Method method = handler.getController().getClass().getMethod( methodName, new Class[]{String.class});
                paramValues[i] = method.invoke(handler.getController(), mid);
            } else  {
                System.out.println("参数类型不匹配");
            }
        }
//         5、利用反射机制来调用
//        handler.getMethod().invoke(handler.getController(), paramValues);

        // 简单实现
//        Object object =  handler.getMethod().invoke(handler.getController(), mid);

//        if ("getMemberById".equals(url)){   // http://localhost:8080/spring5core/getMemberById?mid=1
//            new MemberController().getMemberById(mid);
//        } else if ("getOrderById".equals(url)){ // http://localhost:8080/spring5core/getOrderById?mid=1
//            new OrderController().getOrderById(mid);
//        } else if ("logout".equals(url)){ // http://localhost:8080/spring5core/logout
//            new SystemController().logout();
//        } else {
//            // 404
//            response.getWriter().write("404 Not Found!");
//        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("500 Exception, Detail : " + Arrays.toString(e.getStackTrace()));
        }
    }
    class Handler {
        private Object controller;
        private Method method;
        private String url;

        public Object getController() {
            return controller;
        }

        public Handler setController(Object controller) {
            this.controller = controller;
            return this;
        }

        public Method getMethod() {
            return method;
        }

        public Handler setMethod(Method method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Handler setUrl(String url) {
            this.url = url;
            return this;
        }
    }
}
