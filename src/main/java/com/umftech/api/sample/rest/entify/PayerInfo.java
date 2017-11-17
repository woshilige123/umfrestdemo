package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;

/********************************
 * @description 付款人详细信息实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class PayerInfo {
	@SerializedName("card_id")
	private String cardId;			//银行卡号
	
	@SerializedName("identity_code")
	private String identityCode;	//证件号码
	
	@SerializedName("identity_type")
	private String identityType;	//证件类型
	
	@SerializedName("card_holder")
	private String cardHolder;		//持卡人姓名
	
	@SerializedName("valid_date")
	private String validDate;		//有效期
	
	private String cvv2;			//CVV2
	
	@SerializedName("media_id")
	private String mediaId;			//媒介值
	
	@SerializedName("media_type")
	private String mediaType;		//媒介类型

	@SerializedName("last_four_cardId")
	private String lastFourCardId;	//手机号
	
	@SerializedName("agreement_id")
	private String agreementId;		//支付协议号
	
	@SerializedName("busi_agreement_id")
	private String busiAgreementId;	//业务协议号
	
	@SerializedName("mer_cust_id")
	private String merCustId;		//商户用户标识

	@SerializedName("mer_cust_name")
	private String merCustName;		//商户用户标识
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public String getLastFourCardId() {
		return lastFourCardId;
	}

	public void setLastFourCardId(String lastFourCardId) {
		this.lastFourCardId = lastFourCardId;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public String getBusiAgreementId() {
		return busiAgreementId;
	}

	public void setBusiAgreementId(String busiAgreementId) {
		this.busiAgreementId = busiAgreementId;
	}

	public String getMerCustId() {
		return merCustId;
	}

	public void setMerCustId(String merCustId) {
		this.merCustId = merCustId;
	}
	
	public String getMerCustName() {
		return merCustName;
	}

	public void setMerCustName(String merCustName) {
		this.merCustName = merCustName;
	}

	
}
