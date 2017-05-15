package com.qf.nauth.modules.auth.web;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.qf.nauth.modules.auth.service.AuthService;
import com.qf.nauth.modules.sys.entity.User;
import com.qf.nauth.modules.sys.service.SystemService;

/**
 * 
 * ClassName: AuthController <br/>
 * Function: 进行AD域账号认证 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月31日 下午5:30:42 <br/>
 * 
 * @author YiqiangShen
 * @version
 * @since JDK 1.8
 */
@RestController
public class AuthController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private AuthService authServ;

	@Resource
	private SystemService sysServ;

//	// 认证域账号返回True或者False
//	@RequestMapping(value = "/authentication", produces = "application/json", method = RequestMethod.POST)
//	public String authAction(@RequestBody Map<String, Object> map) {
//		log.info("===auth request input:{}", map);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		try {
//			String tid = (String) map.get("tenantId");
//			String uid = (String) map.get("userId");
//			String pwd = (String) map.get("password");
//			Map<String, String> param = new HashMap<>();
//			param.put("tid", tid);
//			param.put("uid", uid);
//			param.put("pwd", pwd);
//			if (StringUtils.isEmpty(tid) || StringUtils.isEmpty(uid) || StringUtils.isEmpty(pwd)) {
//				resultMap.put("login", false);
//				return JSON.toJSONString(resultMap);
//			}
//
//			if (authServ.authLogin(param)) {
//				resultMap.put("login", true);
//				return JSON.toJSONString(resultMap);
//			}
//		} catch (Exception e) {
//			log.error("===auth failed, input:{}", map, e);
//		}
//		resultMap.put("login", false);
//		return JSON.toJSONString(resultMap);
//	}

	// 行为授权认证返回True或者False
	@RequestMapping(value = "${adminPath}/permission", produces = "application/json", method = RequestMethod.POST)
	public String auth(@RequestBody Map<String, String> param) {
		log.info("===permission request input:{}", param);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String uname = (String) param.get("uname");
			String href = (String) param.get("href");
			String permission = (String) param.get("permission");
			if (StringUtils.isEmpty(uname) || StringUtils.isEmpty(href) || StringUtils.isEmpty(permission)) {
				resultMap.put("auth", false);
				return JSON.toJSONString(resultMap);
			}

			resultMap.put("auth", authServ.authAction(param));
			return JSON.toJSONString(resultMap);
		} catch (Exception e) {
			log.error("===action auth failed, input:{}", param, e);
		}
		resultMap.put("auth", false);
		return JSON.toJSONString(resultMap);
	}

//	// 查询用户、权限信息
//	@RequestMapping(value = "/info/{uname}", method = RequestMethod.GET)
//	public String get(@PathVariable String uname) {
//		log.info("===get request uname:{}", uname);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		try {
//			if (StringUtils.isEmpty(uname)) {
//				resultMap.put("code", Constant.INVALID_PARAM_RESPONSE);
//				return JSON.toJSONString(resultMap);
//			}
//			User user = sysServ.getUserByLoginName(uname);
//			resultMap.put("code", Constant.SUCCESS_RESPONSE);
//			resultMap.put("data", user);
//			return JSON.toJSONString(resultMap, Constant.USER_JSON_FILTER);
//		} catch (Exception e) {
//			log.error("===action auth failed, uname:{}", uname, e);
//		}
//		resultMap.put("code", Constant.ERROR_RESPONSE);
//		return JSON.toJSONString(resultMap);
//	}

}
