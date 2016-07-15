package com.sfy.controller;

//import com.jd.erp.info.common.LoginHelpr;
import com.sfy.common.LoginHelpr;
import com.sfy.domain.AreaCascadeInfo ;
import com.sfy.service.AreaCascadeService ;
import com.sfy.service.UserService ;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value={"/erp/common/areaCascadeController", "/passport/common/areaCascadeController"})
public class AreaCascadeController {
	private static final Logger logger = Logger.getLogger(AreaCascadeController.class);
	
	@Autowired
	private AreaCascadeService areaCascadeService;
	
	@Autowired
	private UserService userService;


	@RequestMapping("/getUserArea")
	@ResponseBody
	public List<Map<String,String>> getUserArea(HttpServletRequest request, HttpServletResponse response) {
		String pin = LoginHelpr.getUserPin(request);
		return areaCascadeService.getUserArea(pin);
	}

    @RequestMapping("/getUserUniqueArea")
    @ResponseBody
    public List<Map<String, String>> getUserUniqueArea(HttpServletRequest request) {
        String pin = LoginHelpr.getUserPin(request);
        return areaCascadeService.getUserUniqueArea(pin);
    }

    @RequestMapping("/getOperateCenter")
	@ResponseBody
	public List<Map<String,String>> getOperateCenter(HttpServletRequest request, HttpServletResponse response) {
		String pin = LoginHelpr.getUserPin(request);
		return areaCascadeService.getOperateCenter(pin,request.getParameter("areaNo"));
	}
	@RequestMapping("/getUser")
	@ResponseBody
	public List<Map<String,String>> getUser(AreaCascadeInfo areaCascadeInfo) {
		return userService.getUserOnlyExist(areaCascadeInfo);
	}
	@RequestMapping(value = "/getUserName")
	public void getUserName(HttpServletRequest request,HttpServletResponse response) {
		
		String loginPin=LoginHelpr.getUserPin(request);
		logger.info("AreaCascadeController getUserName  loginPin "+loginPin);
		List<Map<String, String>> list=userService.getUserInfo(loginPin);
		String userName="";
		if(list!=null && list.size()>0){
			Map<String, String> rowMap=list.get(0);
			userName=rowMap.get("userName");
		}
		logger.info("AreaCascadeController getUserName  name "+userName);
		
		try {
			response.getWriter().print(userName);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @RequestMapping("/hasAllAreaPrivilege")
    @ResponseBody
    public boolean hasAllAreaPrivilege(HttpServletRequest request) {
        String pin = LoginHelpr.getUserPin(request);
        return areaCascadeService.hasAllAreaPrivilege(pin);
    }
    @RequestMapping("/hasAllOperateCenterPrivilege")
    @ResponseBody
    public boolean hasAllOperateCenterPrivilege(HttpServletRequest request) {
        String pin = LoginHelpr.getUserPin(request);
        return areaCascadeService.hasAllOperateCenterPrivilege(pin,request.getParameter("areaNo"));
    }

    @RequestMapping("/getAllArea")
    @ResponseBody
    public List<Map<String,String>> getAllArea(){
        return areaCascadeService.getAllArea();
    }

    @RequestMapping("/getAllOperateCenter")
    @ResponseBody
    public List<Map<String, String>> getAllOperateCenter(HttpServletRequest request){
        return areaCascadeService.getAllOperateCenter(request.getParameter("areaNo"));
    }
}
