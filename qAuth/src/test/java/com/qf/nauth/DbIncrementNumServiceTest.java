/**
 * 
 */
package com.qf.nauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.qf.nauth.modules.sys.service.DbIncrementNumService;

/**
 * @author MengpingZeng
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan({"com.qf.nauth"})
@EnableAutoConfiguration
@AutoConfigureJson
@AutoConfigureJsonTesters
public class DbIncrementNumServiceTest {
	
	@Autowired
	private DbIncrementNumService incrNumservice;
	
	@Test
	public void testGetNextNum() throws Exception {
		int currNo = incrNumservice.getNextNum("menu_code");
		System.out.println("菜单编码: " + currNo);
	}
}
