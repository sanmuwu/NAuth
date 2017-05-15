/**
 * 
 */
package com.qf.nauth.config.springbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.qf.nauth.common.servlet.ValidateCodeServlet;

/**
 * @author MengpingZeng
 *
 */
@Configuration
@EnableConfigurationProperties
public class SpringContextConfiguration {
	
	@Autowired
	private ContextProperties contextProperties;
	
    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    @Bean(name="validator")
    public LocalValidatorFactoryBean validator() {
    	return new LocalValidatorFactoryBean();
    }
	
	@Bean(name = "cacheManager")
	public EhCacheManagerFactoryBean cacheManager() {
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(resourceLoader.getResource(contextProperties.getEhcacheConfigFile()));
		return ehCacheManagerFactoryBean;
	}
	
	@Bean
	public FilterRegistrationBean siteMeshFilterRegistration() {
	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	    SiteMeshFilter siteMeshFilter = new SiteMeshFilter();
	    filterRegistrationBean.setFilter(siteMeshFilter);
	    filterRegistrationBean.addUrlPatterns(contextProperties.getAdminPath() + "/*");
	    filterRegistrationBean.addUrlPatterns(contextProperties.getFrontPath() + "/*");
	    return filterRegistrationBean;
	}
	
	@Bean
    public ServletRegistrationBean validateCodeServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ValidateCodeServlet());
        registration.addUrlMappings("/servlet/validateCodeServlet");
        return registration;
    }
}
