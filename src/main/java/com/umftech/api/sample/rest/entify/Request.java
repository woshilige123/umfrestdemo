package com.umftech.api.sample.rest.entify;

/********************************
 * @description 返回商户信息实体类
 * @author lixiaohe
 * @param <T>
 * @date 20170320
 ********************************/
public class Request {
	private Payer payer; 			//meta：表示响应的基本信息，包括返回码，返回信息，分页信息，签名
	private Order order; 	//result: 表示响应的相关业务实体
	private String notify_url; 		//result: 表示响应的相关业务实体
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Order getOrder() {
		return order;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	public Payer getPayer() {
		return payer;
	}

	public void setPayer(Payer payer) {
		this.payer = payer;
	}
	
}
