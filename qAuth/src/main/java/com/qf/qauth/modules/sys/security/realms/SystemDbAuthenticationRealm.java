/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.qauth.modules.sys.security.realms;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.qf.qauth.common.config.Global;
import com.qf.qauth.common.servlet.ValidateCodeServlet;
import com.qf.qauth.common.utils.Encodes;
import com.qf.qauth.common.utils.SpringContextHolder;
import com.qf.qauth.common.web.Servlets;
import com.qf.qauth.modules.sys.entity.Menu;
import com.qf.qauth.modules.sys.entity.Role;
import com.qf.qauth.modules.sys.entity.User;
import com.qf.qauth.modules.sys.security.Principal;
import com.qf.qauth.modules.sys.security.exception.DbAuthenticationException;
import com.qf.qauth.modules.sys.security.tokens.UsernamePasswordToken;
import com.qf.qauth.modules.sys.service.SystemService;
import com.qf.qauth.modules.sys.utils.LogUtils;
import com.qf.qauth.modules.sys.utils.UserUtils;
import com.qf.qauth.modules.sys.web.LoginController;

import lombok.ToString;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-5
 */
public class SystemDbAuthenticationRealm extends AuthorizingRealm {
	
	private SystemService systemService;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken dbAuthcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) dbAuthcToken;
		
		// 校验登录验证码
		if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)) {
			Session session = UserUtils.getSession();
			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
				throw new DbAuthenticationException("msg:验证码错误, 请重试.");
			}
		}
		
		// 校验用户名密码
		User user = getSystemService().getUserByLoginName(token.getUsername());
		if (user != null) {
			if (Global.NO.equals(user.getLoginFlag())) {
				throw new DbAuthenticationException("msg:该帐号已禁止登录.");
			}
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
			return new SimpleAuthenticationInfo(new DbPrincipal(user, token.isMobileLogin()),
					user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if(principal instanceof DbPrincipal) {
			DbPrincipal currPrincipal = (DbPrincipal) principal;
			User user = getSystemService().getUserByLoginName(currPrincipal.getLoginName());
			if (user != null) {
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				List<Menu> list = UserUtils.getMenuList();
				for (Menu menu : list) {
					if (StringUtils.isNotBlank(menu.getPermission())) {
						// 添加基于Permission的权限信息
						for (String permission : StringUtils.split(menu.getPermission(),",")) {
							info.addStringPermission(permission);
						}
					}
				}
				// 添加用户权限
				info.addStringPermission("user");
				// 添加用户角色信息
				for (Role role : user.getRoleList()) {
					info.addRole(role.getEnname());
				}
				// 更新登录IP和时间
				getSystemService().updateUserLoginInfo(user);
				// 记录登录日志
				LogUtils.saveLog(Servlets.getRequest(), "系统登录");
				return info;
			} else {
				return null;
			}
		}  else {
			return null;
		}
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	
	/**
	 * 数据库登陆认证用户信息.
	 */
	@ToString(of = "loginName", callSuper = true)
	public static class DbPrincipal extends Principal implements Serializable {
		
		private static final long serialVersionUID = 7621884952854371915L;
		
		private String loginName; 			// 登录名
		

		public DbPrincipal(User user, boolean mobileLogin) {
			super(user, mobileLogin);
			this.loginName = user.getLoginName();
		}

		public String getLoginName() {
			return loginName;
		}
	}
}
