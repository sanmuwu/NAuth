/**
 * 
 */
package com.qf.qauth.modules.sys.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.qf.qauth.common.service.BaseService;

/**
 * 授权管理service.
 * 
 * @author MengpingZeng
 *
 */
@Service
public class AuthorizationService extends BaseService {
	
	private static final String PERMISSION_NAMES_DELIMETER = ",";

	/**
	 * 检验用户已经通过认证.
	 * @return
	 */
	public boolean isAuthenticated() {
		boolean isAuthenticated = false;
		
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
			isAuthenticated = true;
		}
		return isAuthenticated;
	}
	
	/**
	 * 检验用户未通过认证.
	 * @return
	 */
	public boolean isNotAuthenticated() {
		boolean isNotAuthenticated = false;
		
		Subject subject = SecurityUtils.getSubject();
		if (subject == null || !subject.isAuthenticated()) {
			isNotAuthenticated = true;
		}
		return isNotAuthenticated;
	}
	
	/**
	 * 检验用户是否具有认证身份标识.
	 * @return
	 */
	public boolean isUser() {
		boolean isUSer = false;
		
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.getPrincipal() != null) {
			isUSer = true;
		}
		return isUSer;
	}
	/**
	 * 检验用户是否没有认证身份标识.
	 * @return
	 */
	public boolean isGuest() {
		boolean isGuest = false;
		
		Subject subject = SecurityUtils.getSubject();
		if (subject == null || subject.getPrincipal() == null) {
			isGuest = true;
		}
		return isGuest;
	}

	/**
	 * 检验用户是否具有标识的权限.
	 * 
	 * @param permissionName
	 * @return
	 */
	public boolean hasPermission(String permissionName) {
		boolean hasPermission = false;

		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			// check to see if the user has the permissionName
			if (subject.isPermitted(permissionName.trim())) {
				hasPermission = true;
			}
		}
		return hasPermission;
	}
	
	/**
	 * 检验用户是否具有任一标识的权限.
	 * 
	 * @param permissionNames
	 * @return
	 */
	public boolean hasAnyPermissions(String permissionNames) {
		boolean hasAnyPermission = false;

		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			// Iterate through permissions and check to see if the user has one of the permissionNames
			for (String permissionName : permissionNames.split(PERMISSION_NAMES_DELIMETER)) {
				if (subject.isPermitted(permissionName.trim())) {
					hasAnyPermission = true;
					break;
				}
			}
		}
		return hasAnyPermission;
	}
	
	/**
	 * 检验用户是否不具有标识的权限.
	 * 
	 * @param permissionName
	 * @return
	 */
	public boolean lacksPermission(String permissionName) {
		boolean lacksPermission = false;
		
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			// check to see if the user not has the permissionName
			if (!subject.isPermitted(permissionName.trim())) {
				lacksPermission = true;
			}
		}
		return lacksPermission;
	}

	/**
	 * 检验用户是否具有标识的角色.
	 * 
	 * @param roleName
	 * @return
	 */
	public boolean hasRole(String roleName) {
		boolean hasRole = false;

		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			// check to see if the user has the roleName
			if (subject.hasRole(roleName.trim())) {
				hasRole = true;
			}
		}
		return hasRole;
	}
	
	/**
	 * 检验用户是否具有任一标识的权限.
	 * 
	 * @param roleNames
	 * @return
	 */
	public boolean hasAnyRoles(String roleNames) {
		boolean hasAnyRole = false;

		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			// Iterate through permissions and check to see if the user has one of the roleNames
			for (String roleName : roleNames.split(PERMISSION_NAMES_DELIMETER)) {
				if (subject.hasRole(roleName.trim())) {
					hasAnyRole = true;
					break;
				}
			}
		}
		return hasAnyRole;
	}
	
	/**
	 * 检验用户是否不具有标识的角色.
	 * 
	 * @param roleName
	 * @return
	 */
	public boolean lacksRole(String roleName) {
		boolean lacksRole = false;
		
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			// check to see if the user not has the roleName
			if (!subject.hasRole(roleName.trim())) {
				lacksRole = true;
			}
		}
		return lacksRole;
	}
}
