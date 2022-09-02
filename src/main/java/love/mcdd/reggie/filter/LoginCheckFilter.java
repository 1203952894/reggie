package love.mcdd.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import love.mcdd.reggie.common.BaseContext;
import love.mcdd.reggie.common.R;
import org.springframework.http.HttpRequest;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 *
 * @Author:Cola
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        /*
            1. 获取本次请求的 URI
            2. 判断本次请求是否需要处理
            3. 如果不需要处理，直接放行
            4. 判断登录状态，如果已登录，直接放行
            5. 如果未登录，返回登录结果
         */

        // 1. 获取本次请求的 URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}",requestURI);

            // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        // 2. 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);


        // 3. 如果不需要处理，直接放行
        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        // 4. 判断登录状态，如果已登录，直接放行  电脑端
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已经登录：{}",request.getSession().getAttribute("employee"));

            // 获取当前用户 id
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            long id = Thread.currentThread().getId();
            log.info("线程id为{}",id);

            // 获取当前线程的 ThreadLocal


            filterChain.doFilter(request, response);
            return;
        }

        // 4. 判断登录状态，如果已登录，直接放行  移动端
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已经登录：{}",request.getSession().getAttribute("user"));

            // 获取当前用户 id
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            long id = Thread.currentThread().getId();
            log.info("线程id为{}",id);

            // 获取当前线程的 ThreadLocal


            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
        // 5. 如果未登录，返回登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls 不需要拦截的 url
     * @param requestURI 当前请求的 url
     * @return 是 true 否 false
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
