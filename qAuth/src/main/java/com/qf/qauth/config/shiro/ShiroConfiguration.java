/**
 * 
 */
package com.qf.qauth.config.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.qf.qauth.common.security.shiro.session.CacheSessionDAO;
import com.qf.qauth.common.utils.IdGen;
import com.qf.qauth.config.springbean.ContextProperties;
import com.qf.qauth.modules.sys.security.credentials.OAuthCredentialsMatcher;
import com.qf.qauth.modules.sys.security.filters.AuthcAuthenticationFilter;
import com.qf.qauth.modules.sys.security.realms.LdapAuthenticationRealm;
import com.qf.qauth.modules.sys.security.realms.SystemDbAuthenticationRealm;
import com.qf.qauth.modules.sys.security.session.SessionManager;
import com.qf.qauth.modules.sys.security.shiro.ModularRealmAuthenticator;

import net.sf.ehcache.CacheManager;

/**
 * shiro配置类
 * @author MengpingZeng
 *
 */
@Configuration
@PropertySource("classpath:application-shiro.properties")
@ConditionalOnClass({CacheManager.class, LifecycleBeanPostProcessor.class})
@EnableConfigurationProperties(value = {ShiroConfigProperties.class, ContextProperties.class})
public class ShiroConfiguration {

	@Autowired
	private ShiroConfigProperties shiroConfigProperties;
	
	@Autowired
	private ContextProperties contextProperties;
	
	@Autowired
	private EhCacheManagerFactoryBean cacheManagerFactoryBean;

	// oauth密码匹配器
	@Bean(name = "oAuthCredentialsMatcher")
	public OAuthCredentialsMatcher oAuthCredentialsMatcher() {
		return new OAuthCredentialsMatcher();
	}

	// 数据库密码匹配器
	@Bean(name = "dbCredentialsMatcher")
	public HashedCredentialsMatcher dbCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName(shiroConfigProperties.getDbHashAlgorithmName());
		credentialsMatcher.setHashIterations(shiroConfigProperties.getDbHashIterations());
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

	// 数据库认证处理器
	@Bean(name = "sysDbAuthenticationRealm")
	public SystemDbAuthenticationRealm systemDbAuthenticationRealm() {
		SystemDbAuthenticationRealm sysDbAuthenticationRealm = new SystemDbAuthenticationRealm();
		sysDbAuthenticationRealm.setCredentialsMatcher(dbCredentialsMatcher());
		return sysDbAuthenticationRealm;
	}

	// ldap认证处理器
	@Bean(name = "ldapAuthorizingRealm")
	public LdapAuthenticationRealm ldapAuthorizingRealm() {
		return new LdapAuthenticationRealm();
	}

	// 未认证请求过滤器
	@Bean(name = "authcAuthenticationFilter")
	public AuthcAuthenticationFilter authcAuthenticationFilter() {
		AuthcAuthenticationFilter authcAuthenticationFilter = new AuthcAuthenticationFilter();
		authcAuthenticationFilter.setIsLoginUrl(contextProperties.getAdminPath() + contextProperties.getLoginUrl());
		return authcAuthenticationFilter;
	}

	// shiro授权缓存器
	@Bean(name = "shiroCacheManager")
	public EhCacheManager shiroCacheManager() {
		EhCacheManager shiroCacheManager = new EhCacheManager();
		shiroCacheManager.setCacheManager(cacheManagerFactoryBean.getObject());
		return shiroCacheManager;
	}

	@Bean(name = "sessionDAO")
	public CacheSessionDAO sessionDAO() {
		CacheSessionDAO sessionDAO = new CacheSessionDAO();
		sessionDAO.setSessionIdGenerator(new IdGen());
		sessionDAO.setActiveSessionsCacheName(shiroConfigProperties.getActiveSessionsCacheName());
		sessionDAO.setCacheManager(shiroCacheManager());
		return sessionDAO;
	}

	@Bean(name = "sessionIdCookie")
	public SimpleCookie sessionIdCookie() {
		SimpleCookie sessionIdCookie = new SimpleCookie();
		sessionIdCookie.setName(shiroConfigProperties.getSessionCookieName());
		return sessionIdCookie;
	}

	// 设置session管理器
	@Bean(name = "sessionManager")
	public SessionManager sessionManager() {
		SessionManager sessionManager = new SessionManager();
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setGlobalSessionTimeout(shiroConfigProperties.getSessionTimeout());
		sessionManager.setSessionValidationInterval(shiroConfigProperties.getSessionTimeoutClean());
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		sessionManager.setSessionIdCookieEnabled(true);
		return sessionManager;
	}

	// 设置认证策略
	@Bean(name = "authenticator")
	public ModularRealmAuthenticator authenticator() {
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
		return authenticator;
	}

	@Bean
	@DependsOn(value = "lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setAuthenticator(authenticator());
		Collection<Realm> realms = new ArrayList<Realm>();
		realms.add(systemDbAuthenticationRealm()); 				// 添加数据库认证处理器
		realms.add(ldapAuthorizingRealm()); 					// 添加ldap认证处理器
		securityManager.setRealms(realms);
		securityManager.setSessionManager(sessionManager());
		securityManager.setCacheManager(shiroCacheManager());
		return securityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizAttrSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizAttrSourceAdvisor.setSecurityManager(securityManager());
		return authorizAttrSourceAdvisor;
	}

	// shiro过滤器
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		// 设置shiroFilter
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager());
		shiroFilter.setLoginUrl(contextProperties.getAdminPath() + contextProperties.getLoginUrl());
		shiroFilter.setSuccessUrl(contextProperties.getAdminPath());
		Map<String, Filter> filters = shiroFilter.getFilters();
		filters.put("authc", authcAuthenticationFilter());

		// 设置过滤器链
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put(contextProperties.getStaticUrlPath(), "anon");
		filterChainDefinitionMap.put(contextProperties.getAdminPath() + contextProperties.getPermissionsUrl(), "anon");
		/*filterChainDefinitionMap.put(contextProperties.getAdminPath() + "/getNextMenuCode", "anon");*/
		filterChainDefinitionMap.put(contextProperties.getAdminPath() + contextProperties.getLoginUrl(), "authc");
		filterChainDefinitionMap.put(contextProperties.getAdminPath() + contextProperties.getLogoutUrl(), "logout");
		filterChainDefinitionMap.put(contextProperties.getAdminPath() + contextProperties.getAllUrlPath(), "user");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}
	
	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	    DelegatingFilterProxy proxy = new DelegatingFilterProxy();
	    proxy.setTargetFilterLifecycle(true);
	    proxy.setTargetBeanName("shiroFilter");
	    proxy.setTargetFilterLifecycle(true);
	    filterRegistrationBean.setFilter(proxy);
	    return filterRegistrationBean;
	}
}
