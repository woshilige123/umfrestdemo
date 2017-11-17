package com.umftech.api.sample.rest.entify.enums;
/********************************
 * @description 支付信息-订单状态枚举类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public enum PaymentState {
	WAIT_BUYER_PAY(0),
	TRADE_SUCCESS(2) ,
	TRADE_CLOSED(-1)  ,
	TRADE_FAIL (1) ;  
//	,-99-交易创建,
//	0-交易成功,
//	1-交易失败,
//	3-支付中,
//	4-预授权转账
//	-1-交易冲正
	private int PaymentState;
	
	private PaymentState(int valueInt){
		this.PaymentState = valueInt;
	}
	
	public int getIntPaymentState() {
		return PaymentState;
	}
	/**
	 * 根据整型获取英文订单类型
	 * @param valueInt
	 * @return
	 */
	
	public static PaymentState getStringPaymentState(int valueInt){ 
		PaymentState paymentState = WAIT_BUYER_PAY;
		switch(valueInt){ 
		case -99: 
			System.out.println("WAIT_BUYER_PAY!"); 
			paymentState = WAIT_BUYER_PAY;
			break;
		case 0: 
			System.out.println("WAIT_BUYER_PAY!"); 
			paymentState = WAIT_BUYER_PAY;
			break;
		case 1: 
			System.out.println("TRADE_CLOSED!"); 
			paymentState = TRADE_CLOSED;
			break;
		case 2: 
			System.out.println("TRADE_SUCCESS!"); 
			paymentState = TRADE_SUCCESS;
			break;
		case -1: 
			System.out.println("TRADE_FAIL!"); 
			paymentState = TRADE_FAIL;
			break;
		}
		return paymentState; 
		
	}
}
