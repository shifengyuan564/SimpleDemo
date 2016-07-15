package com.sfy.domain.store;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: 门店基础信息 实体类
 * Author: shifengyuan@jd.com
 * Date: 2016/2/25
 */
public class SiteBaseInfo implements Serializable {

    private static final long serialVersionUID = -7733463478285509853L;

    private SiteExtendInfo siteExtendInfo;      // 门店扩展信息实体(通过相同的siteNo与SiteBaseInfo对应)
    private Long siteNo;                        // 自增id (门店编码,主键)
    private String siteNoName;                  // 用于门店查询专用
    private String bussinessSiteNo;             // 业务门店编码
    private String orgNo;                       // 区域编码
    private String orgName;                     // 区域名称
    private String distributeNo;                // 运营中心编码
    private String distributeName;              // 运营中心名称
    private String siteName;                    // 门店名称 (唯一)
    private Integer siteStatus;                 // 门店状态
    private Integer siteLevel;                  // 门店级别
    private Integer siteType;                   // 门店类型
    private String siteManager;                 // 门店店长
    private String provinceNo;                  // 省编码
    private String provinceName;                // 省名称
    private String cityNo;                      // 城市编码
    private String cityName;                    // 城市名称
    private String countryNo;                   // 县编码
    private String countryName;                 // 县名称
    private String townNo;                      // 乡镇编码
    private String townName;                    // 乡镇名称
    private String address;                     // 详细地址
    private String jdAccount;                   // 商城账号
    private String managerErp;                  // 管家ERP
    private String managerName;                 // 管家姓名
    private double gpsLongitude;                // 地址经度
    private double gpsLatitude;                 // 地址维度
    private String telephone;                   // 联系电话
    private String email;                       // 邮箱
    private String wechat;                      // 微信号
    private String createUser;                  // 创建人
    private String updateUser;                  // 更新人
    private Date createTime;                    // 创建日期
    private Date updateTime;                    // 更新日期
    private Date startTime;                     // 启用日期
    private Date openTime;                      // 开业日期
    private Integer isDelete;
    private List<String> orgAllNums;

    private int currentPage;
    private int totalPage;
    private int pageSize;
    private int nextPage;
    private int prevPage;
    private int startNum;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date startTimeBegin;
    private Date startTimeEnd;
    private Date openTimeBegin;
    private Date openTimeEnd;


    private String erp;
    private List<Map<String, String>> orgNos;
    private List<String> distributeNos;

    public SiteExtendInfo getSiteExtendInfo() {
        return siteExtendInfo;
    }

    public void setSiteExtendInfo(SiteExtendInfo siteExtendInfo) {
        this.siteExtendInfo = siteExtendInfo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDistributeNo() {
        return distributeNo;
    }

    public void setDistributeNo(String distributeNo) {
        this.distributeNo = distributeNo;
    }

    public String getDistributeName() {
        return distributeName;
    }

    public void setDistributeName(String distributeName) {
        this.distributeName = distributeName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(Integer siteStatus) {
        this.siteStatus = siteStatus;
    }

    public Integer getSiteLevel() {
        return siteLevel;
    }

    public void setSiteLevel(Integer siteLevel) {
        this.siteLevel = siteLevel;
    }

    public Integer getSiteType() {
        return siteType;
    }

    public void setSiteType(Integer siteType) {
        this.siteType = siteType;
    }

    public String getSiteManager() {
        return siteManager;
    }

    public void setSiteManager(String siteManager) {
        this.siteManager = siteManager;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTownNo() {
        return townNo;
    }

    public void setTownNo(String townNo) {
        this.townNo = townNo;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJdAccount() {
        return jdAccount;
    }

    public void setJdAccount(String jdAccount) {
        this.jdAccount = jdAccount;
    }

    public String getManagerErp() {
        return managerErp;
    }

    public void setManagerErp(String managerErp) {
        this.managerErp = managerErp;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(Date createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Date getStartTimeBegin() {
        return startTimeBegin;
    }

    public void setStartTimeBegin(Date startTimeBegin) {
        this.startTimeBegin = startTimeBegin;
    }

    public Date getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(Date startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Long getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(Long siteNo) {
        this.siteNo = siteNo;
    }

    public String getBussinessSiteNo() {
        return bussinessSiteNo;
    }

    public void setBussinessSiteNo(String bussinessSiteNo) {
        this.bussinessSiteNo = bussinessSiteNo;
    }

    public List<String> getOrgAllNums() {
        return orgAllNums;
    }

    public void setOrgAllNums(List<String> orgAllNums) {
        this.orgAllNums = orgAllNums;
    }

    public String getSiteNoName() {
        return siteNoName;
    }

    public void setSiteNoName(String siteNoName) {
        this.siteNoName = siteNoName;
    }

    public Date getOpenTimeBegin() {
        return openTimeBegin;
    }

    public void setOpenTimeBegin(Date openTimeBegin) {
        this.openTimeBegin = openTimeBegin;
    }

    public Date getOpenTimeEnd() {
        return openTimeEnd;
    }

    public void setOpenTimeEnd(Date openTimeEnd) {
        this.openTimeEnd = openTimeEnd;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }

    public List<Map<String, String>> getOrgNos() {
        return orgNos;
    }

    public void setOrgNos(List<Map<String, String>> orgNos) {
        this.orgNos = orgNos;
    }

    public List<String> getDistributeNos() {
        return distributeNos;
    }

    public void setDistributeNos(List<String> distributeNos) {
        this.distributeNos = distributeNos;
    }
}
