package com.sfy.controller;

import com.google.gson.Gson;
import com.sfy.dao.CodeMapDao;
import com.sfy.domain.Code;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sfy on 2016/8/16.
 */

@Controller
@RequestMapping("/codemap/")
public class CodeMapController {

    private final static Log logger = LogFactory.getLog(CodeMapController.class);

    @Autowired
    private CodeMapDao codeMapDao;

    @RequestMapping(value = "/{company}/{business}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Code> queryDispatcher(@PathVariable String company,
                                      @PathVariable String business,
                                      String queryKey) {

        Code queryCode = new Code();
        queryCode.setCompanyCode(company);
        queryCode.setBusinessCode(business);
        queryCode.setQueryKey(queryKey);

        return codeMapDao.queryCode(queryCode);
    }
}
