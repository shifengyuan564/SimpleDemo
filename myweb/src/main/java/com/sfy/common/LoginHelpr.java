package com.sfy.common;

import com.google.gson.Gson;
import com.sfy.domain.User;
//import com.jd.common.web.LoginContext;
//import com.jd.passport.utils.JdLoginUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public final class LoginHelpr {

	private static final Logger logger = Logger
			.getLogger(LoginHelpr.class);

	//获取当前用户名
	public static String getUserPin(HttpServletRequest request) {
/*		try {
			// 获取passport登录信息，如果没有用ERP登录信息，如果ERP也没有则返回字符串#error#。
			String pin;
			pin = JdLoginUtils.getPin(request);

			if (pin == null) {
				LoginContext loginContext = LoginContext.getLoginContext();
				if (loginContext != null) {
					return loginContext.getPin();
				}
			} else {
				return pin;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "#error#";*/
        return "bjlxdong";
	}

	//判断当前用户为ERP用户还是Passport用户。ERP用户返回1，Passport用户返回2.
	//返回0代表判断失败
/*	public static int getUserType(HttpServletRequest request) {
		try {
			// 获取passport登录信息，如果没有用ERP登录信息，如果ERP也没有则返回字符串#error#。
			String pin;

			LoginContext loginContext = LoginContext.getLoginContext();
			if (loginContext != null){
				pin = loginContext.getPin();
				if (!pin.isEmpty())
					return 1;
			}
			pin = JdLoginUtils.getPin(request);
			if (!pin.isEmpty())
				return 2;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}*/

	// 获取乡亲app用户权限资源码
	public static List<LinkedHashMap<String,String>> getVillagePermissionResourcesByUser(String userPin) {
		Gson gson = new Gson();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		MediaType mediaType = new MediaType("application", "json");
		supportedMediaTypes.add(mediaType);

		MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
		httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = restTemplate
				.getMessageConverters();
		list.add(httpMessageConverter);

		List<LinkedHashMap<String,String>> menuResources = restTemplate
				.getForObject("=http://las.core.jd.com/service/permission/getAllMenuResource/JDHMS_VILLAGE/" + userPin,
						List.class);
		return menuResources;
	}
	// 验证资源码
	public static boolean judgeUserResource(String userPin, String resourceCode) {
		boolean result = false;
		try {

			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
			MediaType mediaType = new MediaType("application", "json");
			supportedMediaTypes.add(mediaType);

			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
			RestTemplate restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> list = restTemplate
					.getMessageConverters();
			list.add(httpMessageConverter);

			// 判断资源是否存在
			result = restTemplate.getForObject(
					"http://las.core.jd.com/service/permission/judgeUserResource/LAS_MADS/"
							+ userPin + "/" + resourceCode, Boolean.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 获取机构信息
	public static List<String> getOrgIdsByUser(String userPin) {

		List<String> orgIds = new ArrayList<String>();

		try {

			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
			MediaType mediaType = new MediaType("application", "json");
			supportedMediaTypes.add(mediaType);

			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
			RestTemplate restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> list = restTemplate
					.getMessageConverters();
			list.add(httpMessageConverter);

			Map user = new HashMap();
			user.put("userNo", userPin);
			// 获取机构
			List<Map<String, Object>> users = restTemplate
					.postForObject(
							"http://las.core.jd.com/service/permission/getUsersByUserNo.json",
							user, List.class);

			for (Map tmpUser : users) {
				orgIds.add(tmpUser.get("orgNo").toString());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgIds;
	}

	// 获取用户权限资源码
	public static List<LinkedHashMap<String,String>> getPermissionResourcesByUser(String userPin) {
		Gson gson = new Gson();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		MediaType mediaType = new MediaType("application", "json");
		supportedMediaTypes.add(mediaType);

		MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
		httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = restTemplate
				.getMessageConverters();
		list.add(httpMessageConverter);

		List<LinkedHashMap<String,String>> menuResources = restTemplate
				.getForObject(
						"http://las.core.jd.com/service/permission/getAllResource/LAS-JDHMS/" + userPin,
						List.class);

		logger.info("getMenuResourcesByUser:" + userPin + " resources:" + gson.toJson(menuResources));

		return menuResources;
	}

	// 获取用户权限资源码
	public static List<LinkedHashMap<String,String>> getPermissionResourcesByUserResourceCode(String userPin, String resourceCode) {
		Gson gson = new Gson();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		MediaType mediaType = new MediaType("application", "json");
		supportedMediaTypes.add(mediaType);

		MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
		httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = restTemplate
				.getMessageConverters();
		list.add(httpMessageConverter);

		List<LinkedHashMap<String,String>> menuResources = restTemplate
				.getForObject(
						"http://las.core.jd.com/service/permission/getResourceLevel/LAS-JDHMS/" + userPin+"/"+resourceCode,
						List.class);

		logger.info("getMenuResourcesByUser:" + userPin + " resources:" + gson.toJson(menuResources));

		return menuResources;
	}



	// 获取erp用户详细信息（包括机构，配送中心等）
	public static List<Map<String, Object>> getUserInfo(String userPin) {

		List<String> orgIds = new ArrayList<String>();

		try {

			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
			MediaType mediaType = new MediaType("application", "json");
			supportedMediaTypes.add(mediaType);

			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
			RestTemplate restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> list = restTemplate
					.getMessageConverters();
			list.add(httpMessageConverter);

			Map user = new HashMap();
			user.put("userNo", userPin);
			// 获取用户详细信息
			List<Map<String, Object>> users = restTemplate
					.postForObject(
							"http://las.core.jd.com/service/permission/getUsersByUserNo.json",
							user, List.class);
			return users;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// 获取外部用户详细信息（包括机构，配送中心等）
	public static List<Map<String, Object>> getOutUsers(String userPin) {

		List<String> orgIds = new ArrayList<String>();

		try {

			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
			MediaType mediaType = new MediaType("application", "json");
			supportedMediaTypes.add(mediaType);

			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
			RestTemplate restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> list = restTemplate
					.getMessageConverters();
			list.add(httpMessageConverter);

			Map user = new HashMap();
			user.put("userNo", userPin);
			// 获取用户详细信息
			List<Map<String, Object>> users = restTemplate
					.postForObject(
							"http://las.core.jd.com/service/permission/getOutUsers.json",
							user, List.class);
			return users;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	// 获取承运商信息
	/*
	 * public static List<Long> getAssignedCarriersByUser(String userPin) {
	 * List<Long> carrierSysNos = new ArrayList<Long>();
	 *
	 * try { //ErpUser erpUser = this.getCurrentUserFromLoginContext(); //
	 * 通过erp用户账号查询分配的承运商Sysno（数据库）
	 *
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 *
	 * return carrierSysNos; }
	 */

	// 落地用户详细信息到调度数据库（待定）
	public static void saveLoginUserInfoToDB(String userPin) {
		try {
			// ErpUser erpUser = this.getCurrentUserFromLoginContext();
			// 查询数据库中是否有erpUser.getUserCode，没有则新增，有则更新

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	// 获取用户详细信息（包括机构，配送中心等）
	public static List<Map<String,Object>> getUsers(User user) {

		List<String> orgIds = new ArrayList<String>();
		try {

			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
			MediaType mediaType = new MediaType("application", "json");
			supportedMediaTypes.add(mediaType);

			MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
			httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
			RestTemplate restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> list = restTemplate
					.getMessageConverters();
			list.add(httpMessageConverter);

			// 获取用户详细信息
			List<Map<String,Object>> users = restTemplate
					.postForObject(
							"http://las.core.jd.com/service/permission/getOutUsers.json",
							user, List.class);
			return users;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 根据资源编码获取其所有下N级资源对象
	 * 动态功能菜单使用
	 * @Auth WXJ
	 * @At 2016-3-8
	 * @param userPin
	 * @param resourceCode
	 * @return
	 */
	public static List<LinkedHashMap<String,String>> getResourceByParent(String userPin, String resourceCode) {
		Gson gson = new Gson();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		MediaType mediaType = new MediaType("application", "json");
		supportedMediaTypes.add(mediaType);

		MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
		httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = restTemplate
				.getMessageConverters();
		list.add(httpMessageConverter);
		List menuResources = (List)restTemplate.getForObject("http://las.core.jd.com/service/permission/getResourceLevel/LAS-JDHMS/" + userPin + "/" + resourceCode, List.class, new Object[0]);
		return menuResources;

	}

	/**
	 * 获取用户权限资源码 ，新版JSF权限接口
	 */
	public static List<LinkedHashMap<String,String>> getPermissionResourcesByUserJSF(String userPin) {
		Gson gson = new Gson();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		MediaType mediaType = new MediaType("application", "json");
		supportedMediaTypes.add(mediaType);

		MappingJacksonHttpMessageConverter httpMessageConverter = new MappingJacksonHttpMessageConverter();
		httpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = restTemplate
				.getMessageConverters();
		list.add(httpMessageConverter);

		List<LinkedHashMap<String,String>> menuResources = restTemplate
				.getForObject(
						"http://las.core.jd.com/service/permission/getAllResource/LAS-JDHMS/" + userPin,
						List.class);

		logger.info("getMenuResourcesByUser:" + userPin + " resources:" + gson.toJson(menuResources));

		return menuResources;
	}


}
