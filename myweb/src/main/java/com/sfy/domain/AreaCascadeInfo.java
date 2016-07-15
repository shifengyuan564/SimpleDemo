package com.sfy.domain;

import java.util.List;

public class AreaCascadeInfo {
	private String area;
	private String operateCenter;
	private List<String> roleIds;
	private List<String> roleNames;
	public List<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getOperateCenter() {
		return operateCenter;
	}
	public void setOperateCenter(String operateCenter) {
		this.operateCenter = operateCenter;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	
}
