package com.qf.qauth.modules.sys.constants;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * 
 * ClassName: Constant <br/>  
 * Function: TODO QAuth常量类. <br/>  
  * date: 2017年3月31日 下午5:46:53 <br/>  
 *  
 * @author YiqiangShen  
 * @version   
 * @since JDK 1.8
 */
public class Constant {
	
	public static final int TOKEN_OT_PERIOD = 5;
	
	public static final int SUCCESS_RESPONSE = 0;
	
	public static final int INVALID_PARAM_RESPONSE = 1;
	
	public static final int ERROR_RESPONSE = 2;
	
	public static final String AD_SEARCH_KEY = "AD_SEARCH_KEY";
	
	public static final String AD_DOMAIN = "quark.com";
	
	public final static String DEFAULT_PERMISSION = "access";
	
	public static final PropertyFilter USER_JSON_FILTER = new PropertyFilter(){  
        @Override  
        public boolean apply(Object object, String name, Object value) {  
            switch (name){
            case "admin" :
            	return false;
            case "currentUser" :
            	return false;
            case "page": 
            	return false;
            case "sqlMap":
            	return false;
            case "dbName":
            	return false;
            case "delFlag":
            	return false;
            case "global":
            	return false;
            case "isNewRecord":
            	return false;
            case "parent":
            	return false;
            case "parentId":
            	return false;
            case "sort":
            	return false;
            case "type":
            	return false;
            case "parentIds":
            	return false;
            case "createBy":
            	return false;
            case "createDate":
            	return false;
            case "loginDate":
            	return false;
            case "loginFlag":
            	return false;
            case "loginIp":
            	return false;
            case "oldLoginDate":
            	return false;
            case "oldLoginIp":
            	return false;
            case "password":
            	return false;
            case "updateDate":
            	return false;
            case "updateBy":
            	return false;
            case "useable":
            	return false;
            default:
            	return true;
            }
        }  
    };  
}
