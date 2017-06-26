package com.sfy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: shifengyuan
 * Date: 2016/8/10

 */

@Controller
@RequestMapping(value = "/udemy")
public class UdemyController {

    /**
     * onemillionlines 跳转页面
     */
    @RequestMapping(value={"/onemillionlines/**"},method= RequestMethod.GET)
    public String getView(HttpServletRequest request, Model model){
        return request.getRequestURI();
    }
}
