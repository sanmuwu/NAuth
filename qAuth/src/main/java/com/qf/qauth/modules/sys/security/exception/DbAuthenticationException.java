/**
 * 
 */
package com.qf.qauth.modules.sys.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 数据库认证异常.
 * 
 * @author MengpingZeng
 *
 */
public class DbAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4753582495015001817L;

	public DbAuthenticationException(String msg) {
        super(msg);
    }

	public DbAuthenticationException(Throwable cause) {
        super(cause);
    }
}
