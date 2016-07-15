package com.sfy.common;

/**
 * Description: 异常枚举类
 * Author: shifengyuan@jd.com
 * Date: 2016/1/22
 */
public enum MeExceptionCode {

    //系统异常 runtime exception
    UNKNOW_EXCEPTON("9999", "系统异常，请联系管理员查看错误信息。"),
    FUNCTION_IS_NOT_COMPLETE("9998", "该功能尚未开放，尽情等待。"),

    //数据库操作异常
    DB_OPERATOR_EXPT("0001", "数据库操作发生异常，请联系管理员查看错误信息。"),
    LEGAL_ACCESS_EXPT("0002", "非法访问异常，请检查对象类型是否合法。"),
    INVACTION_TARGET_EXPT("0003", "调用目标异常"),
    BD_UPDATE_EXPT("0004", "更新数据出错");

    private String code;
    private String desc;

    MeExceptionCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static String getDescByCode(String code) {
        if (code != null && !"".equals(code.trim())) {
            for (MeExceptionCode mc : values()) {
                if (mc.getCode().equals(code)) {
                    return mc.getDesc();
                }
            }
        }
        return null;
    }
}
