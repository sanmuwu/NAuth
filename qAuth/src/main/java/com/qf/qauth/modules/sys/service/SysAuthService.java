/**
 * 
 */
package com.qf.qauth.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qf.qauth.modules.sys.dao.SysAuthDao;
import com.qf.qauth.modules.sys.entity.AuthUser;
import com.qf.qauth.modules.sys.entity.MenuPermission;
import com.qf.qauth.modules.sys.entity.PermissionRole;

/**
 * @author MengpingZeng
 *
 */
@Service
public class SysAuthService {
	
	@Autowired
	private SysAuthDao sysAuthDao;

	/**
	 * 获取用户角色集合.
	 * @param user
	 * @return
	 */
	public List<PermissionRole> getRoleListByUser(AuthUser user) {
		return sysAuthDao.getRoleListByUser(user);
	}

	/**
	 * 根据角色ID获取用户权限集合.
	 * @param roleIds
	 * @return
	 */
	public List<MenuPermission> getMenuListByRoleIds(List<String> roleIds) {
		return sysAuthDao.getMenuListByRoleIds(roleIds);
	}
}
