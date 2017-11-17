package com.umf.api.payments;

import com.google.gson.annotations.SerializedName;

/********************************
 * @description 退费信息实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class Refund {
	private String id;
	@SerializedName("refund_info")
	private RefundInfo refundInfo;			//退费订单信息
	
	@SerializedName("state")
	private RefundState state;			//退费状态
	
	@SerializedName("notify_url")
	private String refundNotifyUrl;		//退费金额信息
	
	private String parent_payment;
	
	public String getId() {
		return id;	
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent_payment() {
		return parent_payment;
	}

	public void setParent_payment(String parent_payment) {
		this.parent_payment = parent_payment;
	}

	public RefundInfo getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(RefundInfo refundInfo) {
		this.refundInfo = refundInfo;
	}

	
	public RefundState getState() {
		return state;
	}

	public void setState(RefundState state) {
		this.state = state;
	}
	
	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}

	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}
}
