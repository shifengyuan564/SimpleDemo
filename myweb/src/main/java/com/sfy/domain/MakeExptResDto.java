package com.sfy.domain;

import java.io.Serializable;

/**
 * Description: 返回状态DTO
 * Author: shifengyuan@jd.com
 * Date: 2016/1/22
 */
public class MakeExptResDto implements Serializable {

    private Integer resultCode = 1;         // 返回状态 0失败 1成功 2处理中
    private String resultMsg = "Success";   // 错误描述
    private Throwable errorStack;              // 错误信息栈

    public MakeExptResDto(){}

    public MakeExptResDto(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Throwable getErrorStack() {
        return errorStack;
    }

    public void setErrorStack(Throwable errorStack) {
        this.errorStack = errorStack;
    }
}
