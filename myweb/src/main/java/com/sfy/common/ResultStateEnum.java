package com.sfy.common;

/**
 * @author Jimmy Shan
 * @date 2014-09-03
 * @desc 枚举返回状态
 */
public enum ResultStateEnum
{
    FAIL(0, "失败"),
    SUCCESS(1, "成功"),
    ING(2, "处理中"),
    NO_RECORD(3, "无记录"),
    UPDATE_FAIL(4, "更新状态失败");

    private final Integer code;
    private final String desc;

    /**
     * @param code
     * @param description
     */
    private ResultStateEnum(Integer code, String description)
    {
        this.code = code;
        this.desc = description;
    }

    /**
     * @return
     * @desc 返回code
     */
    public Integer getCode()
    {
        return code;
    }

    /**
     * @return
     * @desc 返回desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * 通过code获取枚举
     * @param code
     */
    public static ResultStateEnum getByCode(String code)
    {
        for (ResultStateEnum yesNo : values())
        {
            if (yesNo.getCode().equals(code))
            {
                return yesNo;
            }
        }
        return null;
    }

}
