/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.qauth.modules.sys.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.qf.qauth.common.persistence.TreeEntity;

/**
 * 机构Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = 1L;
//	private Office parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private Area area;		// 归属区域
	private String code; 	// 机构编码
//	private Area area ;
//	private String name; 	// 机构名称
//	private Integer sort;		// 排序
	private String type; 	// 机构类型（1：公司；2：机构；3：部门；4：区域；5：城市；6：分中心；7团队；8营业部；9其他,10 临时职场）
	private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	private String useable;//是否可用
	private User primaryPerson;//主负责人
	private User deputyPerson;//副负责人
	
	private String oldCode;
	private String oldName;
	//===========================================================增加=============
	
	private String orgcode1;
	private String cname1;
	private String orgcode2;
	private String cname2;
	private String orgcode3;
	private String cname3;
	private String orgcode4;
	private String cname4;
	private String orgcode5;
	private String cname5;
	private String orgcode6;
	private String cname6;
	private String orgcode7;
	private String cname7;
	//===========================================================增加=============
	
	
	
	private List<String> childDeptList;//快速添加子部门
	
	
	//添加新的字段成立日期，运营日期
	private String officeCreateDate;
	private String officeBusinessDate;
	
	
	
	
	public Office(){
		super();
//		this.sort = 30;
		this.type = "2";
	}

	public Office(String id){
		super(id);
	}
	
	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public User getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(User primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

//	@JsonBackReference
//	@NotNull
	public Office getParent() {
		return parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
	}
//
//	@Length(min=1, max=2000)
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}

//
//	@Length(min=1, max=100)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}
	
	@Length(min=1, max=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=1, max=1)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min=0, max=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=100)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min=0, max=100)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Length(min=0, max=200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min=0, max=200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
	
	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getOfficeCreateDate() {
		return officeCreateDate;
	}

	public void setOfficeCreateDate(String officeCreateDate) {
		this.officeCreateDate = officeCreateDate;
	}
	
	public String getOfficeBusinessDate() {
		return officeBusinessDate;
	}

	public void setOfficeBusinessDate(String officeBusinessDate) {
		this.officeBusinessDate = officeBusinessDate;
	}

	
	
	
	//========================================增加方法==========================
	
	public String getOrgcode1() {
		return orgcode1;
	}

	public void setOrgcode1(String orgcode1) {
		this.orgcode1 = orgcode1;
	}

	public String getCname1() {
		return cname1;
	}

	public void setCname1(String cname1) {
		this.cname1 = cname1;
	}
	
	public String getOrgcode2() {
		return orgcode2;
	}

	public void setOrgcode2(String orgcode2) {
		this.orgcode2 = orgcode2;
	}
	
	public String getCname2() {
		return cname2;
	}

	public void setCname2(String cname2) {
		this.cname2 = cname2;
	}

	public String getOrgcode3() {
		return orgcode3;
	}

	public void setOrgcode3(String orgcode3) {
		this.orgcode3 = orgcode3;
	}

	public String getCname3() {
		return cname3;
	}

	public void setCname3(String cname3) {
		this.cname3 = cname3;
	}

	public String getOrgcode4() {
		return orgcode4;
	}

	public void setOrgcode4(String orgcode4) {
		this.orgcode4 = orgcode4;
	}

	public String getCname4() {
		return cname4;
	}

	public void setCname4(String cname4) {
		this.cname4 = cname4;
	}

	public String getOrgcode5() {
		return orgcode5;
	}

	public void setOrgcode5(String orgcode5) {
		this.orgcode5 = orgcode5;
	}

	public String getCname5() {
		return cname5;
	}

	public void setCname5(String cname5) {
		this.cname5 = cname5;
	}
	
	public String getOrgcode6() {
		return orgcode6;
	}

	public void setOrgcode6(String orgcode6) {
		this.orgcode6 = orgcode6;
	}
	
	public String getCname6() {
		return cname6;
	}

	public void setCname6(String cname6) {
		this.cname6 = cname6;
	}
	
	public String getOrgcode7() {
		return orgcode7;
	}

	public void setOrgcode7(String orgcode7) {
		this.orgcode7 = orgcode7;
	}
	
	public String getCname7() {
		return cname7;
	}

	public void setCname7(String cname7) {
		this.cname7 = cname7;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
}