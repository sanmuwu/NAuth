package com.qf.nauth.modules.auth.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 接口返回角色列表相关数据实体类
 * 
 * @author SenWu
 *
 */
public class RoleListEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4982925684012658946L;
	/**
	 * 系统编号
	 */
	private String systemId;
	/**
	 * 与系统对应的角色列表
	 */
	private List<RoleEntity> roleList;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public List<RoleEntity> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleEntity> roleList) {
		this.roleList = roleList;
	}

}
