package com.qf.nauth.modules.auth.entity;

import java.io.Serializable;

/**
 * 接口返回用户信息实体类
 * 
 * @author SenWu
 *
 */
public class UserEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2662712480315453061L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户姓名
	 */
	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
