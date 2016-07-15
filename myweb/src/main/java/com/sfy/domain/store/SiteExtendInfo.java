package com.sfy.domain.store;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:门店扩展信息 实体类
 * Author: shifengyuan@jd.com
 * Date: 2016/2/25
 */
public class SiteExtendInfo implements Serializable {

    private static final long serialVersionUID = -5721295041486318718L;

    private Long id;                         // 自增id (主键)
    private Long siteNo;                      // 门店编码 (唯一)
    private String companyName;                 // 公司名称
    private Integer companyType;                // 企业性质
    private String bussinessLicenseNo;          // 营业执照编号
    private String bussinessLicenseAddress;     // 营业执照所在地
    private Date bussinessLicenseTermValidity;  // 营业执照有效期
    private Integer taxType;                    // 供应商纳税类型
    private String taxRate;                     // 纳税税率
    private String taxRegistrationNo;           // 税务登记号
    private String bankDepositName;             // 开户行名称
    private String bankDepositAddress;          // 开户行地址
    private String bankAccount;                 // 银行账号
    private String bankAccountName;             // 开户名称
    private String jssAttachement;              // 上传附件地址
    private String createUser;                  // 创建人
    private String updateUser;                  // 更新人
    private Date createTime;                    // 创建日期
    private Date updateTime;                    // 更新日期
    private String remark;                      // 备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(Long siteNo) {
        this.siteNo = siteNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getBussinessLicenseNo() {
        return bussinessLicenseNo;
    }

    public void setBussinessLicenseNo(String bussinessLicenseNo) {
        this.bussinessLicenseNo = bussinessLicenseNo;
    }

    public String getBussinessLicenseAddress() {
        return bussinessLicenseAddress;
    }

    public void setBussinessLicenseAddress(String bussinessLicenseAddress) {
        this.bussinessLicenseAddress = bussinessLicenseAddress;
    }

    public Date getBussinessLicenseTermValidity() {
        return bussinessLicenseTermValidity;
    }

    public void setBussinessLicenseTermValidity(Date bussinessLicenseTermValidity) {
        this.bussinessLicenseTermValidity = bussinessLicenseTermValidity;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxRegistrationNo() {
        return taxRegistrationNo;
    }

    public void setTaxRegistrationNo(String taxRegistrationNo) {
        this.taxRegistrationNo = taxRegistrationNo;
    }

    public String getBankDepositName() {
        return bankDepositName;
    }

    public void setBankDepositName(String bankDepositName) {
        this.bankDepositName = bankDepositName;
    }

    public String getBankDepositAddress() {
        return bankDepositAddress;
    }

    public void setBankDepositAddress(String bankDepositAddress) {
        this.bankDepositAddress = bankDepositAddress;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getJssAttachement() {
        return jssAttachement;
    }

    public void setJssAttachement(String jssAttachement) {
        this.jssAttachement = jssAttachement;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
