package com.sfy.dao;

import com.sfy.dao.common.MySessionDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserDao extends MySessionDaoSupport {

    private static final String nameSpace = "com.jd.las.jdams.dao.UserMapper";

    public List<Map<String, String>> getUserList(Map<String, String> parm) {
        return getSqlSession().selectList(nameSpace + ".getUserList", parm);
    }

    public List<Map<String, String>> getUser(Map<String, Object> para) {
        return getSqlSession().selectList(nameSpace + ".getUser", para);
    }

    public List<Map<String, String>> getUserOperationUser(Map<String, Object> para) {
        return getSqlSession().selectList(nameSpace + ".getUserOperationUser", para);
    }

    public List<Map<String, String>> getUserOnlyExist(Map<String, Object> para) {
        return getSqlSession().selectList(nameSpace + ".getUserOnlyExist", para);
    }

    public List<Map<String, String>> userSecurityInterceptor(Map<String, String> parm) {
        return getSqlSession().selectList(nameSpace + ".userSecurityInterceptor", parm);
    }

    public List<String> queryUsers(Map<String, Object> parameterMap) {
        return getSqlSession().selectList(nameSpace + ".queryUsers", parameterMap);
    }
}
