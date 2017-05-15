/**
 * 
 */
package com.qf.nauth.modules.sys.dao;

import com.qf.nauth.common.persistence.CrudDao;
import com.qf.nauth.common.persistence.annotation.MyBatisDao;
import com.qf.nauth.modules.sys.entity.DbIncrementNum;

/**
 * @author MengpingZeng
 *
 */
@MyBatisDao
public interface DbIncrementNumDao extends CrudDao<DbIncrementNum> {

	DbIncrementNum getCurrNoByBussKey(DbIncrementNum param); 
}
