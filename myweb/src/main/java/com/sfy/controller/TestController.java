package com.sfy.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Description:测试各种插件
 * Author: shifengyuan@jd.com
 * Date: 2016/1/10
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final static Log logger = LogFactory.getLog(TestController.class);

    @RequestMapping(value = "/jsontest", method = RequestMethod.GET)
    public String index(@RequestParam(value = "locale", required = false) Locale locale,
            HttpServletRequest request, HttpServletResponse response, Model view) {

        logger.debug("-------------------------进入index.vm---------------------------");
        return "test/jsonTest";
    }

    @RequestMapping(value = "/typeahead", method = RequestMethod.GET)
    public String typeahead(HttpServletRequest request, HttpServletResponse response, Model view) {

        logger.debug("-------------------------进入typeahead-demo.vm---------------------------");
        return "test/typeahead-demo";
    }

    @RequestMapping(value = "/datagrid", method = RequestMethod.GET)
    public String datagrid() {

        logger.debug("-------------------------进入datagridJQ.vm---------------------------");
        return "test/datagridJQ";
    }
}
