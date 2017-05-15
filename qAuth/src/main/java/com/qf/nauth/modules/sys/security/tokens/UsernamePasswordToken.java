/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.nauth.modules.sys.security.tokens;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户和密码（包含验证码）令牌类
 * @author zmp.
 * @version 2013-5-19
 */
@Getter
@Setter
@NoArgsConstructor
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	
	/** 手机登陆 **/
	private boolean mobileLogin;

	public UsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha, boolean mobileLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
	}
	
	public String toString() {
		if(StringUtils.isNotBlank(captcha)) {
			return "username: " + super.getUsername() + " ,password: " + Arrays.toString(super.getPassword());
		} else {
			return "username: " + super.getUsername()  + " ,password: " + Arrays.toString(super.getPassword())
			+ "captcha: " + captcha;
		}
	}
}