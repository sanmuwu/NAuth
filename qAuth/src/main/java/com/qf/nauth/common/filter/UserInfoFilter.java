/**
 * 
 */
package com.qf.nauth.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qf.nauth.modules.sys.utils.UserUtils;

/**
 * @author MengpingZeng
 *
 */
public class UserInfoFilter implements Filter {

	
	private static Logger logger = LoggerFactory.getLogger(UserInfoFilter.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String username = (String) request.getParameter("username");
		if(logger.isDebugEnabled()) {
			logger.debug("请求用户名：" + username);
		}
		if(StringUtils.isBlank(username)) {
			username = (String) SecurityUtils.getSubject().getSession().getAttribute("username");
		}
		
		UserUtils.getUser(username);
		//获取用户信息
		filterChain.doFilter(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		UserUtils.clearCache();
	}

}
