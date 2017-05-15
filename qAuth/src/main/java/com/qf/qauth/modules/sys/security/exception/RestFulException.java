/**
 * 
 */
package com.qf.qauth.modules.sys.security.exception;

/**
 * @author MengpingZeng
 *
 */
public class RestFulException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1162079004729031829L;
	
	public RestFulException(String message) {
        super(message);
    }
	
	public RestFulException(Throwable cause) {
		super(cause);
	}
}
