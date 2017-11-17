package com.umftech.api.sample.rest.entify;

import com.google.gson.Gson;

/**
 * 汇率实体
 * @author fengjian
 *
 */
public class ExchangeRate {
	
	private String currency;//币种
	private String rate;//汇率
	
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;

	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;

	}
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
