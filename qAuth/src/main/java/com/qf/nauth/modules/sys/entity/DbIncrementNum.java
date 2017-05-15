/**
 * 
 */
package com.qf.nauth.modules.sys.entity;

import com.qf.nauth.common.persistence.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库自增实体对象.
 * @author MengpingZeng
 *
 */
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class DbIncrementNum extends DataEntity<DbIncrementNum> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8309969717966904935L;
	
	private String bussKey;
	
	private Integer currNo;

}
