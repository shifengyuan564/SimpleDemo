/**
 *
 */
package com.sfy.shiro;

import com.sfy.domain.Role;
import com.sfy.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;


public class ShiroFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Principal principal = httpRequest.getUserPrincipal();

        if (principal != null) {
            Subject subjects = SecurityUtils.getSubject();
            // 为了简单，这里初始化一个用户。实际项目项目中应该去数据库里通过名字取用户：
            // 例如：User user = userService.getByAccount(principal.getName());
            User user = new User();
            user.setUserName("sfy");
            user.setPassword("sfy");
            user.setRole(new Role("administrator"));
            if (user.getUserName().equals(principal.getName())) {
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
                subjects = SecurityUtils.getSubject();
                subjects.login(token);
                subjects.getSession();
            } else {
                // 如果用户为空，则subjects信息登出
                if (subjects != null) {
                    subjects.logout();
                }
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }

}
