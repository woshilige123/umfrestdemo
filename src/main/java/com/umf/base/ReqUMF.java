package com.umf.base;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.umf.api.payments.BankCard;
import com.umf.api.payments.Payer;
import com.umf.api.payments.PayerInfo;
import com.umf.api.payments.Payment;
import com.umf.api.payments.PaymentMethod;
import com.umf.api.payments.QrCodeScan;
import com.umf.api.payments.Refund;
import com.umf.api.payments.WechatInApp;
import com.umf.api.payments.WechatInAppWeb;
import com.umf.base.rest.APIContext;
import com.umf.base.util.JsonUtil;
import com.umf.base.util.RSAUtils;
import com.umf.base.util.RestUtil;

public class ReqUMF {
	private static final Logger logger = LoggerFactory.getLogger(ReqUMF.class);

	public static String getOauth(APIContext APIcontext) throws Exception {
		Map getOauthMap = new HashMap();
		String url = APIcontext.getOauthUrl();
		String client_secret = APIcontext.getClientSecret();
		String client_id = APIcontext.getClientId();
		getOauthMap.put("url", url);
		getOauthMap.put("type", "POST");
		getOauthMap.put("Content-Type", "application/json");
		getOauthMap.put("Accept", "application/json");
		Map reqBodyMap = new HashMap();
		reqBodyMap.put("grant_type", "client_credentials");
		reqBodyMap.put("client_secret", client_secret);
		reqBodyMap.put("client_id", client_id);
		String json = JsonUtil.MapToJson(reqBodyMap);
		getOauthMap.put("json", json);
		String result = RestUtil.httpRestString(getOauthMap);
		return result;

	}

	public static String post(APIContext APIcontext, Object object)
			throws Exception {
		String crtPath = APIcontext.getCrtPath();
		checkString("crtPath in APIcontext", crtPath);
		String p8Path = APIcontext.getP8Path();
		checkString("p8Path in APIcontext", p8Path);
		String url = APIcontext.getUrl();
		checkString("url in APIcontext", url);
		String type = APIcontext.getType();
		checkString("type in APIcontext", type);
		String token = APIcontext.getToken();
		checkString("token in APIcontext", token);
		Payment payment;
		Refund refund;
		String json = "";
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		RSAUtils rsaUtils = new RSAUtils(crtPath, p8Path);
		if(object instanceof Payment){
			payment = (Payment) object;
			json = checkObject(payment, rsaUtils);
		}else{
			refund = (Refund) object;
			json = gson.toJson(refund);
		}
		Map postMap = new HashMap();
		postMap.put("Content-Type", "application/json");
		postMap.put("url", url);
		postMap.put("type", type);
		postMap.put("json", json);
		token = "Bearer" + token;
		postMap.put("Authorization", token);
		String signature = RSAUtils.createSign(json);
		postMap.put("Signature", signature);
		logger.info("【umf-rest-api-sdk】postMap : " + postMap);
		String result = RestUtil.httpRestString(postMap);
		logger.info("【umf-rest-api-sdk】resultMap : " + result);
		return result;
	}

	public static void checkString(String tip, String param) throws Exception {
		if (null == param || "".equals(param)) {
			throw new Exception("can't find " + tip);
		}
	}

	public static String checkObject(Payment payment, RSAUtils rsaUtils) throws Exception {
		checkObject("payment", payment);
		PaymentMethod paymentMethod = null;
		PayerInfo payerInfo = null;
		Payer payer = null;
		payer = payment.getPayer();
		checkObject("payer in Payment", payer);
		paymentMethod = payer.getPaymentMethod();
		checkObject("paymentMethod in Payment", paymentMethod);
		payerInfo = payer.getPayerInfo();
		checkObject("payerInfo in Payment", payerInfo);
		String number = null;
		String citizen_id_number = null;
		String payer_name = null;
		String valid_date = null;
		String cvv2 = null;
		if (PaymentMethod.CREDIT_CARD == paymentMethod
				|| PaymentMethod.DEBIT_CARD == paymentMethod) {
			BankCard bankCard = payerInfo.getBankCard();
			number = bankCard.getNumber();
			citizen_id_number = bankCard.getCitizenIdNumber();
			payer_name = bankCard.getPayerName();
			valid_date = bankCard.getValidDate();
			cvv2 = bankCard.getCvv2();
			if (number != null && !"".equals(number)) {
				number = rsaUtils.Sensitivity(number);
				bankCard.setNumber(number);
			}
			if (citizen_id_number != null && !"".equals(citizen_id_number)) {
				citizen_id_number = rsaUtils.Sensitivity(citizen_id_number);
				bankCard.setCitizenIdNumber(citizen_id_number);
			}
			if (payer_name != null && !"".equals(payer_name)) {
				payer_name = rsaUtils.Sensitivity(payer_name);
				bankCard.setPayerName(payer_name);
			}
			if (valid_date != null && !"".equals(valid_date)) {
				valid_date = rsaUtils.Sensitivity(valid_date);
				bankCard.setValidDate(valid_date);
			}
			if (cvv2 != null && !"".equals(cvv2)) {
				cvv2 = rsaUtils.Sensitivity(cvv2);
				bankCard.setCvv2(cvv2);
			}
		} else if (paymentMethod == PaymentMethod.WECHAT_SCAN
				|| paymentMethod == PaymentMethod.ALIPAY_SCAN) {
			QrCodeScan qrCodeScan = payerInfo.getQrCodeScan();
			citizen_id_number = qrCodeScan.getCitizenIdNumber();
			payer_name = payerInfo.getName();
			if (citizen_id_number != null && !"".equals(citizen_id_number)) {
				citizen_id_number = rsaUtils.Sensitivity(citizen_id_number);
				qrCodeScan.setCitizenIdNumber(citizen_id_number);
			} else {
				throw new Exception("can't find CitizenIdNumber in payment");
			}
			if (payer_name != null && !"".equals(payer_name)) {
				payer_name = rsaUtils.Sensitivity(payer_name);
				payerInfo.setName(payer_name);
			} else {
				throw new Exception("can't find Name in payment");
			}
		} else if (paymentMethod == PaymentMethod.WECHAT_IN_APP) {
			WechatInApp wechatInApp = payerInfo.getWechatInApp();
			citizen_id_number = wechatInApp.getCitizenIdNumber();
			payer_name = payerInfo.getName();
			if (citizen_id_number != null && !"".equals(citizen_id_number)) {
				citizen_id_number = rsaUtils.Sensitivity(citizen_id_number);
				wechatInApp.setCitizenIdNumber(citizen_id_number);
			} else {
				throw new Exception("can't find CitizenIdNumber in payment");
			}
			if (payer_name != null && !"".equals(payer_name)) {
				payer_name = rsaUtils.Sensitivity(payer_name);
				payerInfo.setName(payer_name);
			} else {
				throw new Exception("can't find Name in payment");
			}
		} else if (paymentMethod == PaymentMethod.WECHAT_WEB) {
			WechatInAppWeb wechatInAppWeb = payerInfo.getWechatInAppWeb();
			citizen_id_number = wechatInAppWeb.getCitizenIdNumber();
			payer_name = payerInfo.getName();
			if (citizen_id_number != null && !"".equals(citizen_id_number)) {
				citizen_id_number = rsaUtils.Sensitivity(citizen_id_number);
				wechatInAppWeb.setCitizenIdNumber(citizen_id_number);
			} else {
				throw new Exception("can't find CitizenIdNumber in payment");
			}
			if (payer_name != null && !"".equals(payer_name)) {
				payer_name = rsaUtils.Sensitivity(payer_name);
				payerInfo.setName(payer_name);
			} else {
				throw new Exception("can't find Name in payment");
			}
		}
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(payment);
	}
	
	public static void checkObject(String tip, Object object) throws Exception {
		if (null == object) {
			throw new Exception("can't find " + tip);
		}
	}

}
