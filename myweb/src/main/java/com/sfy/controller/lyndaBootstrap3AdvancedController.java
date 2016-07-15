package com.sfy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * Author: shifengyuan@jd.com
 * Date: 2016/6/29
 */
@Controller
@RequestMapping("/lyndaBootstrap")
public class lyndaBootstrap3AdvancedController {
    /**
     * 跳转页面
     */
    @RequestMapping(value={"/**"},method= RequestMethod.GET)
    public String getView(HttpServletRequest request, Model model){

        String basePath = "bootstrap/LyndaBootstrap3Advanced/";
        String subPath = request.getRequestURI().substring("/lyndaBootstrap/".length());

        model.addAttribute("request", request);
        //model.addAttribute("userPin", LoginContext.getLoginContext().getPin());
        return basePath+subPath;
    }
}
