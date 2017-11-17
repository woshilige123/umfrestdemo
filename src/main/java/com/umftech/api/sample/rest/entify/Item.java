package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;
import com.umftech.api.sample.rest.entify.enums.ItemType;

/********************************
 * @description 交易明细实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class Item {
	@SerializedName("item_id")
	private String itemId;	//商品编号
	
	@SerializedName("item_type")
	private ItemType itemType;		//商品类型
	
	@SerializedName("item_name")
	private String itemName;		//商品名称
	
	@SerializedName("item_quantity")
	private String itemQuantity;	//商品数量
	
	@SerializedName("item_description")
	private String itemDescription;	//商品描述
	
	@SerializedName("item_amount")
	private Amount itemAmount;		//商品总金额对象
	
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Amount getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Amount itemAmount) {
		this.itemAmount = itemAmount;
	}
}
