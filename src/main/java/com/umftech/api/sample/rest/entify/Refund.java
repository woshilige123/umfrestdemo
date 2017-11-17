package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;
import com.umftech.api.sample.rest.entify.enums.RefundState;
/********************************
 * @description 退费信息实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class Refund {
	@SerializedName("refund_no")
	private String refundNo;			//退费流水号
	
	@SerializedName("refund_amount")
	private Amount refundAmount;		//退费金额信息
	
	@SerializedName("refund_state")
	private RefundState refundState;	//退费状态
	
	@SerializedName("refund_notify_url")
	private Amount refundNotifyUrl;		//退费金额信息
	
	@SerializedName("orig_order")
	private Order origOrder;			//原订单信息
	
	@SerializedName("orig_payment_id")
	private String origPaymentId;		//原交易流水号
	
	public Order getOrigOrder() {
		return origOrder;
	}

	public void setOrigOrder(Order origOrder) {
		this.origOrder = origOrder;
	}

	public String getOrigPaymentId() {
		return origPaymentId;
	}

	public void setOrigPaymentId(String origPaymentId) {
		this.origPaymentId = origPaymentId;
	}
	
	public RefundState getRefundState() {
		return refundState;
	}

	public void setRefundState(RefundState refundState) {
		this.refundState = refundState;
	}
	
	public Amount getRefundNotifyUrl() {
		return refundNotifyUrl;
	}

	public void setRefundNotifyUrl(Amount refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}

	private Order order;				//订单信息
	
	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public Amount getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Amount refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
