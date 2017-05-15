/**
 * 
 */
package com.qf.qauth.modules.sys.dao;

import com.qf.qauth.common.persistence.CrudDao;
import com.qf.qauth.common.persistence.annotation.MyBatisDao;
import com.qf.qauth.modules.sys.entity.DbIncrementNum;

/**
 * @author MengpingZeng
 *
 */
@MyBatisDao
public interface DbIncrementNumDao extends CrudDao<DbIncrementNum> {

	DbIncrementNum getCurrNoByBussKey(DbIncrementNum param); 
}
