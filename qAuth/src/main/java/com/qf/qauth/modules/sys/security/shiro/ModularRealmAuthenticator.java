/**
 * 
 */
package com.qf.qauth.modules.sys.security.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MengpingZeng
 *
 */
public class ModularRealmAuthenticator extends org.apache.shiro.authc.pam.ModularRealmAuthenticator {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override  
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {  

          
        AuthenticationStrategy strategy = getAuthenticationStrategy();  
        
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        if (logger.isTraceEnabled()) {
        	logger.trace("Iterating through {} realms for PAM authentication", realms.size());
        }
        for (Realm realm : realms) {  
            aggregate = strategy.beforeAttempt(realm, token, aggregate);  
            if (realm.supports(token)) {  
                AuthenticationInfo info = null;  
                Throwable t = null;  
                info = realm.getAuthenticationInfo(token);  
                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);  
                // dirty dirty hack  
                if (aggregate != null && !CollectionUtils.isEmpty(aggregate.getPrincipals())) {  
                    return aggregate;  
                }  
                // end dirty dirty hack  
            } else {  
  
            }  
        }  
        aggregate = strategy.afterAllAttempts(token, aggregate);  
        return aggregate;  
    }
 
}
