package com.qf.nauth.modules.auth.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

import com.qf.nauth.common.utils.StringUtils;


/**
 * 
 * ClassName: MD5Util <br/>  
 * Function: TODO MD5相关. <br/>  
  * date: 2017年3月31日 下午5:47:18 <br/>  
 *  
 * @author YiqiangShen  
 * @version   
 * @since JDK 1.8
 */
public class MD5Util {

	public static String encrypt(String str, String key, String charset) {
    	if(StringUtils.isNotEmpty(key)){
    		str = str + key;
    	}
        return DigestUtils.md5Hex(getContentBytes(str, charset));
    }
    
    public static boolean verify(String encryptStr, String str, String key, String charset) {
    	if(encrypt(str, key, charset).equals(encryptStr)) {
    		return true;
    	}
    	return false;
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5 doesn't support " + charset);
        }
    }
}
