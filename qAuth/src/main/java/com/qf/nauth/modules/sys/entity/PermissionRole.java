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
public class PermissionRole extends DataEntity<PermissionRole> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7233432673262931342L;
	
	private String roleCName; 							// 角色名称
	private String roleEName;							// 英文名称

	/**
	 * @return the roleCNName
	 */
	public String getRoleCName() {
		return roleCName;
	}

	/**
	 * @param roleCNName the roleCNName to set
	 */
	public void setRoleCName(String roleCName) {
		this.roleCName = roleCName;
	}

	/**
	 * @return the roleEName
	 */
	public String getRoleEName() {
		return roleEName;
	}

	/**
	 * @param roleEName the roleEName to set
	 */
	public void setRoleEName(String roleEName) {
		this.roleEName = roleEName;
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
