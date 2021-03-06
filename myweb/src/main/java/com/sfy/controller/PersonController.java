package com.sfy.controller;

import com.google.gson.Gson;
import com.sfy.common.DoubleEditor;
import com.sfy.common.JsonUtil;
import com.sfy.common.LasViewException;
import com.sfy.domain.MakeExptResDto;
import com.sfy.domain.Person;
import com.sfy.service.MakeExptService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 学习@ResponseBody 和 @RequestBody
 *
 * @author: shifengyuan@jd.com
 * @date: 2016-01-08
 */

@Controller
@RequestMapping(value = "/person")
public class PersonController {

    private final static org.apache.log4j.Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private MakeExptService makeExptService;

    /**
     * jsonTest首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "test/jsonTest";
    }


    //  前台页面传的时间跟Date类型绑定
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logger.debug("======================= initBinder ==========================");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), false));
        binder.registerCustomEditor(Double.class, "age", new DoubleEditor());
    }

    /**
     * 查询个人信息
     */
    @RequestMapping(value = "/profile/{id}/{name}/{status}/{birth}", method = RequestMethod.GET)
    @ResponseBody
    public Person porfile(@PathVariable int id, @PathVariable String name,
                          @PathVariable boolean status, @PathVariable Date birth) {
        return new Person(id, name, status, birth);
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST )
    @ResponseBody
    public Person login(@RequestBody String personStr) {

        Person person = null;
        try {
            person = JsonUtil.fromJson(personStr,Person.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }


    /**
     * 触发异常，返回到前台
     */
    @RequestMapping(value = "/expt", method = RequestMethod.POST)
    @ResponseBody
    public MakeExptResDto expt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        MakeExptResDto expt = makeExptService.makeExpt();
        if(expt.getResultCode() != 1){
            throw new LasViewException(expt.getResultMsg(),expt.getErrorStack().toString());
        }
        return expt;
    }

    /**
     * 李轶 ： 前台输入url和端口和内容，如果内容是00则返回成功，否则失败，断开连接是30秒
     */
    @RequestMapping(value = "/getResponse", method = RequestMethod.POST)
    @ResponseBody
    public String getResponse(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String content = request.getParameter("contentData");
        String url = request.getParameter("targetUrl");
        String port = request.getParameter("targetPort");
        String jsonResult = null;

        Socket s = new Socket(url, Integer.parseInt(port));
        s.setSoTimeout(30 * 1000);

        OutputStream os = s.getOutputStream();
        os.write(content.getBytes());

        // 获取输入流
        InputStream is = s.getInputStream();
        byte[] bys = new byte[1024];
        int len = is.read(bys);// 阻塞
        String client = new String(bys, 0, len);

        if("00".equals(client)){
            jsonResult =  "成功";
        } else{
            jsonResult = "失败";
        }
        return new Gson().toJson(jsonResult);
    }
}
