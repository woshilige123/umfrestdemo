package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;
import com.umftech.api.sample.rest.entify.enums.PaymentMethod;
/********************************
 * @description 付款信息实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class Payer {
	@SerializedName("payment_method")
	private PaymentMethod paymentMethod;//支付方式
	
	@SerializedName("bank_code")
	private String bankCode;			//银行编码
	
	@SerializedName("bank_id")
	private String bankId;				//通道号
	
	@SerializedName("pay_date")
	private String payDate;				//支付日期
	
	private String expand;				//扩展字段
	
	@SerializedName("payer_info")
	private PayerInfo payerInfo;		//付款人详细信息

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public PayerInfo getPayerInfo() {
		return payerInfo;
	}

	public void setPayerInfo(PayerInfo payerInfo) {
		this.payerInfo = payerInfo;
	}

}
