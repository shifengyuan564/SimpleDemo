package com.sfy.dao.Impl;

import com.sfy.common.MeExceptionCode;
import com.sfy.common.MeUnCheckedException;
import com.sfy.dao.MakeExptDao;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;

/**
 * Description:
 * Author: shifengyuan@jd.com
 * Date: 2016/1/22
 */
@Repository
public class MakeExptDaoImpl implements MakeExptDao {

    private final static org.apache.log4j.Logger logger = LogManager.getLogger(MakeExptDaoImpl.class);

    public void makeExpt() {

        logger.info("-------------MakeExptDaoImpl.makeExpt---------------");
        try {
            FileInputStream fis = new FileInputStream("G:\\aaa.xls");   // checked exception (FileNotFoundException )默认不受事务管理（可以配置rollbackFor更改）
            // int a = Integer.parseInt("123sss");                      // unchecked exception (runtime exception) 默认受事务管理
        } catch (Exception e){
            throw new MeUnCheckedException(MeExceptionCode.UNKNOW_EXCEPTON,e);          // 用try/catch的话，必须throw和throws才能触发事务回滚
        }
        logger.info("测试是否catch后，还能继续执行下面语句");           // 当上面throw出runtime异常后，不再执行下面了
    }
}
