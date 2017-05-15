
package com.qf.nauth.config.datasource;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.qf.nauth.common.persistence.Page;

/**
 * MyBatis的session工厂
 *
 * @author yanweiqin
 * @create 2017/03/14 16:51
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application-mybatis.properties")
@EnableConfigurationProperties(value = {ExtendMybatisProperties.class, MybatisProperties.class})
@AutoConfigureAfter(DataSourceConfiguration.class)
public class MyBatisSqlSessionFactoryConfiguration implements TransactionManagementConfigurer{

    private static final Logger logger = LoggerFactory.getLogger(MyBatisSqlSessionFactoryConfiguration.class);
    
    @Autowired
    private MybatisProperties myBatisProperties;
    
    @Autowired
    @Qualifier(value="extendMybatisProperties")
    private ExtendMybatisProperties extendMyBatisProperties;
    

    @Autowired
    private DataSourceConfiguration dataSource;
    
    @Autowired
    private ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();


    @Bean()
    public SqlSessionFactory sqlSessionFactory() {
    	logger.info("*************************开始构建sqlSessionFactory***********************" + myBatisProperties);
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		try {
			sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
			sqlSessionFactoryBean.setDataSource(dataSource.dataSource());
			sqlSessionFactoryBean.setTypeAliasesSuperType(Class.forName(extendMyBatisProperties.getTypeAliasesSuperType()));
			sqlSessionFactoryBean.setTypeAliasesPackage(myBatisProperties.getTypeAliasesPackage());
			sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource(myBatisProperties.getConfigLocation()));
			// 添加类型别名类
			Class<?>[] typeAliases = new Class<?>[1];
			typeAliases[0] = Page.class;
			sqlSessionFactoryBean.setTypeAliases(typeAliases);
			//添加Resource解析对象
        	sqlSessionFactoryBean.setMapperLocations(resourceLoader.getResources(myBatisProperties.getMapperLocations()[0]));
        	SqlSessionFactory sqlSessionFactory =  sqlSessionFactoryBean.getObject();
        	logger.info("*************************构建sqlSessionFactory成功:" + sqlSessionFactory);
        	return sqlSessionFactory;
        } catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error("创建sqlSessionFactoryBean出错，异常信息如下：\n", e);
        	}
            throw new RuntimeException(e);
        }
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
    	logger.info("*************************声明式事物管理构建开始***********************");
    	DataSourceTransactionManager transactionMgr = new DataSourceTransactionManager(dataSource.dataSource());
    	logger.info("*************************声明式事物管理构建成功***********************");
        return transactionMgr;
    }
}
