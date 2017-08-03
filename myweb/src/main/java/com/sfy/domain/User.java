package com.sfy.domain;

public class User {

    private Role role;


    private String userNo; // 用户账号
	private String password;
	private String userName; // 用户姓名
	private Integer userType; // 用户类型
	private String memo; // 链接信息
	private String orgNo; // 所属分公司编号
	private String orgName; // 所属分公司名字
	private String distributeNo; // 所属配送中心编号
	private String distributeName; // 所属配送中心名字
	private String wareHouseNo; // 所属大货仓
	private String wareHouseName;
	private String hrOrgId; // 人资系统中的机构ID
	private Integer accountStatus; // 账号状态：是否激活
	private Integer yn;
	private Integer userFlag;

    public User(String userName, String password) {
        this.password = password;
        this.userName = userName;
    }

    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}
    public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getWareHouseNo() {
		return wareHouseNo;
	}
	public void setWareHouseNo(String wareHouseNo) {
		this.wareHouseNo = wareHouseNo;
	}
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}
	public String getHrOrgId() {
		return hrOrgId;
	}
	public void setHrOrgId(String hrOrgId) {
		this.hrOrgId = hrOrgId;
	}
	public Integer getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	public Integer getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}
}
