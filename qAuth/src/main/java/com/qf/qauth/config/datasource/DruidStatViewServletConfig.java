package com.qf.qauth.config.datasource;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * Druid监控配置
 *
 * @author zmp
 * 
 */

@SuppressWarnings("serial")
@WebServlet(urlPatterns="/druid/*",initParams = {@WebInitParam(name="resetEnable",value = "false")})
public class DruidStatViewServletConfig extends StatViewServlet{
}
