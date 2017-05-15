/**
 * 
 */
package com.qf.nauth.config.springbean;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.qf.nauth.common.mapper.JsonMapper;
import com.qf.nauth.modules.sys.interceptor.LogInterceptor;


/**
 * spring mvc配置扩展类
 * @author MengpingZeng
 *
 */
@Configuration
@EnableWebMvc
@EnableConfigurationProperties
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {
	
	@Value("${spring.mvc.view.prefix}")
	private String viewPrefix;
	
	@Value("${spring.mvc.view.suffix}")
	private String viewSuffix;
	
	@Value("${spring.mvc.static-path-pattern}")
	private String staticPathPattern;
	
	@Value("${spring.resources.static-locations}")
	private String staticLocations;
	
	@Value("${spring.resources.cache-period}")
	private int cachePeriod;
	
	@Autowired
	private ContextProperties contextProperties;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new LogInterceptor()).addPathPatterns(contextProperties.getAdminPath() + contextProperties.getAllUrlPath())
			.excludePathPatterns(contextProperties.getAdminPath() + contextProperties.getIndexUrl())
			.excludePathPatterns(contextProperties.getAdminPath() + contextProperties.getLoginUrl());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addRedirectViewController(contextProperties.getIndexUrl(), contextProperties.getAdminPath());
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		 registry.addResourceHandler(staticPathPattern).addResourceLocations(staticLocations).setCachePeriod(cachePeriod);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		registry.jsp(viewPrefix, viewSuffix);
	}
	

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
		mediaTypes.put("xml", MediaType.APPLICATION_XML);
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		
		configurer.mediaTypes(mediaTypes);
		configurer.ignoreAcceptHeader(true)
			.favorParameter(true);
	}
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		StringHttpMessageConverter stringMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		
		// 设置json mapper转换器
		MappingJackson2HttpMessageConverter jsonMapperConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		jsonMapperConverter.setSupportedMediaTypes(mediaTypes);
		
		jsonMapperConverter.setPrettyPrint(false);
		jsonMapperConverter.setObjectMapper(new JsonMapper());
		
		converters.add(stringMessageConverter);
		converters.add(jsonMapperConverter);
	}

	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
            	container.addErrorPages(new ErrorPage(UnauthorizedException.class, "/error/403"));
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
                container.addErrorPages(new ErrorPage(java.lang.Throwable.class,"/error/500"));
            }
        };
    }

	@Bean
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty(HttpStatus.NOT_FOUND.toString(), "error/404");
		exceptionResolver.setExceptionMappings(mappings);
		return exceptionResolver;
	}
    
    @Bean
    public RequestContextListener requestContextLister() {
    	return new RequestContextListener();
    }
}
