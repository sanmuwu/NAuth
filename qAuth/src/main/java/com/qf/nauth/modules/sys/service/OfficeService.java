/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.nauth.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qf.nauth.common.service.TreeService;
import com.qf.nauth.common.utils.IdGen;
import com.qf.nauth.modules.sys.dao.OfficeDao;
import com.qf.nauth.modules.sys.entity.Area;
import com.qf.nauth.modules.sys.entity.Office;
import com.qf.nauth.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		if(office.getArea() == null) {
			Area area = new Area();
			area.setId(IdGen.uuid());
			office.setArea(area);
		}
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	//通过orgcode获取机构信息
	public Office checkOrgCode(String code){
		Office office = new Office();
		office.setCode(code);
		
		return dao.checkOrgCode(office);
	}
	
	//通过orgcode获取机构信息
	public Office getByOrgName(String name,String type){
		Office office = new Office();
		office.setName(name);
		office.setType(type);
		
		return dao.getByOrgName(office);
	}
		
	//通过orgcode获取机构信息
	public Office checkOrgName(String name){
		Office office = new Office();
		office.setName(name);
		
		return dao.checkOrgName(office);
	}
}
