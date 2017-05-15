/**
 * 
 */
package com.qf.qauth.common.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.qf.qauth.modules.sys.constants.AuthParamConstants;

/**
 * 
 *
 * RSA公钥/私钥/签名工具包
 * 
 * @author MengpingZeng
 *
 */
public class RSAUtils {
 
    /** 
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
    	 //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(AuthParamConstants.getKeyAlgorithmParam());
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(AuthParamConstants.getPublicKeyParam(), publicKey);
        keyMap.put(AuthParamConstants.getPrivateKeyParam(), privateKey);
        return keyMap;
    }
 
    /**
     * <p>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(AuthParamConstants.getKeyAlgorithmParam());
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > AuthParamConstants.getMaxDecryptBlockParam()) {
                cache = cipher.doFinal(encryptedData, offSet, AuthParamConstants.getMaxDecryptBlockParam());
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * AuthParamConstants.getMaxDecryptBlockParam();
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /**
     * <p>
     * 公钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(AuthParamConstants.getKeyAlgorithmParam());
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > AuthParamConstants.getMaxDecryptBlockParam()) {
                cache = cipher.doFinal(encryptedData, offSet, AuthParamConstants.getMaxDecryptBlockParam());
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * AuthParamConstants.getMaxDecryptBlockParam();
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(AuthParamConstants.getKeyAlgorithmParam());
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > AuthParamConstants.getMaxEncryptBlockParam()) {
                cache = cipher.doFinal(data, offSet, AuthParamConstants.getMaxEncryptBlockParam());
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * AuthParamConstants.getMaxEncryptBlockParam();
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    /**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(AuthParamConstants.getKeyAlgorithmParam());
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > AuthParamConstants.getMaxEncryptBlockParam()) {
                cache = cipher.doFinal(data, offSet, AuthParamConstants.getMaxEncryptBlockParam());
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * AuthParamConstants.getMaxEncryptBlockParam();
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    /**
     * <p>
     * 获取私钥
     * </p>
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(AuthParamConstants.getPrivateKeyParam());
        return Base64Utils.encode(key.getEncoded());
    }
 
    /**
     * <p>
     * 获取公钥
     * </p>
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(AuthParamConstants.getPublicKeyParam());
        return Base64Utils.encode(key.getEncoded());
    }
 
    public static void main(String[] args) {
    	 Map<String, Object> keyMap;
         try {
             keyMap = genKeyPair();
             String publicKey = getPublicKey(keyMap);
             System.out.println(publicKey);
             String privateKey = getPrivateKey(keyMap);
             System.out.println(privateKey);
             String orgString = "123";
             byte[] data = orgString.getBytes();
             
             byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);            
             String encryptString = new String(encodedData);            
  
             byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
             String decryptString = new String(decodedData);
  
             System.out.println("orginalString:"+orgString);
             System.out.println("encrypString:"+encryptString);
             System.out.println("decryptString:"+decryptString); 
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 
}
