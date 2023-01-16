package cn.geekhall.demo.servlet;

import cn.geekhall.demo.annotation.GKAutowired;
import cn.geekhall.demo.annotation.GKController;
import cn.geekhall.demo.annotation.GKRequestMapping;
import cn.geekhall.demo.annotation.GKService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

/**
 * GKDispatcherServlet
 *
 * @author yiny
 * @date 2023/1/16 20:30
 */
public class GKDispatcherServlet extends HttpServlet {
    private Map<String, Object> mapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception, Detail:" + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        if (!this.mapping.containsKey(url)) {
            try {
                resp.getWriter().write("404 Not Found!!!");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Method method = (Method) this.mapping.get(url);
        Map<String, String[]> params = req.getParameterMap();
        method.invoke(this.mapping.get(method.getDeclaringClass().getName()), new Object[]{req, resp, params.get("name")[0]});
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        InputStream is = null;
        try {
            Properties configContext = new Properties();
            is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
            configContext.load(is);
            String scanPackage = configContext.getProperty("scanPackage");
            doScanner(scanPackage);
            for (String className : mapping.keySet()) {
                if (!className.contains(".")) {
                    continue;
                }
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(GKController.class)) {
                    mapping.put(className, clazz.newInstance());
                    String baseUrl = "";
                    if (clazz.isAnnotationPresent(GKRequestMapping.class)) {
                        GKRequestMapping requestMapping = clazz.getAnnotation(GKRequestMapping.class);
                        baseUrl = requestMapping.value();
                    }
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (!method.isAnnotationPresent(GKRequestMapping.class)) {
                            continue;
                        }
                        GKRequestMapping requestMapping = method.getAnnotation(GKRequestMapping.class);
                        String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                        mapping.put(url, method);
                        System.out.println("Mapped " + url + "," + method);
                    }
                } else if (clazz.isAnnotationPresent(GKService.class)) {
                    GKService service = clazz.getAnnotation(GKService.class);
                    String beanName = service.value();
                    if ("".equals(beanName.trim())) {
                        beanName = clazz.getName();
                    }
                    Object instance = clazz.newInstance();
                    mapping.put(beanName, instance);
                    for (Class<?> i : clazz.getInterfaces()) {
                        mapping.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
            for (Object object : mapping.values()) {
                if (object == null) {
                    continue;
                }
                Class<?> clazz = object.getClass();
                if (clazz.isAnnotationPresent(GKController.class)) {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (!field.isAnnotationPresent(GKAutowired.class)) {
                            continue;
                        }
                        GKAutowired autowired = field.getAnnotation(GKAutowired.class);
                        String beanName = autowired.value().trim();
                        if ("".equals(beanName)) {
                            beanName = field.getType().getName();
                        }
                        field.setAccessible(true);
                        field.set(mapping.get(clazz.getName()), mapping.get(beanName));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("GK Spring framework is init.");
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                mapping.put(className, null);
//                try {
//                    Class<?> clazz = Class.forName(className);
//                    if (clazz.isAnnotationPresent(GKController.class)) {
//                        mapping.put("/" + clazz.getAnnotation(GKRequestMapping.class).value(), clazz.newInstance());
//                    } else if (clazz.isAnnotationPresent(GKService.class)) {
//                        GKService service = clazz.getAnnotation(GKService.class);
//                        String beanName = service.value();
//                        if ("".equals(beanName.trim())) {
//                            beanName = clazz.getName();
//                        }
//                        Object instance = clazz.newInstance();
//                        mapping.put(beanName, instance);
//                        for (Class<?> i : clazz.getInterfaces()) {
//                            mapping.put(i.getName(), instance);
//                        }
//                    } else {
//                        continue;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }
}
