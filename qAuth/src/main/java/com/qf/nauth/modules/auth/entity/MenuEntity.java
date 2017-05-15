package com.qf.nauth.modules.auth.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 接口返回的菜单级别权限实体类
 * 
 * @author SenWu
 *
 */
public class MenuEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1088926589170054291L;

	private String id;

	private String menuName;

	private String menuHref;
	
	private List<FieldEntity> fieldList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuHref() {
		return menuHref;
	}

	public void setMenuHref(String menuHref) {
		this.menuHref = menuHref;
	}

	public List<FieldEntity> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FieldEntity> fieldList) {
		this.fieldList = fieldList;
	}
	
	
}
