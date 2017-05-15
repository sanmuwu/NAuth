/**
 * 
 */
package com.qf.nauth.config.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix="apache.shiro",ignoreInvalidFields = true)
public class ShiroConfigProperties {

	private String dbHashAlgorithmName;			// 数据库认证加密方式
	
	private int dbHashIterations;				// 迭代计算次数
	
	private long sessionTimeout;				// 会话超时时间 
	
	private long sessionTimeoutClean;			// 孤立会话清除时间
	
	private String activeSessionsCacheName;		// 活动会话缓存名称
	
	private String sessionCookieName;			// Shiro指定本系统SESSIONID避免与容器中的冲突
}
