/**
 * 
 */
package com.qf.nauth.modules.sys.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qf.nauth.common.persistence.DataEntity;
import com.qf.nauth.common.utils.StringUtils;

/**
 * @author MengpingZeng
 *
 */
public class Permission extends DataEntity<Permission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8454683400978653917L;
	
	private String cName; 			// 名称
	private String permission; 		// 权限标识
	
	/**
	 * @return the cName
	 */
	public String getcName() {
		return cName;
	}
	/**
	 * @param cName the cName to set
	 */
	public void setcName(String cName) {
		this.cName = cName;
	}
	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
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
