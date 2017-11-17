/*
 * file name   : APIContextHelper.java 
 * <br>copyright   : Copyright (c) 2017
 * <br>description : 负责获取APIContext信息
 * <br>modified    : 
 * @author      <a href="mailto:zhangming@umpay.com">Thomas Zhang</a>
 * @version     1.0
 * @date        2017年7月6日 下午12:25:22
 */ 

package com.umftech.api.sample.rest.util;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import com.umf.base.rest.APIContext;

/*************************************************************************
 * description : 负责获取APIContext信息
 * @author      <a href="mailto:zhangming@umpay.com">Thomas Zhang</a>
 * @date        2017年7月6日 下午12:25:22
 * @version     1.0             
 *************************************************************************/
@Configuration
@ComponentScan(basePackages = { "com.umftech.api.sample.*" })
@PropertySources({
    @PropertySource("/WEB-INF/conf/cert.conf"),
    @PropertySource("/WEB-INF/conf/demo-app.conf")
})
public class APIContextHelper{

	/**
	 * 这里我们封装每次API请求所需要的信息
	 */
	private static APIContext API_CONTEXT=new APIContext();
	
	/**
	 * @return 见{@link #API_CONTEXT} 
	 */
	public static APIContext getAPIContext() {
		String token=TokenKeeper.getToken();
		APIContextHelper.API_CONTEXT.setToken(token);
		APIContextHelper.API_CONTEXT.setCrtPath(SignUtil.UMF_PUBLIC_KEY);
		APIContextHelper.API_CONTEXT.setP8Path(SignUtil.PRIVATE_KEY);
		return API_CONTEXT;
	}
	/**
	 * @param aPI_CONTEXT 见{@link #API_CONTEXT} 
	 */
	public static void setAPIContext(APIContext aPI_CONTEXT) {
		APIContextHelper.API_CONTEXT = aPI_CONTEXT;
	}
}
