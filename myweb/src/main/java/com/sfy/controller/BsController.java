package com.sfy.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:测试bootstrap基本功能
 * Author: shifengyuan@jd.com
 * Date: 2016/3/14
 */
@Controller
@RequestMapping("/bs")
public class BsController {

    private final static Log logger = LogFactory.getLog(BsController.class);

    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    public String pageDispatcher(@PathVariable Integer id) {

        return "bootstrap/page"+id;
    }

    @RequestMapping(value = "/page/modal", method = RequestMethod.GET)
    public String pageModal() {

        return "bootstrap/BsModal";
    }
}
