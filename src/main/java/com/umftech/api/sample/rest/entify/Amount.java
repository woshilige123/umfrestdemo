package com.umftech.api.sample.rest.entify;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.SerializedName;

/********************************
 * @description 金额实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
@Component("Amount") 				
public class Amount {
	private String total;			//总金额,单位元,最多支持小数点后两位的数字
	
	private String currency;		//货币类型
	
	@SerializedName("total_cny")
	private String totalCny;		//人民币金额,单位元,最多支持小数点后两位的数字
	
	@SerializedName("exchange_rate")
	private ExchangeRate exchangeRate;	//汇率
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTotalCny() {
		return totalCny;
	}
	public void setTotalCny(String totalCny) {
		this.totalCny = totalCny;
	}
	public ExchangeRate getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(ExchangeRate exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	

}
