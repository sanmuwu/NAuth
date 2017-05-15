package com.qf.nauth.modules.auth.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 接口返回的菜单列表实体类
 * 
 * @author SenWu
 *
 */
public class MenuListEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 33423941903054862L;

	/**
	 * 系统ID
	 */
	private String systemId;

	/**
	 * 与系统对应的菜单列表
	 */
	private List<MenuEntity> menuList;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public List<MenuEntity> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuEntity> menuList) {
		this.menuList = menuList;
	}

}
