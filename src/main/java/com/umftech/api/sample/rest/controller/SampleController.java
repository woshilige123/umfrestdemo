/*
 * file name   : SampleController.java 
 * <br>copyright   : Copyright (c) 2017
 * <br>description : rest api sample demo controller
 * <br>modified    : 
 * @author      <a href="mailto:zhangming@umpay.com">Thomas Zhang</a>
 * @version     1.0
 * @date        2017-7-6 8:47:53 PM
 */

package com.umftech.api.sample.rest.controller;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import com.umf.api.payments.Link;
import com.umf.api.payments.Meta;
import com.umf.api.payments.Payment;
import com.umf.api.payments.PaymentMethod;
import com.umf.api.payments.Refund;
import com.umf.base.ReqUMF;
import com.umf.base.rest.APIContext;
import com.umf.base.util.RSAUtils;
import com.umf.base.util.RestUtil;
import com.umftech.api.sample.rest.util.APIContextHelper;
import com.umftech.api.sample.rest.util.TokenKeeper;

/*************************************************************************
 * description : 
 * @author <a href="mailto:zhangming@umpay.com">Thomas Zhang</a>
 * @date 2017-7-6 8:47:53PM
 * @version 1.0
 *************************************************************************/

@RestController
@RequestMapping(value = "/payments", produces = "text/plain;charset=UTF-8")
public class SampleController{
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	private String openidUrl;

	private String baseUrl;

	private String paymentUrl;

	private String bankUrl;

	private String certPath;

	private String keyPath;

	public String getKeyPath() {
		return keyPath;
	}

	@Value("${cert.mer.key.private}")
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getCertPath() {
		return certPath;
	}

	@Value("${cert.umf.key.public}")
	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getOpenidUrl() {
		return openidUrl;
	}

	@Value("${server.umf.url.openid}")
	public void setOpenidUrl(String openidUrl) {
		this.openidUrl = openidUrl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	@Value("${server.umf.url.base}")
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	@Value("${server.umf.url.payment}")
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public String getBankUrl() {
		return bankUrl;
	}

	@Value("${server.umf.url.bank}")
	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}
	
	private String requestUMF(String url, String reqBody ,Type typeOfT){
		Gson gson = new Gson();
		// 1.payment
		APIContext apiContext = APIContextHelper.getAPIContext();
		apiContext.setUrl(url);
		apiContext.setType("POST");
		Payment payment = gson.fromJson(reqBody, typeOfT);
		String result = "";

		try {
			logger.info(String.format("apiContext url:%s", apiContext.url));
			logger.info(String.format("apiContext crtPath:%s", apiContext.crtPath));
			logger.info(String.format("apiContext p8Path:%s", apiContext.p8Path));
			logger.info(String.format("apiContext token:%s", apiContext.token));
			logger.info(String.format("payment: %s", reqBody));
			result = ReqUMF.post(apiContext, payment);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
			Map<String, HashMap<String, String>> retMap = new HashMap<>();
			Map<String, String> mataMap = new HashMap<>();
			mataMap.put("ret_msg", e1.getMessage());
			retMap.put("meta", (HashMap<String, String>) mataMap);
			result = new Gson().toJson(retMap);
		}
		return result;
	}

	@RequestMapping(value = "/createPayment", method = { RequestMethod.POST })
	@ResponseBody
	public String createPayment(HttpServletRequest req, @RequestBody String reqBody) {

		Gson gson = new Gson();
		// General the request URL
		String url = baseUrl + paymentUrl;
		
		String result = requestUMF(url,reqBody,Payment.class);
		
		// Get the return information
		Type type = new TypeToken<Map<String, JsonElement>>() {}.getType();
		Map<String, JsonElement> resultMap = gson.fromJson(result, type);
		logger.info(String.format("resultMap: %s", result));
		Meta meta = gson.fromJson(resultMap.get("meta"), Meta.class);

		// create payment successfully && payment type is bankcard
		Payment payment = gson.fromJson(reqBody, Payment.class);
		if("0000".equals(meta.getRetCode()) && (PaymentMethod.CREDIT_CARD == payment.getPayer().getPaymentMethod() || PaymentMethod.DEBIT_CARD==payment.getPayer().getPaymentMethod())){
			// get the verify code API url from the result of createPayment API
			Link[] linksArr = gson.fromJson(resultMap.get("links"), Link[].class);
			
			result=requestUMF(linksArr[1].getHref(),reqBody,Payment.class);
		}
		return result;
	}

	
	@RequestMapping(value = "/createRefund", method = { RequestMethod.POST })
	@ResponseBody
	public String createRefund(HttpServletRequest req, @RequestBody String reqBody) {

		Gson gson = new Gson();
		Refund refund = gson.fromJson(reqBody, Refund.class);
		// General the request URL
		String url = baseUrl + paymentUrl + "/" + refund.getParent_payment() + "/refund";
		
		APIContext apiContext = APIContextHelper.getAPIContext();
		apiContext.setUrl(url);
		apiContext.setType("POST");
		String result = "";

		try {
			logger.info(String.format("apiContext url:%s", apiContext.url));
			logger.info(String.format("apiContext crtPath:%s", apiContext.crtPath));
			logger.info(String.format("apiContext p8Path:%s", apiContext.p8Path));
			logger.info(String.format("apiContext token:%s", apiContext.token));
			logger.info(String.format("payment: %s", reqBody));
			result = ReqUMF.post(apiContext, refund);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
			Map<String, HashMap<String, String>> retMap = new HashMap<>();
			Map<String, String> mataMap = new HashMap<>();
			mataMap.put("ret_msg", e1.getMessage());
			retMap.put("meta", (HashMap<String, String>) mataMap);
			result = new Gson().toJson(retMap);
		}

		return result;
	
	}
	
	/**
	 * confirmPayment Bankcard
	 * 
	 * @param req
	 * @param reqBody
	 * @return
	 */
	@RequestMapping(value = "/executePayment", method = { RequestMethod.POST })
	@ResponseBody
	public String confirmPaymentOfBankcard(HttpServletRequest req, @RequestBody String reqBody) {
		JsonElement jelement = new JsonParser().parse(reqBody);
		JsonObject jObject = jelement.getAsJsonObject();
		String url = jObject.get("url").toString().replace("\"", "");
		return requestUMF(url, jObject.get("payment").toString(), Payment.class);
	}


}
