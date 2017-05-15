package com.qf.qauth.common.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTSigner.Options;
import com.auth0.jwt.JWTVerifier;

public class JWTUtils {
	
	/**
	 * 使用分配的RSA私钥加密生成token字符串.
	 * 
	 * @throws Exception
	 */
	public static <T> String sign(RSAPrivateKey privateKey, Map<String, Object> claims) throws Exception {
		final JWTSigner signer = new JWTSigner(privateKey);
		Options options = new Options();
		//使用分配的私钥进行rsa加密
		options.setAlgorithm(Algorithm.RS256);
		return signer.sign(claims, options);
	}
	
	
	/**
	 * 使用RSA公钥解密token获取claims内容并返回.
	 * 
	 */
	public static Map<String, Object> unsign(String token, RSAPublicKey publicKey) throws Exception {
		final JWTVerifier verifier = new JWTVerifier(publicKey);
		final Map<String,Object> claims= verifier.verify(token);
		return claims;
	}
}
