package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;
import com.umftech.api.sample.rest.entify.enums.PaymentState;
/********************************
 * @description 支付信息实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class Payment {
	private Payer payer;				//付款信息
	
	private Order order;				//订单信息
	
	@SerializedName("payment_id")
	private String paymentId;			//交易流水号
	
	@SerializedName("notify_url")
	private String notifyUrl;			//结果后台通知

	@SerializedName("ret_url")
	private String retUrl;				//结果前台通知
	
	@SerializedName("risk_info")
	private RiskInfo riskInfo;			//风控对象
	
	@SerializedName("mer_check_date")
	private String merCheckDate;		//对账日期
	
	@SerializedName("verify_code")
	private String verifyCode;			//短信验证码
	
	@SerializedName("payment_state")
	private PaymentState paymentState;	//交易状态
	
	@SerializedName("pay_elements")
	private String payElements;			//支付要素结合
	
	@SerializedName("settle_date")
	private String settleDate;			//对账日期
	
	@SerializedName("pay_seq")
	private String paySeq;				//银行流水
	
	@SerializedName("error_code")
	private String errorCode;			//交易错误码

	@SerializedName("open_id")
	private String openId;				//公众号使用！
	
	public Payer getPayer() {
		return payer;
	}
	public void setPayer(Payer payer) {
		this.payer = payer;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public RiskInfo getRiskInfo() {
		return riskInfo;
	}
	public void setRiskInfo(RiskInfo riskInfo) {
		this.riskInfo = riskInfo;
	}
	public String getMerCheckDate() {
		return merCheckDate;
	}
	public void setMerCheckDate(String merCheckDate) {
		this.merCheckDate = merCheckDate;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public PaymentState getPaymentState() {
		return paymentState;
	}
	public void setPaymentState(PaymentState paymentState) {
		this.paymentState = paymentState;
	}
	public String getPayElements() {
		return payElements;
	}
	public void setPayElements(String payElements) {
		this.payElements = payElements;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getPaySeq() {
		return paySeq;
	}
	public void setPaySeq(String paySeq) {
		this.paySeq = paySeq;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
