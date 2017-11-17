package com.umftech.api.sample.rest.entify;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/********************************
 * @description 子订单实体类
 * @author fengjian
 * @date 20170306
 ********************************/
public class SubOrder {

	@SerializedName("sub_mer_reference_id")
	private String subMerReferenceId;	//商户子订单号
	
	@SerializedName("sub_trans_code")
	private String subTransCode;		//子订单业务编码
	
	@SerializedName("sub_trans_type")
	private String subTransType;		//子订单业务类型

	
	@SerializedName("sub_order_amt")
	private Amount subOrderAmt;			//子订单金额
	
	@SerializedName("is_customs")
	private String isCustoms;			//是否报关
	
	private List<Item> items ; 			//交易明细，具体见item对象的说明
	
	
	public String getSubMerReferenceId() {
		return subMerReferenceId;
	}
	public void setSubMerReferenceId(String subMerReferenceId) {
		this.subMerReferenceId = subMerReferenceId;
	}
	public String getSubTransCode() {
		return subTransCode;
	}
	public void setSubTransCode(String subTransCode) {
		this.subTransCode = subTransCode;
	}
	public String getSubTransType() {
		return subTransType;
	}
	public void setSubTransType(String subTransType) {
		this.subTransType = subTransType;
	}
	public Amount getSubOrderAmt() {
		return subOrderAmt;
	}
	public void setSubOrderAmt(Amount subOrderAmt) {
		this.subOrderAmt = subOrderAmt;
	}
	public String getIsCustoms() {
		return isCustoms;
	}
	public void setIsCustoms(String isCustoms) {
		this.isCustoms = isCustoms;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
	
