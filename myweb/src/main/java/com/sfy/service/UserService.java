package com.sfy.service;

import com.sfy.dao.UserDao;
import com.sfy.domain.AreaCascadeInfo ;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

	private static final Logger logger = Logger.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;

	/**
	 * 根据erp获取用户信息
	 * @param userPin
	 * @return
     */
	public List<Map<String, String>> getUserInfo(String userPin) {
		
		logger.info("UserService getUserInfo begin userPin:"+userPin);
		
		Map<String,String> parm = new HashMap<String,String>();
		parm.put("boundErp", userPin);
		
		List<Map<String, String>> map=userDao.getUserList(parm);
		
		logger.info("UserService getUserInfo end userPin:"+userPin);
		
		return map;
	}

	/**
	 * 根据京东账号获取用户信息
	 * @param userAccount
	 * @return
     */
	public List<Map<String, String>> getUserInfoByJDAccount(String userAccount) {

		logger.info("UserService getUserInfoByJDAccount begin userPin:"+userAccount);

		Map<String,String> parm = new HashMap<String,String>();
		parm.put("extranetNo", userAccount);

		List<Map<String, String>> map=userDao.getUserList(parm);

		logger.info("UserService getUserInfoByJDAccount end userPin:"+userAccount);

		return map;
	}

	/**
	 * 根据ERP获取用户信息
	 * @param userAccount
	 * @return
	 */
	public List<Map<String, String>> getUserInfoByERP(String userAccount) {

		logger.info("UserService getUserInfoByJDAccount begin userPin:"+userAccount);

		Map<String,String> parm = new HashMap<String,String>();
		parm.put("boundErp", userAccount);

		List<Map<String, String>> map=userDao.getUserList(parm);

		logger.info("UserService getUserInfoByJDAccount end userPin:"+userAccount);

		return map;
	}
	
	//根据机构、配送中心获取用户列表
	public List<Map<String,String>> getUser(AreaCascadeInfo areaCascadeInfo){
		//String areaNo,String operateCenterNo,String roleId
		logger.info("UserService getUser begin areaNo:"+areaCascadeInfo.getArea() +" operateCenterNo:"+areaCascadeInfo.getOperateCenter()
				+" getRoleIds:"+areaCascadeInfo.getRoleIds()+" getRoleName:"+areaCascadeInfo.getRoleNames());
		List<Map<String, String>> users =null;
		try {
//			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//			MediaType mediaType = new MediaType("application", "json");
//			supportedMediaTypes.add(mediaType);
//			
//			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
//			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//			RestTemplate restTemplate = new RestTemplate();
//			List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
//			list.add(httpMessageConverter);

			Map<String,Object> para = new HashMap<String,Object>();
			para.put("orgNo", areaCascadeInfo.getArea());
			para.put("distributeNo", areaCascadeInfo.getOperateCenter());
			para.put("roleIds", areaCascadeInfo.getRoleIds());
			para.put("roleNames", areaCascadeInfo.getRoleNames());
			// 获取用户详细信息
			//users = restTemplate.postForObject("http://las.core.jd.com/service/permission/getUsers.json",para, List.class);
			users=userDao.getUser(para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("UserService getUser end areaNo:"+areaCascadeInfo.getArea() +" operateCenterNo:"+areaCascadeInfo.getOperateCenter()+" getRoleIds:"+areaCascadeInfo.getRoleIds());
		return users;
	}
	//根据机构、配送中心获取用户列表
	public List<Map<String,String>> getUserOperationUser(AreaCascadeInfo areaCascadeInfo){
		//String areaNo,String operateCenterNo,String roleId
		logger.info("UserService getUser begin :"+" getRoleName:"+areaCascadeInfo.getRoleNames());
		List<Map<String, String>> users =null;
		try {
			Map<String,Object> para = new HashMap<String,Object>();
			para.put("roleNames", areaCascadeInfo.getRoleNames());
			// 获取用户详细信息
			//users = restTemplate.postForObject("http://las.core.jd.com/service/permission/getUsers.json",para, List.class);
			users=userDao.getUserOperationUser(para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("UserService getUser end areaNo:"+areaCascadeInfo.getArea() +" operateCenterNo:"+areaCascadeInfo.getOperateCenter()+" getRoleIds:"+areaCascadeInfo.getRoleIds());
		return users;
	}
	// 获取erp用户详细信息,包括机构，配送中心等
//	public List<Map<String, String>> getUserInfo(String userPin) {
//		try {
//			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//			MediaType mediaType = new MediaType("application", "json");
//			supportedMediaTypes.add(mediaType);
//
//			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
//			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//			RestTemplate restTemplate = new RestTemplate();
//			List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
//			list.add(httpMessageConverter);
//
//			Map user = new HashMap();
//			user.put("userNo", userPin);
//			// 获取用户详细信息
//			List<Map<String, String>> users = restTemplate.postForObject("http://las.core.jd.com/service/permission/getUsersByUserNo.json",user, List.class);
//			return users;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	//根据机构、配送中心获取用户列表
	public List<Map<String,String>> getUserOnlyExist(AreaCascadeInfo areaCascadeInfo){
		//String areaNo,String operateCenterNo,String roleId
		logger.info("UserService getUserOnlyExist begin areaNo:"+areaCascadeInfo.getArea() +" operateCenterNo:"+areaCascadeInfo.getOperateCenter()
				+" getRoleIds:"+areaCascadeInfo.getRoleIds()+" getRoleName:"+areaCascadeInfo.getRoleNames());
		List<Map<String, String>> users =null;
		try {
//			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//			MediaType mediaType = new MediaType("application", "json");
//			supportedMediaTypes.add(mediaType);
//			
//			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
//			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//			RestTemplate restTemplate = new RestTemplate();
//			List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
//			list.add(httpMessageConverter);

			Map<String,Object> para = new HashMap<String,Object>();
			para.put("orgNo", areaCascadeInfo.getArea());
			para.put("distributeNo", areaCascadeInfo.getOperateCenter());
			para.put("roleIds", areaCascadeInfo.getRoleIds());
			para.put("roleNames", areaCascadeInfo.getRoleNames());
			// 获取用户详细信息
			//users = restTemplate.postForObject("http://las.core.jd.com/service/permission/getUsers.json",para, List.class);
			users=userDao.getUserOnlyExist(para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("UserService getUserOnlyExist end areaNo:"+areaCascadeInfo.getArea() +" operateCenterNo:"+areaCascadeInfo.getOperateCenter()+" getRoleIds:"+areaCascadeInfo.getRoleIds());
		return users;
	}
	public List<Map<String, String>> userSecurityInterceptor(Map<String,String> parm) {
		return userDao.userSecurityInterceptor(parm);
	}
}
