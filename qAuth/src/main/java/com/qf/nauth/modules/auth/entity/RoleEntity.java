package com.qf.nauth.modules.auth.entity;

import java.io.Serializable;

/**
 * 接口返回角色信息实体类
 * 
 * @author SenWu
 *
 */
public class RoleEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3752400019304771616L;

	/**
	 * 角色编号
	 */
	private String roleID;

	/**
	 * 角色姓名
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleDesc;

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
