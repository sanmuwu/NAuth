package com.qf.nauth.modules.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qf.nauth.modules.auth.dao.AuthorizingDao;
import com.qf.nauth.modules.auth.entity.MenuListEntity;
import com.qf.nauth.modules.auth.entity.RoleListEntity;
import com.qf.nauth.modules.auth.entity.UserEntity;

/**
 * 授权信息服务类
 * 
 * @author SenWu
 *
 */
@Service
@Transactional(readOnly = true)
public class AuthorizingService {

	@Autowired
	private AuthorizingDao authorizingDao;

	/**
	 * 根据登录名查询用户
	 * 
	 * @param userId
	 * @return
	 */
	public UserEntity getByLoginName(String userId) {

		return authorizingDao.getByLoginName(userId);
	}

	/**
	 * 查询用户角色集合
	 * 
	 * @param map
	 * @return
	 */
	public List<RoleListEntity> getRoleListByUser(Map<String, Object> map) {

		return authorizingDao.getRoleListByUser(map);
	}

	/**
	 * 查询权限用户菜单列表集合
	 * 
	 * @param map
	 * @return
	 */
	public List<MenuListEntity> getMenuList(Map<String, Object> map) {

		return authorizingDao.getMenuList(map);
	}

}
