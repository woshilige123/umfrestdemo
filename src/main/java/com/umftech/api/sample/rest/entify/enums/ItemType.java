package com.umftech.api.sample.rest.entify.enums;
/********************************
 * @description 交易明细-商品类型枚举类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public enum ItemType {
	CLOTHING(1)  ,		//1-服装
	FOOD(2)      ,		//2-食品
	ELECTRONIC(3),		//3-电子产品
	OTHER(4)	 ;		//4-其他
	
	private int itemType;
	
	private ItemType(int value){
		this.itemType = value;
	}
	
	public int getItemTypeToInt() {
        return itemType;
    }
}
