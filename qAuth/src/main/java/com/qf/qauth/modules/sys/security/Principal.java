/**
 * 
 */
package com.qf.qauth.modules.sys.security;

import java.io.Serializable;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;

import com.qf.qauth.common.utils.IdGen;
import com.qf.qauth.modules.sys.entity.User;
import com.qf.qauth.modules.sys.utils.UserUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * shiro认证实体父类.
 * @author MengpingZeng
 *
 */
@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "id", "name", "mobileLogin" }, doNotUseGetters = false, callSuper = false) 	// 等于Eclipse的重构Generator特性。
public class Principal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;			  	//编号
	
	private String name;			//显示名称
	
	private String loginName; 		// 登录名
	
	private boolean mobileLogin; 	// 是否手机登录
	
	public Principal(User user, boolean mobileLogin) {
		setId(user.getId());
		setName(user.getName());
		setLoginName(user.getLoginName());
		setMobileLogin(mobileLogin);
	}

	public Principal(UsernamePasswordToken token, boolean mobileLogin) {
		setId(IdGen.uuid());
		setName(token.getUsername());
		setLoginName(token.getUsername());
		setMobileLogin(mobileLogin);
	}
	
	/**
	 * 获取session对象
	 * @return session对象
	 */
	public Session getSession() {
		try {
			return UserUtils.getSession();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取SESSIONID
	 */
	public String getSessionid() {
		try {
			return (String) UserUtils.getSession().getId();
		} catch (Exception e) {
			return "";
		}
	}
}
