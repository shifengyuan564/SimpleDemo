package com.sfy.service;

import com.sfy.dao.AreaCascadeDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Service
public class AreaCascadeService {

    private static final Logger logger = Logger.getLogger(AreaCascadeService.class);

    @Autowired
    private AreaCascadeDao areaCascadeDao;

    @Autowired
    private UserService userService;


    // 获取用户信息
    public List<Map<String,String>> getUserInfo(String pin){

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<Map<String, String>> temp1 = userService.getUserInfoByJDAccount(pin);
        //增加erp和jdAccount 同时支持，首先判断京东账号
        if (temp1 == null || temp1.size() == 0) {
            //如果没有，则判断erp账号
            List<Map<String, String>> temp2 = userService.getUserInfoByERP(pin);
            result = temp2;
        } else {
            result = temp1;
        }
        return result;
    }

    /**
     * 获取用户的机构
     * 根据用户在权限系统中的区域
     */
    public List<Map<String, String>> getUserArea(String pin) {

        logger.info("AreaCascadeService getUserArea begin " + pin);

        //修改根据京东账号获取用户信息 by luyanbin 2015-11-24
        List<Map<String, String>> temp = getUserInfo(pin);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        if (temp != null && temp.size() > 0) {
            for (Iterator iterator = temp.iterator(); iterator.hasNext(); ) {
                Map<String, String> tempMap = (Map<String, String>) iterator.next();
                if (tempMap.get("org_no").equals("#")) {//#代表最大权限,获取全部区域
                    return areaCascadeDao.getArea(null);
                }
                result.add(tempMap);
            }
        }
        logger.info("AreaCascadeService getUserArea end " + pin + " result:" + result.size());
        return result;
    }

    /**
     * 根据用户机构获取用户的配送中心
     */
    @ResponseBody
    public List<Map<String, String>> getOperateCenter(String pin, String areaNo) {

        logger.info("AreaCascadeService getOperateCenter begin firstPin:" + pin + " areaNo:" + areaNo);

        //修改接口入参为京东账号
        List<Map<String, String>> temp = getUserInfo(pin);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        if (temp != null && temp.size() > 0) {
            for (Map<String, String> tempMap : temp) {

                String orgNo = tempMap.get("org_no");                  // 机构
                String operateCenterNo = tempMap.get("distribute_no"); // 运营中心ID

                if (orgNo.equals(areaNo) || orgNo.equals("#")) {
                    if (operateCenterNo.equals("#")) {
                        Map<String, String> parmMap = new HashMap<String, String>();
                        parmMap.put("areaNo", areaNo);
                        return areaCascadeDao.getOperateCenter(parmMap);
                    } else {
                        result.add(tempMap);
                    }
                }
            }
        }
        logger.info("AreaCascadeService getOperateCenter end firstPin:" + pin + " areaNo:" + areaNo + " result:" + result.size());
        return result;
    }

    /**
     * 判断用户是否有全国的区域权限(#)
     */
    public boolean hasAllAreaPrivilege(String pin){

        List<Map<String, String>> temp = getUserInfo(pin);

        return "#".equals(temp.get(0).get("org_no"));
    }


    /**
     * 判断用户在某个区域下是否拥有全部的运营中心(#)
     */
    public boolean hasAllOperateCenterPrivilege(String pin, String areaNo){

        List<Map<String, String>> temp = getUserInfo(pin);

        if (temp != null && temp.size() > 0) {
            for (Map<String, String> tempMap : temp) {

                String orgNo = tempMap.get("org_no");                  // 机构
                String operateCenterNo = tempMap.get("distribute_no"); // 运营中心ID

                if (orgNo.equals(areaNo) || orgNo.equals("#")) {
                    if (operateCenterNo.equals("#")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取所有的区域
     */
    public List<Map<String, String>> getAllArea(){
        return areaCascadeDao.getArea(null);
    }

    /**
     * 获取某区域下的所有的运营中心
     */
    public List<Map<String, String>> getAllOperateCenter(String areaNo){

        Map<String, String> parmMap = new HashMap<String, String>();
        parmMap.put("areaNo", areaNo);
        return areaCascadeDao.getOperateCenter(parmMap);
    }

    /**
     * 获取用户的机构 (不重复)
     */
    public List<Map<String, String>> getUserUniqueArea(String pin) {

        logger.info("AreaCascadeService getUserUniqueArea begin " + pin);

        List<Map<String, String>> temp = getUserInfo(pin);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        List<String> tempList = new ArrayList<String>();
        if (temp != null && temp.size() > 0) {
            for (Map<String, String> tempMap : temp) {
                if (tempMap.get("org_no").equals("#")) {//#代表最大权限,获取全部区域
                    return areaCascadeDao.getArea(null);
                }
                if(!tempList.contains(tempMap.get("org_no"))){
                    tempList.add(tempMap.get("org_no"));
                    result.add(tempMap);
                }
            }
        }
        logger.info("AreaCascadeService getUserUniqueArea end " + pin + " result:" + result.size());
        return result;
    }

}
