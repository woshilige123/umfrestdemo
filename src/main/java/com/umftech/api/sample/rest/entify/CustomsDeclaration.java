package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;
import com.umftech.api.sample.rest.entify.enums.CustomsDeclarationState;

/**
 * 海关报关实体
 * @author fengjian
 *
 */
public class CustomsDeclaration {
	@SerializedName("sub_order")
	private SubOrder subOrder ;//报送的订单相关信息
	private String 	id;//该ID由平台生成，生成规则：sub_reference_id 加 mer_date生成。需可以反算
	@SerializedName("customs_id")
	private String	customsId;//海关通道编码。 报关时的海关通道编码(如HZ，NB，GZ)分别代表杭州，宁波，广州
	@SerializedName("mer_customs_code")
	private String	merCustomsCode;//商户的海关编号
	@SerializedName("freight_amount")
	private Amount 	freightAmount;//运费对象，类型为amount.
	@SerializedName("tax_amount")
	private Amount	taxAmount ;//税款，出于国际化的考虑，所有金额均采用amount对象
	@SerializedName("sub_order_amount")
	private Amount	subOrderAmount;//订单金额
	@SerializedName("ec_plat_id")
	private String	ecPlatId;//电商平台备案编号
	@SerializedName("notify_url")
	private String	notifyUrl;//结果通知的URL（商户侧）
	private CustomsDeclarationState	State;//ACCEPTED - 未报送|SUBMITED - 已报送待处理|SUCCESS - 成功|FAIL - 失败
	@SerializedName("customs_clearance_date")
	private String	customsClearanceDate;//报关日期，该信息由平台返回时填写，商户不用填写
	@SerializedName("acquiring_by_ump")
	private String	acquiringByUMP;//是否联动付款


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomsId() {
		return customsId;
	}
	public void setCustomsId(String customsId) {
		this.customsId = customsId;
	}
	public String getMerCustomsCode() {
		return merCustomsCode;
	}
	public void setMerCustomsCode(String merCustomsCode) {
		this.merCustomsCode = merCustomsCode;
	}
	public String getEcPlatId() {
		return ecPlatId;
	}
	public void setEcPlatId(String ecPlatId) {
		this.ecPlatId = ecPlatId;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public CustomsDeclarationState getState() {
		return State;
	}
	public void setState(CustomsDeclarationState state) {
		State = state;
	}
	public String getCustomsClearanceDate() {
		return customsClearanceDate;
	}
	public void setCustomsClearanceDate(String customsClearanceDate) {
		this.customsClearanceDate = customsClearanceDate;
	}
	public SubOrder getSubOrder() {
		return subOrder;
	}
	public void setSubOrder(SubOrder subOrder) {
		this.subOrder = subOrder;
	}
	
	public Amount getFreightAmount() {
		return freightAmount;
	}
	public void setFreightAmount(Amount freightAmount) {
		this.freightAmount = freightAmount;
	}
	public Amount getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Amount taxAmount) {
		this.taxAmount = taxAmount;
	}
	public Amount getSubOrderAmount() {
		return subOrderAmount;
	}
	public void setSubOrderAmount(Amount subOrderAmount) {
		this.subOrderAmount = subOrderAmount;
	}
	
	public String getAcquiringByUMP() {
		return acquiringByUMP;
	}
	public void setAcquiringByUMP(String acquiringByUMP) {
		this.acquiringByUMP = acquiringByUMP;
	}
	
}
