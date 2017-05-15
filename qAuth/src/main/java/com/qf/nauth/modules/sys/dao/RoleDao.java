/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.nauth.modules.sys.dao;

import java.util.List;

import com.qf.nauth.common.persistence.CrudDao;
import com.qf.nauth.common.persistence.annotation.MyBatisDao;
import com.qf.nauth.modules.sys.entity.PermissionRole;
import com.qf.nauth.modules.sys.entity.Role;
import com.qf.nauth.modules.sys.entity.User;

/**
 * 角色DAO接口
 * @author ThinkGem
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);
	
	List<PermissionRole> getRoleListByUser(User user);
}
