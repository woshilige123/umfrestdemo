package com.umftech.api.sample.rest.controller;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.umftech.api.sample.rest.entify.ExchangeRate;
import com.umftech.api.sample.rest.util.HttpClientUtil;
import com.umftech.api.sample.rest.util.TokenKeeper;
@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
@PropertySources({
    @PropertySource("/WEB-INF/conf/cert.conf"),
    @PropertySource("/WEB-INF/conf/demo-app.conf")
})
public class GetInfoController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	private String baseUrl;
	
	private String exchangeUrl;
	
	private String bankUrl;

	private String paymentUrl;

	private String refundUrl;

	private String openidUrl;
	
	private String reconciliationUrl;
	
	private String transactionsUrl;
	
	
	public String getTransactionsUrl() {
		return transactionsUrl;
	}
	@Value("${server.umf.url.transactions}")
	public void setTransactionsUrl(String transactionsUrl) {
		this.transactionsUrl = transactionsUrl;
	}
	
	public String getReconciliationUrl() {
		return reconciliationUrl;
	}
	@Value("${server.umf.url.reconciliation}")
	public void setReconciliationUrl(String reconciliationUrl) {
		this.reconciliationUrl = reconciliationUrl;
	}
	
	public String getOpenidUrl() {
		return openidUrl;
	}
	@Value("${server.umf.url.openid}")
	public void setOpenidUrl(String openidUrl) {
		this.openidUrl = openidUrl;
	}

	public String getRefundUrl() {
		return refundUrl;
	}
	@Value("${server.umf.url.refund}")
	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}
	public String getPaymentUrl() {
		return paymentUrl;
	}
	@Value("${server.umf.url.payment}")
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	@Value("${server.umf.url.base}")
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public String getExchangeUrl() {
		return exchangeUrl;
	}
	@Value("${server.umf.url.exchange}")
	public void setExchangeUrl(String exchangeUrl) {
		this.exchangeUrl = exchangeUrl;
	}
	
	public String getBankUrl() {
		return bankUrl;
	}
	@Value("${server.umf.url.bank}")
	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}
	
	@RequestMapping(value = "/getExchangeRate", method = RequestMethod.POST)
	@ResponseBody
	public String getExchangeRate(HttpServletRequest req, @RequestBody String reqBody){
		Map<String, String> header = new HashMap<>();
		Gson gson = new Gson();
		
		String url = baseUrl + exchangeUrl;
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> param = gson.fromJson(reqBody, type);
		
		header.put("Authorization", "Bearer " + TokenKeeper.getToken());
		
		//String json = gson.toJson(param);
		
		String result = HttpClientUtil.doGet(url, header, param);
		
		ExchangeRate testObj = gson.fromJson(result, ExchangeRate.class);
		if(null != testObj.getRate()){
			return gson.toJson(testObj);
		}
		
		return result;
	}

	@RequestMapping(value = "/getBankList", method = RequestMethod.POST)
	@ResponseBody
	public String getBankList(HttpServletRequest req, @RequestBody String reqBody){
		Map<String, String> header = new HashMap<>();
		Gson gson = new Gson();
		
		String url = baseUrl + bankUrl;
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> param = gson.fromJson(reqBody, type);
		
		header.put("Authorization", "Bearer " + TokenKeeper.getToken());
		
		//String json = gson.toJson(param);
		
		String result = HttpClientUtil.doGet(url, header, param);
		
		return result;
	}
	

	@RequestMapping(value = "/checkPaymentStatus", method = RequestMethod.POST)
	@ResponseBody
	public String checkPaymentStatus(HttpServletRequest req, @RequestBody String reqBody){
		Map<String, String> header = new HashMap<>();
		Gson gson = new Gson();
		JsonElement element = new JsonParser().parse(reqBody);
		JsonObject jObeject = element.getAsJsonObject();
		//String url = gson.fromJson(jObeject.get("url"), String.class);
		String url = baseUrl + paymentUrl + "/" + gson.fromJson(jObeject.get("payment_id"), String.class);
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		//Map<String, String> param = gson.fromJson(reqBody, type);
		Map<String, String> param = new HashMap<>();
		
		header.put("Authorization", "Bearer " + TokenKeeper.getToken());
		
		//String json = gson.toJson(param);
		
		String result = HttpClientUtil.doGet(url, header, param);
		
		return result;
	}

	@RequestMapping(value = "/checkRefundStatus", method = RequestMethod.POST)
	@ResponseBody
	public String checkRefundStatus(HttpServletRequest req, @RequestBody String reqBody){
		Map<String, String> header = new HashMap<>();
		Gson gson = new Gson();

		JsonElement element = new JsonParser().parse(reqBody);
		JsonObject jObeject = element.getAsJsonObject();
		//String url = gson.fromJson(jObeject.get("url"), String.class);
		String url = baseUrl + refundUrl + "/" + gson.fromJson(jObeject.get("refund_id"), String.class);
		
		
		
		
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> param = gson.fromJson(reqBody, type);
		
		header.put("Authorization", "Bearer " + TokenKeeper.getToken());
		
		//String json = gson.toJson(param);
		
		String result = HttpClientUtil.doGet(url, header, param);
		
		return result;
	}


	@RequestMapping(value = "/querytransStatement", method = RequestMethod.POST)
	@ResponseBody
	public String querytransStatement(@RequestBody String reqBody){
		Map<String, String> header = new HashMap<>();
		Gson gson = new Gson();
		JsonElement element = new JsonParser().parse(reqBody);
		String url = baseUrl + transactionsUrl;
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> param = gson.fromJson(reqBody, type);
		
		header.put("Authorization", "Bearer " + TokenKeeper.getToken());
		
		String result = HttpClientUtil.doGet(url, header, param);
		
		return result;
	}
	
	@RequestMapping(value = "/queryReconStatement", method = RequestMethod.POST)
	@ResponseBody
	public String queryReconStatement(@RequestBody String reqBody){
		Map<String, String> header = new HashMap<>();
		Gson gson = new Gson();
		JsonElement element = new JsonParser().parse(reqBody);
		String url = baseUrl + reconciliationUrl;
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> param = gson.fromJson(reqBody, type);
		
		header.put("Authorization", "Bearer " + TokenKeeper.getToken());
		
		String result = HttpClientUtil.doGet(url, header, param);
		
		return result;
	}
	
	@RequestMapping(value = "/getOpenID", method = {RequestMethod.POST})
	@ResponseBody
	public String getOpenID(HttpServletRequest req, @RequestBody String reqBody){  
		
		Gson gson = new Gson(); 
		
		String url = baseUrl + openidUrl;		

		Map<String, String> header = new HashMap<>();
		
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		
		Map<String, String> param = gson.fromJson(reqBody, type);
		
		header.put("Authorization", "Bearer " + TokenKeeper.getToken());
		
		//String json = gson.toJson(param);
		
		String result = HttpClientUtil.doGet(url, header, param);
		return result;
	}
}
