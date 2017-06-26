package com.sfy.domain;

import java.io.Serializable;
import java.util.Date;


public class Code implements Serializable {

    private static final long serialVersionUID = 7029367817305372392L;

    private int id;                 // 主键id
    private String code;            // 编码
    private String name;            // 名称
    private String instruction;     // 说明 (简介、全称)
    private String businessCode;    // 业务代码
    private String businessName;    // 业务名称
    private String companyCode;     // 公司代码
    private String companyName;     // 公司名称
    private Integer internal;       // 1代表国内，0代表国外
    private Date updateTime;        // 更新时间
    private Integer isDelete;       // 逻辑删除

    private Integer isStringCode;   // 标记查询的code是否为字符串

    /**
     * 查询关键字
     */
    private String queryKey;

    /**
     * 偏移量
     */
    private Integer offset;
    /**
     * 页
     */
    private Integer pageSize;
    /**
     * 记录总数
     */
    private Integer total;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }

    public Integer getInternal() {
        return internal;
    }

    public void setInternal(Integer internal) {
        this.internal = internal;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Integer getIsStringCode() {
        return isStringCode;
    }

    public void setIsStringCode(Integer isStringCode) {
        this.isStringCode = isStringCode;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
