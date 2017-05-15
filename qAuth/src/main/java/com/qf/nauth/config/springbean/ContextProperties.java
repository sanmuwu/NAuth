/**
 * 
 */
package com.qf.nauth.config.springbean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author MengpingZeng
 *
 */
@Data
@Getter
@Setter
@Component("contextProperties")
@ConfigurationProperties(prefix = "system.context")
public class ContextProperties {

	private String ehcacheConfigFile; // 缓存配置类型

	private String indexUrl; // 首页Url路径

	private String allUrlPath; // 所有Url路径

	private String staticUrlPath; // 静态资源路径

	private String adminPath; // 系统根路径

	private String frontPath;

	private String loginUrl; // 登陆URL

	private String logoutUrl; // 退出Url路径

	private String permissionsUrl; // 权限查询接口

	private String userAndRoleUrl; // 用户信息查询接口

	private String menuListUrl; // 用户授权查询接口
}
