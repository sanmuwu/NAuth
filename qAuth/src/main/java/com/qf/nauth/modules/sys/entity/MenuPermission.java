/**
 * 
 */
package com.qf.nauth.modules.sys.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qf.nauth.common.persistence.DataEntity;
import com.qf.nauth.common.utils.StringUtils;

/**
 * @author MengpingZeng
 *
 */
public class MenuPermission extends DataEntity<MenuPermission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8454683400978653917L;
	
	private String menuName; 	// 名称
	private String menuHref; 	// 链接
	
	private List<Permission> menuPermissionList;

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the menuHref
	 */
	public String getMenuHref() {
		return menuHref;
	}

	/**
	 * @param menuHref the menuHref to set
	 */
	public void setMenuHref(String menuHref) {
		this.menuHref = menuHref;
	}

	/**
	 * @return the menuPermissionList
	 */
	public List<Permission> getMenuPermissionList() {
		return menuPermissionList;
	}

	/**
	 * @param menuPermissionList the menuPermissionList to set
	 */
	public void setMenuPermissionList(List<Permission> menuPermissionList) {
		this.menuPermissionList = menuPermissionList;
	}
	
	@JsonIgnore
	public String getId() {
		return id;
	}
	
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}

	@JsonIgnore
	public Date getUpdateDate() {
		return updateDate;
	}
	
	@JsonIgnore
	public String getRemarks() {
		return remarks;
	}
	
	@JsonIgnore
	public boolean getIsNewRecord() {
        return isNewRecord || StringUtils.isBlank(getId());
    }
}
