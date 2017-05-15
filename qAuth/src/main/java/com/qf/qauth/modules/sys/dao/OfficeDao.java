/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.qauth.modules.sys.dao;

import com.qf.qauth.common.persistence.TreeDao;
import com.qf.qauth.common.persistence.annotation.MyBatisDao;
import com.qf.qauth.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office checkOrgCode(Office office);
	
	public Office getByOrgName(Office office);
	
	public Office checkOrgName(Office office);
}
