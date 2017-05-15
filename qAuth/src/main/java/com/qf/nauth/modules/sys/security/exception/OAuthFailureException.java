/**
 * 
 */
package com.qf.nauth.modules.sys.security.exception;

/**
 * @author MengpingZeng
 *
 */
public class OAuthFailureException extends CustomerAuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598728244780396556L;

	public OAuthFailureException(String msg) {
        super(msg);
    }

	public OAuthFailureException(Throwable cause) {
        super(cause);
    }
}
