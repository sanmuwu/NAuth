/**
 * 
 */
package com.qf.nauth.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qf.nauth.common.utils.CacheUtils;
import com.qf.nauth.common.web.ResponseData;
import com.qf.nauth.modules.sys.entity.MenuPermission;
import com.qf.nauth.modules.sys.entity.PermissionRole;
import com.qf.nauth.modules.sys.entity.User;
import com.qf.nauth.modules.sys.security.exception.RestFulException;
import com.qf.nauth.modules.sys.service.SystemService;
import com.qf.nauth.modules.sys.utils.UserUtils;

/**
 * 应用系统权限查询接口.
 * @author MengpingZeng
 *
 */
@RestController
public class AppPermissionController {

	private static Logger logger = LoggerFactory.getLogger(AppPermissionController.class);

	@Autowired
	private SystemService systemService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "${adminPath}/permissions")
	public ResponseData getUserPermissions(ServletRequest request, ServletResponse response) throws RestFulException {
		try {
			
			String username = request.getParameter("username");
			username = StringUtils.isBlank(username) ? (String) SecurityUtils.getSubject().getSession().getAttribute("username") : username;
			if(StringUtils.isBlank(username)) {
				return ResponseData.ok().putDataValue(ResponseData.ERRORS_KEY, "用户名不存在");
			}
			User user = UserUtils.getUser(username);
			if(null != user) {
				logger.info("用户名：" + user.getName());
				
				String showRoleList = (String) request.getParameter("showRoleList");
				String showMenuList = (String) request.getParameter("showMenuList");
				
				ResponseData respData = ResponseData.ok();
				// 获取勾勾集合
				List<PermissionRole> roleList = (List<PermissionRole>) CacheUtils.get(UserUtils.USER_CACHE, UserUtils.CACHE_ROLE_LIST + "_" + user.getId());
				if(CollectionUtils.isEmpty(roleList)) {
					roleList = systemService.getRoleListByUser(user);
					CacheUtils.put(UserUtils.USER_CACHE, UserUtils.CACHE_ROLE_LIST + "_" + user.getId(), roleList);
				}
				// 是否显示角色集合
				if(StringUtils.isBlank(showRoleList) || Boolean.valueOf(showRoleList)) {
					respData.putDataValue("roleList", roleList);
				}
				
				// 是否显示角色集合
				if(StringUtils.isBlank(showMenuList) || Boolean.valueOf(showMenuList)) {
					List<MenuPermission> menuList = (List<MenuPermission>) CacheUtils.get(UserUtils.USER_CACHE, UserUtils.CACHE_MENU_LIST + "_" + user.getId());
					
					if(CollectionUtils.isEmpty(menuList)) {
						List<String> roleIds = new ArrayList<String>();
						for(PermissionRole role : roleList) {
							roleIds.add(role.getId());
						}
						menuList = systemService.getMenuListByRoleIds(roleIds);
						
						CacheUtils.put(UserUtils.USER_CACHE, UserUtils.CACHE_MENU_LIST + "_" + user.getId(), menuList);
					}
					respData.putDataValue("menuList", menuList);
				}
				return respData;
			} else {
				return ResponseData.ok().putDataValue(ResponseData.ERRORS_KEY, "用户名不存在");
			}
		} catch (Exception e) {
			if(logger.isErrorEnabled()) {
				logger.error("查询用户授权集合出错，异常信息如下:\n", e);
			}
			throw new RestFulException(e);
		}
	}
/*	
	@RequestMapping(value = "${adminPath}/getNextMenuCode")
	public ResponseData getNextNo(ServletRequest request, ServletResponse response) throws RestFulException {
		String menuCode = systemService.getNextMenuCode();
		return ResponseData.ok().putDataValue("menuCode", menuCode);
	}*/
}
