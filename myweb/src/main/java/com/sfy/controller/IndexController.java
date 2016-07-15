package com.sfy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/*模拟京东帮首页*/

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = "/index")
    public String index() {
        return "site/index";
    }

    @RequestMapping(value = "/top/homepage")
    public String topHomepage(){
        return "site/homepage";
    }

    @RequestMapping(value = "/top/baseinfo")
    public String topBaseInfo(){
        return "site/baseinfo";
    }

    /**
     * 跳转页面
     */
    @RequestMapping(value={"/getView/**"},method=RequestMethod.GET)
    public String getView(HttpServletRequest request, Model model){
        String view = request.getRequestURI().substring("/getView".length());
        model.addAttribute("request", request);
        //model.addAttribute("userPin", LoginContext.getLoginContext().getPin());
        return view;
    }
}
