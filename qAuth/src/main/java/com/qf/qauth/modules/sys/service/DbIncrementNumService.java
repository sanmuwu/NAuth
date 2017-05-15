/**
 * 
 */
package com.qf.qauth.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qf.qauth.common.service.CrudService;
import com.qf.qauth.common.utils.StringUtils;
import com.qf.qauth.modules.sys.dao.DbIncrementNumDao;
import com.qf.qauth.modules.sys.entity.DbIncrementNum;

/**
 * @author MengpingZeng
 *
 */
@Service
public class DbIncrementNumService extends CrudService<DbIncrementNumDao, DbIncrementNum> {
	
	@Autowired
	private DbIncrementNumDao incrementNumDao;

	@Transactional(propagation = Propagation.REQUIRED, isolation=Isolation.SERIALIZABLE, rollbackFor=Exception.class)
	public synchronized int getNextNum(String bussKey) {
		DbIncrementNum entity = new DbIncrementNum();
		entity.setBussKey(bussKey);
		entity = incrementNumDao.getCurrNoByBussKey(entity);
		if(entity != null) {
			entity.setCurrNo(entity.getCurrNo() + 1);
		} else {
			entity = new DbIncrementNum();
			entity.setBussKey(StringUtils.trim(bussKey));
			entity.setCurrNo(1);
		}
		super.save(entity);
		return entity.getCurrNo();
	}
}
