package com.qf.nauth.modules.auth.entity;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ClassName: TokenPO <br/>  
 * Function: TODO ADPO对象. <br/>  
  * date: 2017年3月31日 下午5:45:14 <br/>  
 *  
 * @author YiqiangShen  
 * @version   
 * @since JDK 1.8
 */
@Repository
@Getter
@Setter
public class AdPO {
	private final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private final String SECURITY_AUTHENTICATION = "simple";
	private String dn;
	private String url;
	private String domain;
}
