package com.sfy.domain.store;

import java.util.Date;

/**
 * Description: 承运、安维发送给京东帮同步信息对应的实体类
 * Author: shifengyuan@jd.com
 * Date: 2016/2/29
 */
public class SyncSiteInfoMq {

    private String websiteNo;                   // 网点编码 (站点)
    private String websiteName;                 // 网点名称
    private String belongsBranchId;             // 分公司编码 (区域)
    private String belongsBranchName;           // 分公司名称
    private String belongLargewarehouseId;      // 配送中心编码 (运营中心)
    private String belongLargewarehouseName;    // 配送中心名称
    private String serviceWebsiteNo;            // 门店编号 (京东帮)
    private Date establishTime;                 // 创建时间
    private Integer operationType;              // 操作类型（1：新建，2：修改，3：删除）
    private Integer systemSource;               // 系统来源（0：承运，1：安维）


    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }
    public String getBelongsBranchId() {
        return belongsBranchId;
    }

    public void setBelongsBranchId(String belongsBranchId) {
        this.belongsBranchId = belongsBranchId;
    }

    public String getBelongsBranchName() {
        return belongsBranchName;
    }

    public void setBelongsBranchName(String belongsBranchName) {
        this.belongsBranchName = belongsBranchName;
    }

    public String getBelongLargewarehouseId() {
        return belongLargewarehouseId;
    }

    public void setBelongLargewarehouseId(String belongLargewarehouseId) {
        this.belongLargewarehouseId = belongLargewarehouseId;
    }

    public String getBelongLargewarehouseName() {
        return belongLargewarehouseName;
    }

    public void setBelongLargewarehouseName(String belongLargewarehouseName) {
        this.belongLargewarehouseName = belongLargewarehouseName;
    }

    public String getWebsiteNo() {
        return websiteNo;
    }

    public void setWebsiteNo(String websiteNo) {
        this.websiteNo = websiteNo;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getServiceWebsiteNo() {
        return serviceWebsiteNo;
    }

    public void setServiceWebsiteNo(String serviceWebsiteNo) {
        this.serviceWebsiteNo = serviceWebsiteNo;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getSystemSource() {
        return systemSource;
    }

    public void setSystemSource(Integer systemSource) {
        this.systemSource = systemSource;
    }
}
