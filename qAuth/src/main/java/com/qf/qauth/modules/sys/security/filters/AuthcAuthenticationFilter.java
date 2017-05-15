/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.qf.qauth.modules.sys.security.filters;

import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qf.qauth.common.config.Global;
import com.qf.qauth.common.utils.SpringContextHolder;
import com.qf.qauth.common.utils.StringUtils;
import com.qf.qauth.modules.sys.constants.AuthParamConstants;
import com.qf.qauth.modules.sys.security.Principal;
import com.qf.qauth.modules.sys.security.tokens.UsernamePasswordToken;
import com.qf.qauth.modules.sys.service.SystemService;
import com.qf.qauth.modules.sys.utils.UserUtils;

import lombok.Setter;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2014-5-19
 */
public class AuthcAuthenticationFilter extends FormAuthenticationFilter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String MESSAGE_PARAM = "message";
	
	@Setter
	private String isLoginUrl;
	
	private SystemService systemService;
	
	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	public String getMessageParam() {
		return MESSAGE_PARAM;
	}
	
	@Override
	public String getUsername(ServletRequest request) {
		return WebUtils.getCleanParam(request, AuthParamConstants.getUserNameParam());
	}

	private String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, AuthParamConstants.getCaptchaParam());
	}
	
	private boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, AuthParamConstants.getMobileLoginParam());
    }

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);

		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, UserUtils.getPrincipal(), UserUtils.getSession());
		if(sessions.size() > 0) {
			// 是否可以重复登陆
			if(!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))) {
				return invalidBeforeLogin(sessions, token, subject, request, response);
			} else {
				return rejectSecondLogin(sessions, token, subject, request, response);
			}
		} else {
			return super.onLoginSuccess(token, subject, request, response);
		}
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		Class<?> exClassName = e.getClass();
		String message = "";
		// 处理认证异常类型
		if (IncorrectCredentialsException.class.getName().equals(exClassName.getName())
				|| UnknownAccountException.class.getName().equals(exClassName.getName())) {
			message = "用户或密码错误, 请重试.";
		} else if(AuthenticationException.class.isAssignableFrom(exClassName)) {
			message = e.getMessage();
		} else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		} else {
			if(logger.isErrorEnabled()) {
				logger.error("登陆认证出错,异常信息如下:\n", e);
			}
			message = "系统出现点问题，请稍后再试！";
		}
		// 设置异常类名称
		request.setAttribute(getFailureKeyAttribute(), exClassName);
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
	/**
	 * fix relogin
	 * 拒绝二次登录.
	 */
	private boolean rejectSecondLogin(Collection<Session> sessions, AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		Principal currPrincipal = (Principal) subject.getPrincipal();
		if (UserUtils.getSubject().isAuthenticated()) {
			for (Session session : sessions) { 
				Principal prevPrincipal = (Principal) session.getAttribute(session.getId());
				if(prevPrincipal != null && prevPrincipal.getLoginName().equals(currPrincipal.getLoginName())) {
					getSystemService().getSessionDao().delete(session);
					SecurityUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已经登录，请勿重复登录");
				}
			}
		}
		Session currentSession = UserUtils.getSession();
		currentSession.setAttribute(currentSession.getId(), subject.getPrincipal());
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	/**
	 * fix relogin
	 * 注销上次登录, 让上次登录失效.
	 * 
	 * 注: 这里登出不能使用 Subject 的 logout, 将会导致异常.
	 * 需使用 session 的 stop 方法来登出.
	 * @author zmp
	 */
	private boolean invalidBeforeLogin(Collection<Session> sessions, AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		Principal currPrincipal = (Principal) subject.getPrincipal();
		//是否已经登陆
		if (UserUtils.getSubject().isAuthenticated()) {
			for (Session session : sessions) { 
				Principal prevPrincipal = (Principal) session.getAttribute(session.getId());
				if(prevPrincipal != null && prevPrincipal.getLoginName().equals(currPrincipal.getLoginName())) {
					getSystemService().getSessionDao().delete(session);
					break;
				}
			}
		} else {
			// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
			UserUtils.getSubject().logout();
			throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
		}
		Session currentSession = UserUtils.getSession();
		currentSession.setAttribute(currentSession.getId(), subject.getPrincipal());
		return super.onLoginSuccess(token, subject, request, response);
	}

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	Subject subject = getSubject(request, response);
    	if(subject.isAuthenticated()) {
    		return true;
    	} else {
    		return super.onAccessDenied(request, response);
    	}
    }
    
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		// TODO Auto-generated method stub
		/*Subject subject = getSubject(request, response);
		Object principal = subject.getPrincipal();
		AuthenticationToken token = null;*/
		
		/*if(principal == null) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			// 是否登陆请求
			if(httpRequest.getRequestURI().indexOf(httpRequest.getContextPath() + isLoginUrl) < 0) {
				token = createToken(request, response);
				try {
					subject.login(token);
					return onLoginSuccess(token, subject, request, response);
				} catch (Exception e) {
					return onLoginFailure(token, new AuthenticationException(e), request, response);
				}
			} else {
				return super.isAccessAllowed(request, response, mappedValue);
			}
		}*/
		return super.isAccessAllowed(request, response, mappedValue);
	}
}