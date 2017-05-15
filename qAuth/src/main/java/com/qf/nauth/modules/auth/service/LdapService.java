package com.qf.nauth.modules.auth.service;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qf.nauth.modules.auth.constant.Adconfigs;
import com.qf.nauth.modules.auth.constant.Constant;

/**
 * 
 * ClassName: LdapService <br/>
 * Function: TODO AD连接相关. <br/>
 * date: 2017年3月31日 下午5:46:13 <br/>
 * 
 * @author YiqiangShen
 * @version
 * @since JDK 1.8
 */
@Service
public class LdapService implements InitializingBean {

	private Logger log = LoggerFactory.getLogger(this.getClass());

/*	private AdPO ad;

	@Resource
	private AuthDao authDao;*/

	@Autowired
	private Adconfigs adconfigs;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//ad = authDao.getAd();
	}

	public LdapContext connectLocal(Map<String, String> param) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, adconfigs.getINITIAL_CONTEXT_FACTORY());
		env.put(Context.SECURITY_AUTHENTICATION, adconfigs.getSECURITY_AUTHENTICATION());
		env.put(Context.PROVIDER_URL, adconfigs.getUrl());
		env.put(Context.SECURITY_PRINCIPAL, param.get("uid") + adconfigs.getDomain()); // 管理员
		env.put(Context.SECURITY_CREDENTIALS, param.get("pwd"));
		LdapContext ctx = null;
		Control[] connCtls = null;
		try {
			return new InitialLdapContext(env, connCtls);
		} catch (Exception e) {
			log.error("===ldap connect ad failed, env:{}, input:{}", env, param, e);
		}
		return null;
	}

	public LdapContext connect(Map<String, String> param) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, adconfigs.getINITIAL_CONTEXT_FACTORY());
		env.put(Context.SECURITY_AUTHENTICATION, adconfigs.getSECURITY_AUTHENTICATION());
		env.put(Context.PROVIDER_URL, adconfigs.getUrl());
		env.put(Context.SECURITY_PRINCIPAL, param.get("uid") + "@" + param.get("tid")); // 管理员
		env.put(Context.SECURITY_CREDENTIALS, param.get("pwd"));
		LdapContext ctx = null;
		Control[] connCtls = null;
		try {
			return new InitialLdapContext(env, connCtls);
		} catch (Exception e) {
			log.error("===ldap connect ad failed, env:{}, input:{}", env, param, e);
		}
		return null;
	}

	public void search(Map<String, String> param, Map<String, String> resultMap) {
		try {
			LdapContext ctx = connect(param);
			if (ctx != null) {
				SearchControls constraints = new SearchControls();
				constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
				NamingEnumeration<SearchResult> en = ctx.search(adconfigs.getDn(), param.get(Constant.AD_SEARCH_KEY),
						constraints);
				while (en != null && en.hasMoreElements()) {
					Attributes atts = en.nextElement().getAttributes();
					NamingEnumeration<? extends Attribute> all = atts.getAll();
					while (all.hasMoreElements()) {
						Attribute attr = all.next();
						if (resultMap.containsKey(attr.getID()) && attr.get() != null) {
							resultMap.put(attr.getID(), attr.get().toString());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("param:{}", param, e);
		}

	}

/*	public AdPO getAd() {
		return ad;
	}*/

}
