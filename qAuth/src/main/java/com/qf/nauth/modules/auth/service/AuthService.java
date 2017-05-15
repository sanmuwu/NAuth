package com.qf.nauth.modules.auth.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qf.nauth.modules.auth.dao.AuthDao;
import com.qf.nauth.modules.auth.entity.TokenPO;


/**
 * 
 * ClassName: AuthService <br/>
 * Function: TODO AD域认证相关. <br/>
 * date: 2017年3月31日 下午5:44:41 <br/>
 * 
 * @author YiqiangShen
 * @version
 * @since JDK 1.8
 */
@Service
public class AuthService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

/*	@Resource
	private LdapService ldapServ;

	@Resource
	private TokenService tokenServ;*/
	
	@Resource
	private AuthDao authDao;

	/*	// auth by AD
	public boolean authLogin(Map<String, String> param) {
		if (validateAuthParam(param)) {
			TokenPO token = tokenServ.getToken(param);
			// first time to auth
			if (token == null) {
				LdapContext ctx = ldapServ.connect(param);
				if(ctx != null){
					tokenServ.saveOrUpdate(null, param);
					try {
						ctx.close();
					} catch (NamingException e) {
						log.error("ldap context closed failed!", e);
					}
					return true;
				}
			}
			if (token != null) {
				// token is not overtime
				if (!tokenServ.isOvertime(null, token.getCreTime())) {
					return true;
				}
				// token is ot and need to update old token
				if (tokenServ.isOvertime(null, token.getCreTime()) && ldapServ.connect(param) != null) {
					tokenServ.saveOrUpdate(token, null);
					return true;
				}
			}
		}
		return false;
	}

	private boolean validateAuthParam(Map<String, String> param) {
		log.info("===auth input:{}", param);
		if (!param.containsKey("tid") || StringUtils.isEmpty(param.get("tid"))) {
			return false;
		}
		if (!param.containsKey("uid") || StringUtils.isEmpty(param.get("uid"))) {
			return false;
		}
		if (!param.containsKey("pwd") || StringUtils.isEmpty(param.get("pwd"))) {
			return false;
		}
		return true;
	}*/

	// auth by AD
	public boolean authAction(Map<String, String> param) {
		if (validateActAuthParam(param)) {
			Integer cnt = authDao.getPermissionCnt(param);
			if(cnt.intValue() > 0){
				return true;
			}
			return false;
		}
		return false;
	}

	private boolean validateActAuthParam(Map<String, String> param) {
		log.info("===act auth input:{}", param);
		if (!param.containsKey("uname") || StringUtils.isEmpty(param.get("uname"))) {
			return false;
		}
		if (!param.containsKey("href") || StringUtils.isEmpty(param.get("href"))) {
			return false;
		}
		if (!param.containsKey("permission") || StringUtils.isEmpty(param.get("permission"))) {
			return false;
		}
		return true;
	}
}
