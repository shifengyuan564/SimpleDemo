package com.sfy.dao;

import com.sfy.dao.common.MySessionDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaCascadeDao extends MySessionDaoSupport {

    private static final String nameSpace = "com.jd.las.jdams.dao.AreaCascadeMapper";

    public List<Map<String, String>> getArea(String parm) {
        return getSqlSession().selectList(nameSpace + ".getArea", parm);
    }

    public List<Map<String, String>> getOperateCenter(Map<String, String> parmMap) {
        return getSqlSession().selectList(nameSpace + ".getOperateCenter", parmMap);
    }

}
