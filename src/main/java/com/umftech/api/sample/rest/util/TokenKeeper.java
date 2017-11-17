package com.umftech.api.sample.rest.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.impl.AvalonLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.google.gson.Gson;
import com.umftech.api.sample.rest.entify.AccessToken;

import com.umf.base.ReqUMF;
import com.umf.base.rest.APIContext;

@Configuration
@ComponentScan(basePackages = { "com.umftech.api.sample.*" })
@PropertySources({
    @PropertySource("/WEB-INF/conf/cert.conf"),
    @PropertySource("/WEB-INF/conf/demo-app.conf")
})
public class TokenKeeper {

	private static final Logger logger = LoggerFactory.getLogger(TokenKeeper.class);
	private static String CLIENT_ID;
	private static String CLIENT_SECRET;
	private static String baseUrl;
	private static String oauthUrl;
	
	private static String accessToken;
	private static long vaildUntil;
	
	private static TokenKeeper tokenKeeper=null;
	
	@Autowired
    private ApplicationContext ctx;

	public static String getToken(){
		return getToken(false);
	}
	public static String getToken(boolean renew){
		if(vaildUntil - System.currentTimeMillis() <1000 || renew){
			apiReloadToken();
		}
		return accessToken;
	}
	
	private static void apiReloadToken(){
		APIContext apiContext=new APIContext(); 
		String url = baseUrl+oauthUrl;
		apiContext.setOauthUrl(url);
		apiContext.setClientId(CLIENT_ID);
		apiContext.setClientSecret(CLIENT_SECRET);
		String result="";
		try {
			logger.info(String.format("apiContext oauthUrl:%s", apiContext.oauthUrl));
			result = ReqUMF.getOauth(apiContext);
			Gson gson=new Gson();
			AccessToken token = gson.fromJson(result, AccessToken.class);
			accessToken = token.getToken();
			logger.info(String.format("apiContext token:%s", accessToken));
			vaildUntil = System.currentTimeMillis() + token.getExpiresIn()*1000;
		} catch (Exception e2) {
			logger.error(e2.getMessage());
		}
		
	}
	
	private static void reloadToken() {
		Map<String, String> header = new HashMap<>();
		Map<String, String> param = new HashMap<>();
		String url = "http://"+baseUrl+oauthUrl;
		
		header.put("content-type", "application/json");
		
		param.put("client_id", CLIENT_ID);
		param.put("client_secret", CLIENT_SECRET);
		param.put("grant_type", "client_credentials");
		
		Gson gson = new Gson();
		String json = gson.toJson(param);
		
		String result = HttpClientUtil.doPost(url, header, json);
		
		//System.out.println(result);
		AccessToken token = gson.fromJson(result, AccessToken.class);
		accessToken = token.getToken();
		vaildUntil = System.currentTimeMillis() + token.getExpiresIn()*1000;
		
	}
	public static String getClientId() {
		return CLIENT_ID;
	}
	
	@Value("${oauth.client.id}")
	public void setClientId(String clientId) {
		TokenKeeper.CLIENT_ID = clientId;
	}
	public static String getClientSecret() {
		return CLIENT_SECRET;
	}
	
	@Value("${oauth.client.secret}")
	public void setClientSecret(String clientSecret) {
		TokenKeeper.CLIENT_SECRET = clientSecret;
	}
	public static String getAccessToken() {
		return accessToken;
	}
	public static void setAccessToken(String accessToken) {
		TokenKeeper.accessToken = accessToken;
	}
	public static String getBaseUrl() {
		return baseUrl;
	}
	
	@Value("${server.umf.url.base}")
	public void setBaseUrl(String baseUrl) {
		TokenKeeper.baseUrl = baseUrl;
	}
	public static String getOauthUrl() {
		return oauthUrl;
	}
	
	@Value("${server.umf.url.oauth}")
	public void setOauthUrl(String oauthUrl) {
		TokenKeeper.oauthUrl = oauthUrl;
	}
	
	
	
	

}
