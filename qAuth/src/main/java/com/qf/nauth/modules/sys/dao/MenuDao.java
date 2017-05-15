/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.nauth.modules.sys.dao;

import java.util.List;

import com.qf.nauth.common.persistence.CrudDao;
import com.qf.nauth.common.persistence.annotation.MyBatisDao;
import com.qf.nauth.modules.sys.entity.Menu;
import com.qf.nauth.modules.sys.entity.MenuPermission;
import com.qf.nauth.modules.sys.entity.Permission;
import com.qf.nauth.modules.sys.entity.PermissionRole;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	List<MenuPermission> getMenuListByRole(PermissionRole role);
	
	List<Permission> getMenuPermissionListByMenu(MenuPermission menu);
	
	List<MenuPermission> getMenuListByRoleIds(List<String> roleIds);
}
