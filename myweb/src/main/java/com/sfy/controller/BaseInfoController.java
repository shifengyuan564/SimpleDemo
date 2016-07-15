package com.sfy.controller;

import com.sfy.common.Page;
import com.sfy.domain.Link;
import com.sfy.service.LinkManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 京东帮 基础信息相关操作
 * Author: shifengyuan@jd.com
 * Date: 2016/3/16
 */

@Controller
@RequestMapping(value = "/baseinfo")
public class BaseInfoController {

    @Autowired
    LinkManageService linkManageService;

    /**
     * 根据主键查询一条友情链接信息
     */
    @RequestMapping(value = "/querySingleLink", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Link querySingleLink(Link links) {
        return linkManageService.querySingleLink(links);
    }


    /**
     * @Description: 分页查询友情链接
     */
    @RequestMapping(value = "/querylinks", produces = "application/json;charset=UTF-8",  method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> pageQuery(Link link,
                                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Map<String,Object> result = new HashMap<String,Object>();

        Page<Link> pageBean = new Page<Link>();
        pageBean.setSize(pageSize);
        pageBean.setSkip(page * pageSize);

        pageBean = linkManageService.pageQuery(pageBean, link);

        result.put("resultList",pageBean.getData());
        result.put("totalCount",pageBean.getTotal());
        return result;
    }

}
