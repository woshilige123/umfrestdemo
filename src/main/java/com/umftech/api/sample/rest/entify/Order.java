package com.umftech.api.sample.rest.entify;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/********************************
 * @description 订单实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class Order {
	@SerializedName("mer_reference_id")
	private String merReferenceId;		//订单号商户生产保证交易唯一性的ID
	
	@SerializedName("mer_id")
	private String merId;				//订单号商户ID

	@SerializedName("mer_date")
	private String merDate;				//商户订单日期
	
	private Amount amount;				//付款金额对象
	
	@SerializedName("order_summary")
	private String orderSummary;		//订单简要说明

	@SerializedName("expire_time")
	private String expireTime;			//过期时间
	
	@SerializedName("mer_priv")
	private String merPriv;				//商户私有域
	
	@SerializedName("user_ip")
	private String userIp;				//下单IP

	@SerializedName("bank_payurl")
	private String bankPayurl;			//二维码链接
	
	@SerializedName("sub_orders")
	private List<SubOrder> subOrders;	//子订单信息对象
	
	public String getMerReferenceId() {
		return merReferenceId;
	}

	public void setMerReferenceId(String merReferenceId) {
		this.merReferenceId = merReferenceId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(String orderSummary) {
		this.orderSummary = orderSummary;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getMerPriv() {
		return merPriv;
	}

	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getBankPayurl() {
		return bankPayurl;
	}

	public void setBankPayurl(String bankPayurl) {
		this.bankPayurl = bankPayurl;
	}
	
	public List<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

}
