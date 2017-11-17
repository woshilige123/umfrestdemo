package com.umftech.api.sample.rest.entify.enums;
/********************************
 * @description 付款信息-支付方式枚举类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public enum PaymentMethod {
	CREDIT_CARD(6),
	DEBIT_CARD(7),
	WECHAT(13), 
	ALIPAY(14);
	   
	private int paymentMethod;
	
	private PaymentMethod(int valueInt){
		this.paymentMethod = valueInt;
	}
	
	public int getIntPaymentMethod() {
        return paymentMethod;
    }
	
	/**
	 * 根据整型获取英文支付类型
	 * @param valueInt
	 * @return
	 */
	
	public static PaymentMethod getStringPaymentMethod(int valueInt){ 
		PaymentMethod paymentMethod = CREDIT_CARD;
		switch(valueInt){ 
		case 6: 
			System.out.println("CREDIT_CARD!"); 
			paymentMethod = CREDIT_CARD;
			break;
		case 7: 
			System.out.println("DEBIT_CARD!"); 
			paymentMethod = DEBIT_CARD;
			break;
		case 13: 
			System.out.println("WECHAT!"); 
			paymentMethod = WECHAT;
			break;
		case 14: 
			System.out.println("ALIPAY!"); 
			paymentMethod = ALIPAY;
			break;
		}
		return paymentMethod; 
		
	}

}
