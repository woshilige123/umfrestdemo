package com.umf.api.payments;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/********************************
 * @description 退费信息实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class RefundInfo {
	@SerializedName("mer_reference_id")
	private String merReferenceId;		//订单号商户生产保证交易唯一性的ID
	
	@SerializedName("mer_date")
	private String merDate;				//商户订单日期
	
	private Amount amount;				//付款金额对象
	
	@SerializedName("refund_summary")
	private String refundSummary;		//订单简要说明

	@SerializedName("sub_orders")
	private List<SubOrder> subOrders;	//子订单信息对象
	
	public String getMerReferenceId() {
		return merReferenceId;
	}

	public void setMerReferenceId(String merReferenceId) {
		this.merReferenceId = merReferenceId;
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

	public List<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

}
