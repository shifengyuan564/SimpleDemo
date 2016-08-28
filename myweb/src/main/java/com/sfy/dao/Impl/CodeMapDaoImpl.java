package com.sfy.dao.Impl;

import com.sfy.dao.CodeMapDao;
import com.sfy.dao.common.MySessionDaoSupport;
import com.sfy.domain.Code;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("codeMapDao")
public class CodeMapDaoImpl extends MySessionDaoSupport implements CodeMapDao {

    private static final String nameSpace = "com.me.CodeMapper";


    @Override
    public List<Code> queryCode(Code code) {
        return this.getSqlSession().selectList(nameSpace + ".queryCode", code);
    }
}
