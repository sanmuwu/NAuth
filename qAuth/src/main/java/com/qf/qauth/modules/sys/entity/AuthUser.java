/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.qauth.modules.sys.entity;

/**
 * 授权用户Entity
 * 
 */
public class AuthUser extends User {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return getName() + "|" + id;
	}
}