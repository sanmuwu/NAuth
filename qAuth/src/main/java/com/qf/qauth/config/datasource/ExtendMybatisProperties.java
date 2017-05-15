/**
 * 
 */
package com.qf.qauth.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * mybatis扩展配置类.
 * @author MengpingZeng
 *
 */
@Data
@Setter
@Getter
@Component("extendMybatisProperties")
@ConfigurationProperties(prefix="mybatis", ignoreInvalidFields = true)
public class ExtendMybatisProperties {

	private String typeAliasesSuperType;
}
