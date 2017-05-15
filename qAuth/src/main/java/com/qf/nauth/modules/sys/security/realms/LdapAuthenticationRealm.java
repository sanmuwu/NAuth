/**
 * 
 */
package com.qf.nauth.modules.sys.security.realms;

import java.io.Serializable;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;

import com.qf.nauth.modules.sys.security.Principal;
import com.qf.nauth.modules.sys.security.exception.OAuthFailureException;

import lombok.Getter;
import lombok.ToString;

/**
 * ldap认证实现类.
 * @author MengpingZeng
 *
 */
@EnableConfigurationProperties
public class LdapAuthenticationRealm extends AuthorizingRealm {
	
    @Value("${spring.ldap.cn.suffix}")
	private String cnSuffix = "@quark1.com";
    
    @Value("${spring.ldap.urls}")
    private String url = "ldap://quark.com:389";
    
    @Value("${spring.ldap.base}")
    private String base = "ou=QuarkFinance,dc=quark,dc=com";

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 添加用户权限
		info.addStringPermission("user");
		return info;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken ldapToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) ldapToken;
		LdapContextSource contextSource = new LdapContextSource();  
		contextSource.setUrl(url);  
		contextSource.setBase(base);  
		
		contextSource.setUserDn(token.getUsername() + cnSuffix);
		contextSource.setPassword(new String(token.getPassword()));
		contextSource.setPooled(false);  
		contextSource.afterPropertiesSet(); // important  
		  
		LdapTemplate template = new LdapTemplate();  
		template.setContextSource(contextSource);  
		
		LdapQuery query = LdapQueryBuilder.query()
			.where("objectCategory").is("person")
			.and("objectClass").is("user")
			.and("userPrincipalName").is(token.getUsername() + cnSuffix);
		try {
			// 进行LDAP域用户、密码检验
			template.authenticate(query, new String(token.getPassword())); 
			List<String> userList = template.search(
					query, new AttributesMapper<String>() {
	            public String mapFromAttributes(Attributes attrs)
	               throws NamingException {
	               return attrs.get("userPrincipalName").get().toString();
	            }
	         });
			if(CollectionUtils.isEmpty(userList)) {
				throw new OAuthFailureException("用户不存在.");
			} else {
				return new SimpleAuthenticationInfo(new LdapPrincipal(token, false), ldapToken.getCredentials(), getName());
			}
		} catch(Exception e) {
			throw new OAuthFailureException("用户不存在.");
		}
	}

	/**
	 * AD域认证用户信息.
	 */
	@ToString(of = "aDname", callSuper = true)
	@Getter
	public static class LdapPrincipal extends Principal implements Serializable {
		
		private static final long serialVersionUID = 7621884952854371915L;
		
		private String loginName;							// 登陆名
		
		private String aDname; 								// 域用户名
		
		public LdapPrincipal(UsernamePasswordToken token, boolean mobileLogin) {
			super(token, mobileLogin);
			this.loginName = token.getUsername();
			this.aDname = token.getUsername();
		}
	}
}
