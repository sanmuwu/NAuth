package com.qf.qauth.modules.sys.dao;

import java.util.List;

import com.qf.qauth.common.persistence.annotation.MyBatisDao;
import com.qf.qauth.modules.sys.entity.AuthUser;
import com.qf.qauth.modules.sys.entity.MenuPermission;
import com.qf.qauth.modules.sys.entity.PermissionRole;

/**
 * oauthUser 数据访问接口.
 * @author zmp.
 * 
 */
@MyBatisDao
public interface SysAuthDao {
	
	/**
	 * 根据登陆用户名获取授权用户.
	 * @param user
	 * @return
	 */
	AuthUser getByLoginName(AuthUser user);

	/**
	 * 获取用户角色集合.
	 * @param user
	 * @return
	 */
	List<PermissionRole> getRoleListByUser(AuthUser user);

	/**
	 * 根据角色ID获取用户权限集合.
	 * @param roleIds
	 * @return
	 */
	List<MenuPermission> getMenuListByRoleIds(List<String> roleIds);
}
