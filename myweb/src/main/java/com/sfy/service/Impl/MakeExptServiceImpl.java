package com.sfy.service.Impl;

import com.sfy.common.MeUnCheckedException;
import com.sfy.common.ResultStateEnum;
import com.sfy.dao.MakeExptDao;
import com.sfy.domain.MakeExptResDto;
import com.sfy.service.MakeExptService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Description: 造异常
 * Author: shifengyuan@jd.com
 * Date: 2016/1/22
 */
@Service
public class MakeExptServiceImpl implements MakeExptService {

    private Logger logger = Logger.getLogger(MakeExptServiceImpl.class);

    @Autowired
    private MakeExptDao makeExptDao;

    public MakeExptResDto makeExpt() {

        MakeExptResDto resDto = new MakeExptResDto();
        try {
            makeExptDao.makeExpt();
        } catch (MeUnCheckedException e) {
            resDto.setResultCode(ResultStateEnum.FAIL.getCode());
            resDto.setResultMsg("[" + e.getErrCode() + "]" + e.getErrMsg());
            resDto.setErrorStack(e.getCause());
        }
        return resDto;
    }
}
