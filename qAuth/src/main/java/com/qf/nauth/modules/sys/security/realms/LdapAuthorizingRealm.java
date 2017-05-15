/**
 * 
 */
package com.qf.nauth.modules.sys.security.realms;


import java.io.Serializable;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.ldap.UnsupportedAuthenticationMechanismException;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qf.nauth.modules.auth.constant.Adconfigs;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MengpingZeng
 *
 */
@Service
@Getter
@Setter
public class LdapAuthorizingRealm extends JndiLdapRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String rootDN;
	
	@Autowired
	private Adconfigs adconfigs;
	
	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		logger.info("进入LDAP认证方法，请求参数：{}", token);
		AuthenticationInfo info;  
        try {  
            info = queryForAuthenticationInfo(token, getContextFactory());  
        } catch (AuthenticationNotSupportedException e) {  
            String msg = "Unsupported configured authentication mechanism";  
            throw new UnsupportedAuthenticationMechanismException(msg, e);  
        } catch (javax.naming.AuthenticationException e) {  
            String msg = "LDAP authentication failed.";  
            throw new AuthenticationException(msg, e);  
        } catch (NamingException e) {  
            String msg = "LDAP naming error while attempting to authenticate user.";  
            throw new AuthenticationException(msg, e);  
        } catch (UnknownAccountException e) {  
            String msg = "UnknownAccountException";  
            throw new UnknownAccountException(msg, e);  
        } catch (IncorrectCredentialsException e) {  
            String msg = "IncorrectCredentialsException";  
            throw new IncorrectCredentialsException(msg, e);  
        }  
  
        return info;  
	}

	@Override  
    protected AuthenticationInfo queryForAuthenticationInfo(  
            AuthenticationToken token, LdapContextFactory ldapContextFactory)  
            throws NamingException {  
  
        Object principal = token.getPrincipal();  
        Object credentials = token.getCredentials();  
  
        LdapContext systemCtx = null;  
        LdapContext ctx = null;  
        try {  
            /*
            systemCtx = ldapContextFactory.getSystemLdapContext();  
  
            SearchControls constraints = new SearchControls();  
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);  
            NamingEnumeration results = systemCtx.search(rootDN, "cn=" + principal, constraints);  
            if (results != null && !results.hasMore()) {  
                throw new UnknownAccountException();  
            } else {  
                while (results.hasMore()) {  
                    SearchResult si = (SearchResult) results.next();  
                    principal = si.getName() + "," + rootDN;  
                }  
				logger.info("DN=" + principal);  
                try {  
                    ctx = ldapContextFactory.getLdapContext(principal,  
                            credentials);  
                } catch (NamingException e) {  
                    throw new IncorrectCredentialsException();  
                }  
                return createAuthenticationInfo(token, principal, credentials,  
                        ctx);  
            }  
            */
        	systemCtx = ldapContextFactory.getLdapContext(principal + adconfigs.getDomain(), credentials);
        	return new SimpleAuthenticationInfo(new Principal(principal.toString(), new String((char[])credentials)),
        			token.getCredentials(), getName());
        } finally {  
            LdapUtils.closeContext(systemCtx);  
            LdapUtils.closeContext(ctx);  
        }  
    }
	
	public static class Principal implements Serializable {
		private String principal;
		private String credential;
		
		public Principal(String _principal, String _credential) {
			principal = _principal;
			credential = _credential;
		}
		
		public String getPrincipal() {
			return principal;
		}
		
		public String getCredential() {
			return credential;
		}
		
	}
}
