/**
 * 
 */
package com.qf.qauth.modules.sys.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * JWT认证异常基类.
 * 
 * @author MengpingZeng
 *
 */
public abstract class CustomerAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3314323128998763750L;
	
	public CustomerAuthenticationException(String msg) {
        super(msg);
    }

	public CustomerAuthenticationException(Throwable cause) {
        super(cause);
    }
}
