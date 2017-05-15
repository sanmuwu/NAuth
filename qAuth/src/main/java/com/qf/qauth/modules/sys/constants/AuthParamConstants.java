/**
 * 
 */
package com.qf.qauth.modules.sys.constants;

import com.qf.qauth.common.config.Global;
import com.qf.qauth.common.utils.StringUtils;

import lombok.NoArgsConstructor;

/**
 * @author MengpingZeng
 *
 */
@NoArgsConstructor
@SuppressWarnings("static-access")
public final class AuthParamConstants {
	
	private static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";			
	
	private static final String DEFAULT_USERNAME_PARAM = "username";
	
	private static final String DEFAULT_PASSWORD_PARAM = "password";
	
	private static final String DEFAULT_AUTHORIZATION_CODE_PARAM = "auth_code";
	
	private static final String DEFAULT_CLIENT_SECRET_PARAM = "client_secret";
	
	private static final String DEFAULT_CLIENTID_PARAM = "client_id";
	
	private static final String DEFAULT_ACCESS_TOKEN_PARAM = "access_token";
	
	private static final String DEFAULT_EXPIRES_IN_PARAM = "expires_in";
	
	private static final String DEFAULT_REFRESH_TOKEN_PARAM = "refresh_token";

	private static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	
	private static final String DEFAULT_MOBILELOGIN_PARAM = "mobileLogin";
	
	private static final String DEFAULT_GRANTYPE_PARAM = "grant_type";
	
	private static final String DEFAULT_KEY_ALGORITHM = "RSA";                      	  // JWT Toekgn加密算法RSA

    private static final String DEFAULT_PUBLIC_KEY = "publicKey";                 
    
    private static final String DEFAULT_PRIVATE_KEY = "privateKey";             
    
    private static final  String DEFAULT_AUTH_CACHE_PREFIX = "Auth_";
    
    private static final String DEFAULT_CACHED_AUTH_TOKEN_PARAM = "_AuthToken";		  // 缓存的授权码
    
    private static final  String DEFAULT_THRD_SYS_NAME = "_appName";					  // 缓存第三方系统名称								
    
    private static final String DEFAULT_CACHED_RSA_PUBLIC_KEY = "_RSAPublicKey";          // 获取公钥的KEY
    
    private static final String DEFAULT_CACHED_RSA_PRIVATE_KEY = "_RSAPrivateKey";        // 获取私钥的KEY
    
    private static final String DEFAULT_CACHED_ACCESS_TOKEN_PARAM = "_AccessToken";	  	//访问token
    
    private static final String DEFAULT_CACHED_REFRESH_TOKEN_PARAM = "_RefreshToken";	  //刷新访问token的token
    
	private static final String DEFAULT_EXP_PARAM = "exp";
	
	private static final String DEFAULT_IAT_PARAM = "iat";
	
	private static final int DEFAULT_MAX_ENCRYPT_BLOCK = 117;                   //RSA最大加密明文大小
	
	private static final int DEFAULT_MAX_DECRYPT_BLOCK = 128;                   //RSA最大解密密文大小
	
	private static final String DEFAULT_AUD_PARAM = "aud";

	private static final String DEFAULT_RESULT_PARAM = "result";
	
	
	/**
	 * @return the rememberMeparam
	 */
 	public static String getRememberMeparam() {
		String rememberMe = (String) Global.getInstance().getConfig("shiro.rememberme.param");
		return StringUtils.isBlank(rememberMe)? DEFAULT_REMEMBER_ME_PARAM : rememberMe;
	}

	/**
	 * @return the userNameParam
	 */
	public static String getUserNameParam() {
		String userName = (String) Global.getInstance().getConfig("shiro.username.param");
		return StringUtils.isBlank(userName)? DEFAULT_USERNAME_PARAM : userName;
	}

	/**
	 * @return the passwordparam
	 */
	public static String getPasswordparam() {
		String password = (String) Global.getInstance().getConfig("shiro.password.param");
		return StringUtils.isBlank(password)? DEFAULT_PASSWORD_PARAM : password;
	}

	/**
	 * @return the appnameparam
	 */
	public static String getAuthorizationCodeParam() {
		String appName = Global.getInstance().getConfig("auth.authCode.param");
		return StringUtils.isBlank(appName)? DEFAULT_AUTHORIZATION_CODE_PARAM : appName;
	}

	/**
	 * @return the emailparam
	 */
	public static String getClientSecretParam() {
		String email= Global.getInstance().getConfig("auth.clientSecret.param");
		return StringUtils.isBlank(email)? DEFAULT_CLIENT_SECRET_PARAM : email;
	}

	/**
	 * @return the clientidparam
	 */
	public static String getClientIdParam() {
		String clientId = Global.getInstance().getConfig("auth.clientId.param");
		return StringUtils.isBlank(clientId)? DEFAULT_CLIENTID_PARAM : clientId;
	}

	/**
	 * @return the authtokenparam
	 */
	public static String getAccessTokenParam() {
		String authToken = Global.getInstance().getConfig("auth.accessToken.param");
		return StringUtils.isBlank(authToken)? DEFAULT_ACCESS_TOKEN_PARAM : authToken;
	}

	/**
	 * @return the captchaparam
	 */
	public static String getCaptchaParam() {
		String captcha = Global.getInstance().getConfig("shiro.captcha.param");
		return StringUtils.isBlank(captcha)? DEFAULT_CAPTCHA_PARAM : captcha;
	}

	/**
	 * @return the mobileloginparam
	 */
	public static String getMobileLoginParam() {
		String mobileLogin = Global.getInstance().getConfig("shiro.mobilelogin.param");
		return StringUtils.isBlank(mobileLogin)? DEFAULT_MOBILELOGIN_PARAM : mobileLogin;
	}

	/**
	 * @return the authCachePrefix
	 */
	public static String getAuthCachePrefixParam() {
		String authCachePrefix = Global.getInstance().getConfig("auth.cachePrefix.param");
		return StringUtils.isBlank(authCachePrefix)? DEFAULT_AUTH_CACHE_PREFIX: authCachePrefix;
	}

	/**
	 * @return the thrdSysName
	 */
	public static String getThrdSysNameParam() {
		String thrdSysName = Global.getInstance().getConfig("jwt.thrdSysName.param");
		return StringUtils.isBlank(thrdSysName)? DEFAULT_THRD_SYS_NAME: thrdSysName;
	}

	/**
	 * @return the keyAlgorithm
	 */
	public static String getKeyAlgorithmParam() {
		String keyAlgorthm = Global.getInstance().getConfig("jwt.keyAlgorthm.param");
		return StringUtils.isBlank(keyAlgorthm)? DEFAULT_KEY_ALGORITHM: keyAlgorthm;
	}

	/**
	 * @return the rsaPublicKey
	 */
	public static String getCachedRSAPublicKeyParam() {
		String rsaPublickey = Global.getInstance().getConfig("jwt.cachedRsaPublickey.param");
		return StringUtils.isBlank(rsaPublickey)? DEFAULT_CACHED_RSA_PUBLIC_KEY: rsaPublickey;
	}

	/**
	 * @return the rsaPrivateKey
	 */
	public static String getCachedRSAPrivateKeyParam() {
		String rsaPrivatekey = Global.getInstance().getConfig("jwt.cachedRsaPrivatekey.param");
		return StringUtils.isBlank(rsaPrivatekey)? DEFAULT_CACHED_RSA_PRIVATE_KEY: rsaPrivatekey;
	}
	
	/**
	 * 
	 * @return access_token
	 */
	public static String getCachedAccessTokenParam() {
		String rsaPrivatekey = Global.getInstance().getConfig("jwt.cachedAccessToken.param");
		return StringUtils.isBlank(rsaPrivatekey)? DEFAULT_CACHED_ACCESS_TOKEN_PARAM: rsaPrivatekey;
	}

	/**
	 * @return the publicKey
	 */
	public static String getPublicKeyParam() {
		String publickey = Global.getInstance().getConfig("jwt.publickey.param");
		return StringUtils.isBlank(publickey)? DEFAULT_PUBLIC_KEY: publickey;
	}

	/**
	 * @return the privateKey
	 */
	public static String getPrivateKeyParam() {
		String privatekey = Global.getInstance().getConfig("jwt.privatekey.param");
		return StringUtils.isBlank(privatekey)? DEFAULT_PRIVATE_KEY: privatekey;
	}

	/**
	 * @return the exp
	 */
	public static String getExpParam() {
		String exp = Global.getInstance().getConfig("jwt.exp.param");
		return StringUtils.isBlank(exp)? DEFAULT_EXP_PARAM: exp;
	}

	/**
	 * @return the iat
	 */
	public static String getIatParam() {
		String iat = Global.getInstance().getConfig("jwt.iat.param");
		return StringUtils.isBlank(iat)? DEFAULT_IAT_PARAM: iat;
	}

	/**
	 * @return the maxEncryptBlock
	 */
	public static int getMaxEncryptBlockParam() {
		Integer maxEncryptBlock = Integer.valueOf(Global.getInstance().getConfig("jwt.maxEncryptBlock.param"));
		return maxEncryptBlock > 0? DEFAULT_MAX_ENCRYPT_BLOCK: maxEncryptBlock;
	}

	/**
	 * @return the maxDecryptBlock
	 */
	public static int getMaxDecryptBlockParam() {
		Integer maxDecryptBlock = Integer.valueOf(Global.getInstance().getConfig("jwt.maxDecrptBlock.param"));
		return maxDecryptBlock > 0? DEFAULT_MAX_DECRYPT_BLOCK: maxDecryptBlock;
	}
	

	/**
	 * @return the aud
	 */
	public static String getAudParam() {
		String aud = Global.getInstance().getConfig("jwt.aud.param");
		return StringUtils.isBlank(aud)? DEFAULT_AUD_PARAM: aud;
	}
	
	/**
	 * 
	 * @return result.
	 */
	public static String getResultParam() {
		String result = Global.getInstance().getConfig("jwt.result.param");
		return StringUtils.isBlank(result)? DEFAULT_RESULT_PARAM: result;
	}
	
	/**
	 * 
	 * @return authToken
	 */
	public static String getCachedAuthTokenParam() {
		String authToken = Global.getInstance().getConfig("auth.cachedAuthToken.param");
		return StringUtils.isBlank(authToken)? DEFAULT_CACHED_AUTH_TOKEN_PARAM: authToken;
	}
	
	/**
	 * 
	 * @return grantType
	 */
	public static String getGrantTypeParam() {
		String grantType = Global.getInstance().getConfig("auth.grantType.param");
		return StringUtils.isBlank(grantType)? DEFAULT_GRANTYPE_PARAM: grantType;
	}

	/**
	 * @return the expires_in
	 */
	public static String getExpiresInParam() {
		String authToken = Global.getInstance().getConfig("auth.expiresIn.param");
		return StringUtils.isBlank(authToken)? DEFAULT_EXPIRES_IN_PARAM : authToken;
	}
	
	/**
	 * @return the refresh_token
	 */
	public static String getRefreshTokenParam() {
		String authToken = Global.getInstance().getConfig("auth.refreshToken.param");
		return StringUtils.isBlank(authToken)? DEFAULT_REFRESH_TOKEN_PARAM : authToken;
	}
	/**
	 * 
	 * @return cachedRefreshToken
	 */
	public static String getCachedRefreshTokenParam() {
		String rsaPrivatekey = Global.getInstance().getConfig("jwt.cachedRefreshToken.param");
		return StringUtils.isBlank(rsaPrivatekey)? DEFAULT_CACHED_REFRESH_TOKEN_PARAM: rsaPrivatekey;
	}
}
