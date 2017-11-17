package com.umf.api.payments;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Order
{

  @SerializedName("mer_reference_id")
  private String merReferenceId;

  @SerializedName("mer_date")
  private String merDate;
  private Amount amount;

  @SerializedName("order_summary")
  private String orderSummary;

  @SerializedName("expire_time")
  private String expireTime;

  @SerializedName("user_ip")
  private String userIp;

  @SerializedName("sub_orders")
  private List<SubOrder> subOrders;
  
  @SerializedName("sub_mer_id")
  private String subMerId;

  public String getMerReferenceId()
  {
    return this.merReferenceId;
  }

  public void setMerReferenceId(String merReferenceId) {
    this.merReferenceId = merReferenceId;
  }

  public String getMerDate() {
    return this.merDate;
  }

  public void setMerDate(String merDate) {
    this.merDate = merDate;
  }

  public Amount getAmount() {
    return this.amount;
  }

  public void setAmount(Amount amount) {
    this.amount = amount;
  }

  public String getOrderSummary() {
    return this.orderSummary;
  }

  public void setOrderSummary(String orderSummary) {
    this.orderSummary = orderSummary;
  }

  public String getExpireTime() {
    return this.expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = expireTime;
  }

  public String getUserIp() {
    return this.userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public List<SubOrder> getSubOrders() {
    return this.subOrders;
  }

  public void setSubOrders(List<SubOrder> subOrders) {
    this.subOrders = subOrders;
  }

  public String getSubMerId() {
    return this.subMerId;
  }

  public void setSubMerId(String subMerId) {
    this.subMerId = subMerId;
  }
}