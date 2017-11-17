package com.umftech.api.sample.rest.entify.enums;
/********************************
 * @description 退费信息-退费状态枚举类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public enum RefundState {
	
	REFUND_SUCCESS(6)	,//退费成功	ret_code=0000
	REFUND_CLOSE(3)	,//退费关闭	退款失败返回状态
	REFUND_PROCESS(7)	,//退费处理中	ret_code=00060861&ret_msg=财务打款中 为退款请求发起成功
	REFUND_FAIL(5)	,//退费失败	当退费由于网络等诸多因素造成超时时，会返回此状态。
	REFUND_ORIGINAL(-99),//初始
	REFUND_NOTNEED(4),//无需退费
	REFUND_PAY_NOT_SUCCESS(8);//退单不付
	

	private int refundState;
	
	private RefundState(int valueInt){
		this.refundState = valueInt;
	}
	
	public int getIntRefundState() {
		return refundState;
	}
	/**
	 * 根据整型获取英文退费状态类型
	 * @param valueInt
	 * @return
	 */
	
	public static RefundState getStringRefundState(int valueInt){ 
		RefundState refundState = REFUND_PROCESS;
		switch(valueInt){ 
		case 6: 
			System.out.println("REFUND_SUCCESS!"); 
			refundState = REFUND_SUCCESS;
			break;
		case 3: 
			System.out.println("REFUND_CLOSE!"); 
			refundState = REFUND_CLOSE;
			break;
		case 7: 
			System.out.println("REFUND_PROCESS!"); 
			refundState = REFUND_PROCESS;
			break;
		case 5: 
			System.out.println("REFUND_FAIL!"); 
			refundState = REFUND_FAIL;
			break;
		}
		return refundState; 
		
	}
}
