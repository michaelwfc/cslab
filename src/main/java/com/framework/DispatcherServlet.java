package com.framework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.lang.reflect.Method;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.annotations.GetMapping;
import com.annotations.PostMapping;
import com.controller.IndexController;
import com.framework.ViewEngine;

// 在MVC框架中创建一个接收所有请求的Servlet，通常我们把它命名为DispatcherServlet，它总是映射到/，
// 然后，根据不同的Controller的方法定义的@Get或@Post的Path决定调用哪个方法，
// 最后，获得方法返回的ModelAndView后，渲染模板，写入HttpServletResponse，即完成了整个MVC的处理。
@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    // 存储请求路径到某个具体方法的映射：
    // 处理一个GET请求是通过GetDispatcher对象完成的
    private Map<String, GetDispatcher> getMappings = new HashMap<>();
    private Map<String, PostDispatcher> postMappings = new HashMap<>();

    // 定义 controllers : // 关于 Controller 类的 List泛型
    private List<Class<?>> controllers = List.of(IndexController.class);

    private ViewEngine viewEngine;

    @Override
    public void init() throws ServletException {
        // 获取所有的 get 方法
        logger.info("init {}...", getClass().getSimpleName());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 依次处理每个Controller:
        for (Class<?> controllerClass : controllers) {
            try {
                Object controllerInstance = controllerClass.getConstructor().newInstance();
                // 依次处理每个Method:
                for (Method method : controllerClass.getMethods()) {
                    if (method.getAnnotation(GetMapping.class) != null) {
                        // 处理@Get:
                        if (method.getReturnType() != ModelAndView.class && method.getReturnType() != void.class) {
                            throw new UnsupportedOperationException(
                                    "Unsupported return type: " + method.getReturnType() + " for method: " + method);
                        }
                        for (Class<?> parameterClass : method.getParameterTypes()) {
                            if (!supportedGetParameterTypes.contains(parameterClass)) {
                                throw new UnsupportedOperationException(
                                        "Unsupported parameter type: " + parameterClass + " for method: " + method);
                            }
                        }
                        String[] parameterNames = Arrays.stream(method.getParameters()).map(p -> p.getName())
                                .toArray(String[]::new);
                        String path = method.getAnnotation(GetMapping.class).value();
                        logger.info("Found GET: {} => {}", path, method);
                        this.getMappings.put(path, new GetDispatcher(controllerInstance, method, parameterNames,
                                method.getParameterTypes()));
                    } else if (method.getAnnotation(PostMapping.class) != null) {
                        // 处理@Post:
                        if (method.getReturnType() != ModelAndView.class && method.getReturnType() != void.class) {
                            throw new UnsupportedOperationException(
                                    "Unsupported return type: " + method.getReturnType() + " for method: " + method);
                        }
                        Class<?> requestBodyClass = null;
                        for (Class<?> parameterClass : method.getParameterTypes()) {
                            if (!supportedPostParameterTypes.contains(parameterClass)) {
                                if (requestBodyClass == null) {
                                    requestBodyClass = parameterClass;
                                } else {
                                    throw new UnsupportedOperationException("Unsupported duplicate request body type: "
                                            + parameterClass + " for method: " + method);
                                }
                            }
                        }
                        String path = method.getAnnotation(PostMapping.class).value();
                        logger.info("Found POST: {} => {}", path, method);
                        this.postMappings.put(path, new PostDispatcher(controllerInstance, method,
                                method.getParameterTypes(), objectMapper));
                    }
                }
            } catch (ReflectiveOperationException e) {
                throw new ServletException(e);
            }
        }
        // 创建ViewEngine:
        this.viewEngine = new ViewEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // 根据路径查找 GetDispatcher:
        GetDispatcher dispatcher = this.getMappings.get(path);
        if (dispatcher == null) {
            // 未找到返回404:
            resp.sendError(404);
            return;
        }
        // 调用Controller方法获得返回值 ModelAndView:
        ModelAndView mv = null;
        try {
            mv = dispatcher.invoke(req, resp);
        } catch (ReflectiveOperationException e) {
            throw new ServletException(e);
        }
        // 允许返回null:
        if (mv == null) {
            return;
        }
        // 允许返回`redirect:`开头的view表示重定向:
        if (mv.view.startsWith("redirect:")) {
            resp.sendRedirect(mv.view.substring(9));
            return;
        }
        // 将模板引擎渲染的内容写入响应:
        PrintWriter pw = resp.getWriter();
        this.viewEngine.render(mv, pw);
        pw.flush();
    }

    private static final Set<Class<?>> supportedGetParameterTypes = Set.of(int.class, long.class, boolean.class,
            String.class, HttpServletRequest.class, HttpServletResponse.class, HttpSession.class);

    private static final Set<Class<?>> supportedPostParameterTypes = Set.of(HttpServletRequest.class,
            HttpServletResponse.class, HttpSession.class);

}

class GetDispatcher {
    Object instance; // Controller实例
    Method method; // Controller方法
    String[] parameterNames; // 方法参数名称
    Class<?>[] parameterClasses; // 方法参数类型

    public GetDispatcher(Object instance, Method method, String[] parameterNames, Class<?>[] parameterClasses) {
        super();
        this.instance = instance;
        this.method = method;
        this.parameterNames = parameterNames;
        this.parameterClasses = parameterClasses;
    }

    // 定义 invoke 方法，输入 request， 输出 response
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ReflectiveOperationException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else if (parameterClass == int.class) {
                arguments[i] = Integer.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == long.class) {
                arguments[i] = Long.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == boolean.class) {
                arguments[i] = Boolean.valueOf(getOrDefault(request, parameterName, "false"));
            } else if (parameterClass == String.class) {
                arguments[i] = getOrDefault(request, parameterName, "");
            } else {
                throw new RuntimeException("Missing handler for type: " + parameterClass);
            }
        }
        // 通过构造某个方法需要的所有参数列表，使用反射调用该方法后返回结果。
        return (ModelAndView) this.method.invoke(this.instance, arguments);
    }

    private String getOrDefault(HttpServletRequest request, String name, String defaultValue) {
        String s = request.getParameter(name);
        return s == null ? defaultValue : s;
    }

}

class PostDispatcher {
    Object instance; // Controller实例
    Method method; // Controller方法
    Class<?>[] parameterClasses; // 方法参数类型
    ObjectMapper objectMapper; // JSON映射

    public PostDispatcher(Object instance, Method method, Class<?>[] parameterClasses, ObjectMapper objectMapper) {
        this.instance = instance;
        this.method = method;
        this.parameterClasses = parameterClasses;
        this.objectMapper = objectMapper;
    }

    // POST请求严格地来说不能有URL参数，所有数据都应当从Post Body中读取。
    // 这里我们为了简化处理，只支持JSON格式的POST请求，这样，把Post数据转化为JavaBean就非常容易。
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ReflectiveOperationException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else {
                // 读取JSON并解析为JavaBean:
                BufferedReader reader = request.getReader();
                arguments[i] = this.objectMapper.readValue(reader, parameterClass);
            }
        }
        return (ModelAndView) this.method.invoke(instance, arguments);
    }
}