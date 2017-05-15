package com.qf.nauth.modules.auth.entity;

import java.io.Serializable;

/**
 * 接口返回的按钮级别权限实体类
 * 
 * @author SenWu
 *
 */
public class FieldEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7551197120589264834L;

	private String id;

	private String fieldId;

	private String fieldName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
