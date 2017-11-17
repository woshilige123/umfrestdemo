package com.umftech.api.sample.rest.entify;

import java.util.List;
import com.umf.api.payments.Meta;

/********************************
 * @description 返回商户信息实体类
 * @author lixiaohe
 * @param <T>
 * @date 20170320
 ********************************/
public class Response {
	private Meta meta; 			//meta：表示响应的基本信息，包括返回码，返回信息，分页信息，签名
	private Payment payment; 	//result: 表示响应的相关业务实体
	private Refund refund; 		//result: 表示响应的相关业务实体
	private List<Link> links;//Links: 这些links是对当前对象实现各种操作的URL
	private List<CustomsDeclaration> customsDeclarations;//result: 表示响应的相关业务实体
	private List<ExchangeRate> exchangeRates;//result: 表示响应的相关业务实体
	private List<Refund> refunds;//result: 表示响应的相关业务实体
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}
	
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	
	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public List<CustomsDeclaration> getCustomsDeclarations() {
		return customsDeclarations;
	}

	public void setCustomsDeclarations(List<CustomsDeclaration> customsDeclarations) {
		this.customsDeclarations = customsDeclarations;
	}

	public List<ExchangeRate> getExchangeRates() {
		return exchangeRates;
	}

	public void setExchangeRates(List<ExchangeRate> exchangeRates) {
		this.exchangeRates = exchangeRates;
	}

	public List<Refund> getRefunds() {
		return refunds;
	}

	public void setRefunds(List<Refund> refunds) {
		this.refunds = refunds;
	}
	
}
