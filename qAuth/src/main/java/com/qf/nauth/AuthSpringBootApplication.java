package com.qf.nauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Activiti启动类
 *
 * @author YanweiQin
 * @create 2017/03/08 14:31
 */
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@ComponentScan({"com.qf.nauth"})
@EnableAutoConfiguration
public class AuthSpringBootApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuthSpringBootApplication.class);
	}
/*	
	@Bean  
    public EmbeddedServletContainerFactory servletContainer() {  
        JettyEmbeddedServletContainerFactory factory =  
                new JettyEmbeddedServletContainerFactory();  
        return factory;  
    }  */

    public static void main(String[] args) {
        SpringApplication.run(AuthSpringBootApplication.class, args);
    }
}
