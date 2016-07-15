package com.sfy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: shifengyuan@jd.com
 * Date: 2016/6/12
 *
 * Demo 1 (music site): Pluralsight – Extending Bootstrap3 with CSS, JavaScript, and jQuery
 * Demo 2 ():
 */

@Controller
@RequestMapping(value = "/pluralsight")
public class PluralsightController {

    /**
     * musicsite 跳转页面
     */
    @RequestMapping(value={"/musicsite/**"},method= RequestMethod.GET)
    public String getMusicView(HttpServletRequest request, Model model){
        return request.getRequestURI();
    }
}
