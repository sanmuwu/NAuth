package com.qf.nauth.modules.auth.dao;

import java.util.List;
import java.util.Map;

import com.qf.nauth.common.persistence.annotation.MyBatisDao;
import com.qf.nauth.modules.auth.entity.MenuListEntity;
import com.qf.nauth.modules.auth.entity.RoleListEntity;
import com.qf.nauth.modules.auth.entity.UserEntity;

/**
 * 授权信息DAO类
 * 
 * @author SenWu
 *
 */
@MyBatisDao
public interface AuthorizingDao {

	/**
	 * 根据登录名查询用户
	 * 
	 * @param userId
	 * @return
	 */
	public UserEntity getByLoginName(String userId);

	/**
	 * 查询用户角色集合
	 * 
	 * @param map
	 * @return
	 */
	public List<RoleListEntity> getRoleListByUser(Map<String, Object> map);
	
	
	/**
	 * 查询权限用户菜单列表集合
	 * 
	 * @param map
	 * @return
	 */
	public List<MenuListEntity> getMenuList(Map<String, Object> map);

}
