/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.qauth.modules.sys.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.qf.qauth.common.config.Global;
import com.qf.qauth.common.servlet.ValidateCodeServlet;
import com.qf.qauth.common.utils.CacheUtils;
import com.qf.qauth.common.utils.CookieUtils;
import com.qf.qauth.common.utils.IdGen;
import com.qf.qauth.common.utils.StringUtils;
import com.qf.qauth.common.web.BaseController;
import com.qf.qauth.modules.sys.constants.AuthParamConstants;
import com.qf.qauth.modules.sys.security.Principal;
import com.qf.qauth.modules.sys.security.exception.RestFulException;
import com.qf.qauth.modules.sys.security.filters.AuthcAuthenticationFilter;
import com.qf.qauth.modules.sys.security.realms.SystemDbAuthenticationRealm.DbPrincipal;
import com.qf.qauth.modules.sys.security.session.SessionDAO;
import com.qf.qauth.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private SessionDAO sessionDAO;
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		// 如果已登录，再次访问主页，则退出原账号。
/*		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			CookieUtils.setCookie(response, "LOGINED", "false");
		}*/
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()) {
			return "redirect:" + adminPath;
		}
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败或者使用POST方式请求访问入口，真正登录的POST请求由Filter完成
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Principal principal = UserUtils.getPrincipal();
		
		// 获取认证异常对象
		Class<?> exceptionClsname = (Class<?>) request.getAttribute(AuthcAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(AuthcAuthenticationFilter.MESSAGE_PARAM);
		// 是否有认证异常
		if(null != exceptionClsname && (AuthenticationException.class.isAssignableFrom(exceptionClsname)
				|| IncorrectCredentialsException.class.getName().equals(exceptionClsname.getName())
				|| UnknownAccountException.class.getName().equals(exceptionClsname.getName()))) {
			String username = WebUtils.getCleanParam(request, AuthParamConstants.getUserNameParam());
			boolean rememberMe = WebUtils.isTrue(request, AuthParamConstants.getRememberMeparam());
			boolean mobile = WebUtils.isTrue(request, AuthParamConstants.getMobileLoginParam());
			
			model.addAttribute(AuthParamConstants.getRememberMeparam(), username);
			model.addAttribute(AuthParamConstants.getRememberMeparam(), rememberMe);
			model.addAttribute(AuthParamConstants.getMobileLoginParam(), mobile);
			model.addAttribute(AuthcAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exceptionClsname);
			model.addAttribute(AuthcAuthenticationFilter.MESSAGE_PARAM, message);
			
			if (logger.isDebugEnabled()){
				logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exceptionClsname);
			}
			
			// 验证失败清空验证码
			if (!UnauthorizedException.class.getName().equals(exceptionClsname)) {
				// 非授权异常，登录失败，验证码加1。
				model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
			}
			// 验证失败清空验证码
			request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
			
			// 如果是手机登录，则返回JSON字符串
			if (mobile) {
				return renderString(response, model);
			}
			return "modules/sys/sysLogin";
		}
		// 如果已经登录，则跳转到管理首页
		if(principal != null) {
			return "redirect:" + adminPath;
		}
		return null;
	}
	
	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal(); 
		
		// 是否数据库登陆认证
		if(principal instanceof DbPrincipal) {
			DbPrincipal dbPrincipal = (DbPrincipal) principal;
			// 登录成功后，验证码计算器清零
			isValidateCodeLogin(dbPrincipal.getLoginName(), false, true);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)) {
				CookieUtils.setCookie(response, "LOGINED", "true");
			} else if (StringUtils.equals(logined, "true")) {
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}

		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()) {
			if (request.getParameter("login") != null) {
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null) {
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}
			
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 登录成功后的欢迎页面
	 */
	//@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}/loginWelcome")
	public String loginWelcome(HttpServletRequest request, HttpServletResponse response) {
		return "modules/sys/sysLoginWelcome";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isNotBlank(theme)) {
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:" + request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}

    @RequestMapping("${adminPath}/logout")
/*    @ResponseBody*/
    public String logout(HttpServletRequest request, HttpServletResponse response) throws RestFulException {
        Subject subject = SecurityUtils.getSubject();
        // 是否有认证实体对象
        if(null != subject) {
        	subject.logout();
        }
        return "redirect:" + adminPath + "/login";
    }
}
