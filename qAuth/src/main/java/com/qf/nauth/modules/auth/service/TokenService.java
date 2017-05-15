package com.qf.nauth.modules.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qf.nauth.modules.auth.constant.Constant;
import com.qf.nauth.modules.auth.dao.TokenDao;
import com.qf.nauth.modules.auth.entity.TokenPO;
import com.qf.nauth.modules.auth.utils.MD5Util;

/**
 * 
 * ClassName: TokenService <br/>
 * Function: TODO AD认证凭证相关 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月31日 下午5:42:35 <br/>
 * 
 * @author YiqiangShen
 * @version
 * @since JDK 1.8
 */
@Service
public class TokenService {

	/*private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private TokenDao tokenDao;

	public void saveOrUpdate(TokenPO token, Map<String, String> param) {
		if (token == null) {
			save(param);
		} else {
			update(token);
		}
	}

	// create new token data
	public void save(Map<String, String> param) {
		try {
			StringBuilder sb = new StringBuilder(100);
			sb.append(param.get("tid")).append(param.get("uid")).append(param.get("pwd"));
			Map<String, Object> tokenMap = new HashMap<>();
			tokenMap.put("token", createToken());
			tokenMap.put("cid", createCid(sb.toString()));
			tokenMap.put("creTime", new Date());
			log.info("===token save input:{}", tokenMap);
			tokenDao.save(tokenMap);
		} catch (Exception e) {
			log.error("===token failed to save, input:{}", param, e);
		}
	}

	// update token by cid
	public void update(TokenPO token) {
		try {
			Map<String, Object> tokenMap = new HashMap<>();
			tokenMap.put("token", createToken());
			tokenMap.put("cid", token.getCid());
			tokenMap.put("creTime", new Date());
			log.info("===token update input:{}", tokenMap);
			tokenDao.update(tokenMap);
		} catch (Exception e) {
			log.error("===token failed to update, input:{}", token, e);
		}
	}

	// get token by cid
	public TokenPO getToken(Map<String, String> param) {
		String cid = null;
		try {
			StringBuilder sb = new StringBuilder(100);
			sb.append(param.get("tid")).append(param.get("uid")).append(param.get("pwd"));
			cid = createCid(sb.toString());
			return tokenDao.get(cid);
		} catch (Exception e) {
			log.error("===token failed to get, input:{}", cid, e);
			return null;
		}
	}

	// to judge whether token is overtime
	public boolean isOvertime(String token, Date creTime) {
		if (creTime == null) {
			creTime = tokenDao.getCreTime(token);
		}
		if (System.currentTimeMillis() - creTime.getTime() > Constant.TOKEN_OT_PERIOD * 60 * 1000) {
			return true;
		}
		return false;
	}

	private String createToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public String createCid(String str) {
		log.info("===token cidStr:{}", str);
		return MD5Util.encrypt(str, null, "UTF-8");
	}*/

}
