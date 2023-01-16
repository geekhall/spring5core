package cn.geekhall.demo.servlet;

import cn.geekhall.demo.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GKDispatcherServlet
 *
 * @author yiny
 * @date 2023/1/16 20:30
 */
public class GKDispatcherServlet extends HttpServlet {
    private final Map<String, Object> mapping = new HashMap<>();

    // 保存所有application.properties的配置信息
    private final Properties contextConfig = new Properties();

    // 保存所有扫描到的类名
    private final List<String> classNames = new ArrayList<>();

    // IoC容器，保存所有初始化的bean
    private final Map<String, Object> ioc = new HashMap<>();

    // 保存url和Method的对应关系
//    private final Map<String, Method> handlerMapping = new HashMap<>();

    private final List<Handler> handlerMapping = new ArrayList<>();


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
        Handler handler = getHandler(req);
        if (handler == null){
            resp.getWriter().write("404 Not Found");
            return;
        }
        Class<?>[] paramTypes = handler.getParamTypes();
        Object[] paramValues = new Object[paramTypes.length];
        Map<String, String[]> params = req.getParameterMap();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
            if (!handler.paramIndexMapping.containsKey(param.getKey())){
                continue;
            }
            int index = handler.paramIndexMapping.get(param.getKey());
            paramValues[index] = convert(paramTypes[index], value);
        }
        if (handler.paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = handler.paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
        }
        if (handler.paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int respIndex = handler.paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;
        }
        Object result = handler.method.invoke(handler.controller, paramValues);
        if (result == null || result instanceof Void){
            return;
        }
        resp.getWriter().write(result.toString());
//        String url = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
//
//        if (!this.handlerMapping.containsKey(url)) {
//            try {
//                resp.getWriter().write("404 Not Found!!!");
//                return;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        Method method = this.handlerMapping.get(url);
//        // 第一个参数：方法所在的实例
//        // 第二个参数：调用时所需要的实参
//        Map<String, String[]> params = req.getParameterMap();
//
//        // 获取方法的形参列表
//        Class<?>[] parameterTypes = method.getParameterTypes();
//
//        // 保存请求的url参数列表
//        Object[] paramValues = new Object[parameterTypes.length];
//
//        // 根据参数位置动态赋值
//        for (int i = 0; i < parameterTypes.length; i++) {
//            Class parameterType = parameterTypes[i];
//            if (parameterType == HttpServletRequest.class) {
//                paramValues[i] = req;
//                continue;
//            } else if (parameterType == HttpServletResponse.class) {
//                paramValues[i] = resp;
//                continue;
//            } else if (parameterType == String.class) {
//                // 提取方法中加了注解的参数
//                Annotation[][] pa = method.getParameterAnnotations();
//                for (int j = 0; j < pa.length; j++) {
//                    for (Annotation a : pa[i]) {
//                        if (a instanceof GKRequestParam) {
//                            String paramName = ((GKRequestParam) a).value();
//                            if (!"".equals(paramName.trim())) {
//                                String value = Arrays.toString(params.get(paramName))
//                                        .replaceAll("\\[|\\]", "")
//                                        .replaceAll("\\s", ",");
//                                paramValues[i] = value;
//                            }
//                        }
//                    }
//                }
////                for (Map.Entry<String, String[]> param : params.entrySet()) {
////                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
////                    paramValues[i] = value;
////                }
//            }
//        }
//        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
//        System.out.println(ioc.get(beanName) );
//        System.out.println("method = " + method);
//        method.invoke(ioc.get(beanName), new Object[]{req, resp, params.get("name")[0]});
    }

    /**
     * 将String类型的参数转换为目标类型
     * url 传过来的参数都是String类型的，HTTP是基于字符串协议
     * 只需要把String转换为任意类型就好
     *
     * @param type
     * @param value
     * @return
     */
    private Object convert(Class<?> type, String value){
        if (Integer.class == type){
            return Integer.valueOf(value);
        }
        return value;
    }
    private Handler getHandler(HttpServletRequest req) throws Exception{
        if (handlerMapping.isEmpty()){
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
        for (Handler handler : handlerMapping) {
            try {
                Matcher matcher = handler.pattern.matcher(url);
                if (!matcher.matches()){
                    continue;
                }
                return handler;
            } catch (Exception e) {
                throw e;
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1. 加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        // 2. 扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));

        // 3. 初始化扫描到的类，并且将它们放入到IOC容器之中
        doInstance();

        // 4. 完成依赖注入
        doAutowired();

        // 5. 初始化HandlerMapping
        initHandlerMapping();

        System.out.println("GK Spring framework is init.");

//        InputStream is = null;
//        try {
//            Properties configContext = new Properties();
//            is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
//            configContext.load(is);
//            String scanPackage = configContext.getProperty("scanPackage");
//            doScanner(scanPackage);
//            for (String className : mapping.keySet()) {
//                if (!className.contains(".")) {
//                    continue;
//                }
//                Class<?> clazz = Class.forName(className);
//                if (clazz.isAnnotationPresent(GKController.class)) {
//                    mapping.put(className, clazz.newInstance());
//                    String baseUrl = "";
//                    if (clazz.isAnnotationPresent(GKRequestMapping.class)) {
//                        GKRequestMapping requestMapping = clazz.getAnnotation(GKRequestMapping.class);
//                        baseUrl = requestMapping.value();
//                    }
//                    Method[] methods = clazz.getMethods();
//                    for (Method method : methods) {
//                        if (!method.isAnnotationPresent(GKRequestMapping.class)) {
//                            continue;
//                        }
//                        GKRequestMapping requestMapping = method.getAnnotation(GKRequestMapping.class);
//                        String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
//                        mapping.put(url, method);
//                        System.out.println("Mapped " + url + "," + method);
//                    }
//                } else if (clazz.isAnnotationPresent(GKService.class)) {
//                    GKService service = clazz.getAnnotation(GKService.class);
//                    String beanName = service.value();
//                    if ("".equals(beanName.trim())) {
//                        beanName = clazz.getName();
//                    }
//                    Object instance = clazz.newInstance();
//                    mapping.put(beanName, instance);
//                    for (Class<?> i : clazz.getInterfaces()) {
//                        mapping.put(i.getName(), instance);
//                    }
//                } else {
//                    continue;
//                }
//            }
//            for (Object object : mapping.values()) {
//                if (object == null) {
//                    continue;
//                }
//                Class<?> clazz = object.getClass();
//                if (clazz.isAnnotationPresent(GKController.class)) {
//                    Field[] fields = clazz.getDeclaredFields();
//                    for (Field field : fields) {
//                        if (!field.isAnnotationPresent(GKAutowired.class)) {
//                            continue;
//                        }
//                        GKAutowired autowired = field.getAnnotation(GKAutowired.class);
//                        String beanName = autowired.value().trim();
//                        if ("".equals(beanName)) {
//                            beanName = field.getType().getName();
//                        }
//                        field.setAccessible(true);
//                        field.set(mapping.get(clazz.getName()), mapping.get(beanName));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("GK Spring framework is init.");
    }

    /**
     * 初始化url和Method的一对一对应关系
     */
    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(GKController.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.isAnnotationPresent(GKRequestMapping.class)) {
                GKRequestMapping requestMapping = clazz.getAnnotation(GKRequestMapping.class);
                baseUrl = requestMapping.value();
            }
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(GKRequestMapping.class)) {
                    continue;
                }
                GKRequestMapping requestMapping = method.getAnnotation(GKRequestMapping.class);
                String regex = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                handlerMapping.add(new Handler(pattern, entry.getValue(), method));
                System.out.println("Mapped " + regex + "," + method);
//                String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
//                handlerMapping.put(url, method);
//                System.out.println("Mapped " + url + "," + method);
            }
        }
    }

    /**
     * 自动依赖注入
     */
    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            // 获取所有的字段，包括private、protected、default、public类型的
            // 正常来说，普通的OOP编程只能拿到public类型的字段
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(GKAutowired.class)) {
                    continue;
                }
                // 如果用户没有自定义beanName，默认就根据类型注入
                GKAutowired autowired = field.getAnnotation(GKAutowired.class);
                String beanName = autowired.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                // 如果是public以外的类型，只要加了@Autowired注解，都要强制赋值
                // 强制访问
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                // 判断是否是一个控制器
                if (clazz.isAnnotationPresent(GKController.class)) {
                    Object instance = clazz.newInstance();
                    // 获取类名
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, instance);
                } else if (clazz.isAnnotationPresent(GKService.class)) {
                    // 1. 默认的类名首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    // 2. 自定义的beanName
                    GKService service = clazz.getAnnotation(GKService.class);
                    if (!"".equals(service.value())) {
                        beanName = service.value();
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    // 3. 根据类型自动赋值，投机取巧的方式
                    for (Class<?> i : clazz.getInterfaces()) {
                        if (ioc.containsKey(i.getName())) {
                            throw new Exception("The " + i.getName() + " is exists!!");
                        }
                        ioc.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母小写
     * @param simpleName
     * @return
     */
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        // 大小写字母的ASCII码相差32，而且大写字母的ASCII码要小于小写字母的ASCII码
        // 在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 加载配置文件
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {
        // 直接从类路径下找到Spring主配置文件所在的路径
        // 并且将其读取出来放到Properties对象中
        // 相当于scanPackage=cn.geekhall.demo 从文件中保存到了内存中
        System.out.println("[GKDispatcherServlet.doLoadConfig] contextConfigLocation = " + contextConfigLocation);
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                classNames.add(className);
//                mapping.put(className, null);
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

    private class Handler {
        protected Object controller;   // 保存方法对应的实例
        protected Method method;       // 保存映射的方法
        protected Pattern pattern;     // URL的正则匹配
        protected Map<String, Integer> paramIndexMapping; // 参数顺序

        /**
         * 构造一个Handler基本的参数
         * @param controller
         * @param method
         */
        protected Handler(Pattern pattern,Object controller, Method method) {
            this.controller = controller;
            this.method = method;
            this.pattern = pattern;

            paramIndexMapping = new HashMap<String, Integer>();
            putParamIndexMapping(method);
        }

        private void putParamIndexMapping(Method method) {
            // 提取方法中加了注解的参数
            Annotation[][] pa = method.getParameterAnnotations();
            for (int i = 0; i < pa.length; i++) {
                for (Annotation a : pa[i]) {
                    if (a instanceof GKRequestParam) {
                        String paramName = ((GKRequestParam) a).value();
                        if (!"".equals(paramName.trim())) {
                            paramIndexMapping.put(paramName, i);
                        }
                    }
                }
            }

            // 提取方法中的request和response参数
            Class<?>[] paramsTypes = method.getParameterTypes();
            for (int i = 0; i < paramsTypes.length; i++) {
                Class<?> type = paramsTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramIndexMapping.put(type.getName(), i);
                }
            }
        }

        public Class<?>[] getParamTypes() {
            return method.getParameterTypes();
        }
    }
}
