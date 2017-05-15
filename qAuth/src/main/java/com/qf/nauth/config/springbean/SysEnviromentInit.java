package com.qf.nauth.config.springbean;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.qf.nauth.common.persistence.annotation.MyBatisDao;
import com.qf.nauth.config.datasource.MyBatisSqlSessionFactoryConfiguration;

/**
 * mybatis注解扫描配置类.
 *
 * @author zmp
 * 
 */
@Configuration
@AutoConfigureAfter(MyBatisSqlSessionFactoryConfiguration.class)
public class SysEnviromentInit  implements EnvironmentAware {


    private Environment environment;
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(environment.getProperty("mybatis.basePackage"));
        mapperScannerConfigurer.setAnnotationClass(MyBatisDao.class);
        return mapperScannerConfigurer;
    }
	   
	@Bean(name = "lifecycleBeanPostProcessor")
	public  LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
