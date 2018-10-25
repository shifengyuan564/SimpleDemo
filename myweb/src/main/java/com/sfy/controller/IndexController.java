package com.sfy.controller;

import com.sfy.shiro.MD5;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*模拟京东帮首页*/

@Controller
@RequestMapping("/")
public class IndexController {


    @RequestMapping(value = "/login")
    public String submit(HttpServletRequest request, String username, String password, Model model) {
        String msg = "";

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                return "redirect:/index";
            } else {
                return "login";
            }
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
            model.addAttribute("message", msg);
            System.out.println(msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            model.addAttribute("message", msg);
            System.out.println(msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            model.addAttribute("message", msg);
            System.out.println(msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            model.addAttribute("message", msg);
            System.out.println(msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            model.addAttribute("message", msg);
            System.out.println(msg);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            model.addAttribute("message", msg);
            System.out.println(msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", msg);
            System.out.println(msg);
        }

        return "site/login";
    }


    @RequestMapping(value = "/error")
    public String error() {
        return "error/illegal-request";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "site/index";
    }

    @RequestMapping(value = "/top/homepage")
    public String topHomepage() {
        return "site/homepage";
    }

    @RequestMapping(value = "/top/baseinfo")
    public String topBaseInfo() {
        return "site/baseinfo";
    }

    @RequestMapping(value = "/vali")
    public String testValidate() {
        return "site/vali";
    }

    /**
     * 跳转页面
     */
    @RequestMapping(value = {"/getView/**"}, method = RequestMethod.GET)
    public String getView(HttpServletRequest request, Model model) {
        String view = request.getRequestURI().substring("/getView".length());
        model.addAttribute("request", request);
        //model.addAttribute("userPin", LoginContext.getLoginContext().getPin());
        return view;
    }
}
