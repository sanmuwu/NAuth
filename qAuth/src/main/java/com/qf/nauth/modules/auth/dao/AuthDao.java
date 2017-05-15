/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.nauth.modules.auth.dao;

import java.util.Map;

import com.qf.nauth.common.persistence.annotation.MyBatisDao;
import com.qf.nauth.modules.auth.entity.AdPO;



/**
 * 
 * ClassName: AuthDao <br/>  
 * Function: TODO 用户认证，行为认证. <br/>  
  * date: 2017年3月31日 下午5:47:45 <br/>  
 *  
 * @author YiqiangShen  
 * @version   
 * @since JDK 1.8
 */
@MyBatisDao
public interface AuthDao {

	//public AdPO getAd();
	
	public Integer getPermissionCnt(Map<String, String> param);
	
}
