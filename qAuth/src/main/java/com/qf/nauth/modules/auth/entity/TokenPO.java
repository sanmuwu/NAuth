package com.qf.nauth.modules.auth.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ClassName: TokenPO <br/>  
 * Function: TODO AD域凭证PO对象. <br/>  
  * date: 2017年3月31日 下午5:45:14 <br/>  
 *  
 * @author YiqiangShen  
 * @version   
 * @since JDK 1.8
 */
@Repository
@Getter
@Setter
public class TokenPO {
	
	private String cid;
	private String token;
	private Date creTime;
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("【cid=").append(cid).append(", ")
			.append("token=").append(token).append(", ")
			.append("creTime=").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(creTime))
			.append("】");
		return sb.toString();
	}
}
