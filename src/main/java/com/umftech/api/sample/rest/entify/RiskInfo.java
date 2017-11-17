package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;

/********************************
 * @description 风控信息实体类
 * @author lixiaohe
 * @date 20170306
 ********************************/
public class RiskInfo {
	@SerializedName("trans_type")
	private String B0002;				//B0002  交易类型  		01充值、02消费	Y
	
	@SerializedName("receiver_name")
	private String B0003;				//B0003	收货人姓名		String	接收收货信息的人的姓名，，当D0008为1或3时，该字段必填	C
	
	@SerializedName("receiver_moblie_id")
	private String B0004;				//B0004	收货人手机号		String(11位)	接收收货信息的人的联系方式，当D0008为1或3时，该字段必填，例如： 13800011111	C
	
	@SerializedName("receiver_address")
	private String B0005;				//B0005	送货地址		String	收货地址，当D0008为1或3时，该字段必填	C
	
	@SerializedName("registration_email")
	private String B0006;				//B0006	注册email		String	用户注册email，必须是商户已验证过的	N
	
	@SerializedName("registration_moblie_id")
	private String B0007;				//B0007	注册手机号		String(11位)	注册人手机号码	N
	
	@SerializedName("registration_identify_code")
	private String B0008;				//B0008	身份证号		String	注册人身份证号，当D0009为1时，该字段必填	C
	
	@SerializedName("device_id")
	private String D0001;				//D0001	设备标识		String	商户侧生成的设备唯一标识，如手机MAC码，或是内部定义的终端识别ID。	N
	
	@SerializedName("device_type")
	private String D0002;				//D0002	设备类型		String	客户端形态产品填写手机型号；页面形态产品填写浏览器User-Agent信息，如是Web端用户则不可为空。APP可为空。	N
	
	@SerializedName("user_ip")
	private String D0003;				//D0003	用户ID		String	商户侧用户标识，可以是数据库主键标识，也可以是用户登陆id	N
	
	@SerializedName("registration_time")
	private String D0004;				//D0004	用户注册时间		String	用户注册该产品的时间，精确到秒。例如： 20150311120000	N
	
	@SerializedName("user_agent")
	private String D0005;				//D0005	产品形态		String	1-android客户端；2-IOS客户端；3-PC端（web页面）；4-手机端（wap或html5等页面）；	N
	
	@SerializedName("success_transactions_number")
	private String D0007;				//D0007用户成功交易笔数	String	用户在商户端成功交易的笔数	N
	
	@SerializedName("goods_type")
	private String D0008;				//D0008	商品分类		String	0：虚拟类/  1：实物类/ 2：机票类/ 3：3C类	Y
	
	@SerializedName("real_name")
	private String D0009;				//D0009	商品实名制购买	String	0：非实名制，1：实名制	Y
	
	@SerializedName("business_type")
	private String D0010;				//D0010	业务分类		String	3：跨境收银	Y
	
	public String getB0002() {
		return B0002;
	}

	public void setB0002(String b0002) {
		B0002 = b0002;
	}

	public String getB0003() {
		return B0003;
	}

	public void setB0003(String b0003) {
		B0003 = b0003;
	}

	public String getB0004() {
		return B0004;
	}

	public void setB0004(String b0004) {
		B0004 = b0004;
	}

	public String getB0005() {
		return B0005;
	}

	public void setB0005(String b0005) {
		B0005 = b0005;
	}

	public String getB0006() {
		return B0006;
	}

	public void setB0006(String b0006) {
		B0006 = b0006;
	}

	public String getB0007() {
		return B0007;
	}

	public void setB0007(String b0007) {
		B0007 = b0007;
	}

	public String getB0008() {
		return B0008;
	}

	public void setB0008(String b0008) {
		B0008 = b0008;
	}

	public String getD0001() {
		return D0001;
	}

	public void setD0001(String d0001) {
		D0001 = d0001;
	}

	public String getD0002() {
		return D0002;
	}

	public void setD0002(String d0002) {
		D0002 = d0002;
	}

	public String getD0003() {
		return D0003;
	}

	public void setD0003(String d0003) {
		D0003 = d0003;
	}

	public String getD0004() {
		return D0004;
	}

	public void setD0004(String d0004) {
		D0004 = d0004;
	}

	public String getD0005() {
		return D0005;
	}

	public void setD0005(String d0005) {
		D0005 = d0005;
	}

	public String getD0007() {
		return D0007;
	}

	public void setD0007(String d0007) {
		D0007 = d0007;
	}

	public String getD0008() {
		return D0008;
	}

	public void setD0008(String d0008) {
		D0008 = d0008;
	}

	public String getD0009() {
		return D0009;
	}

	public void setD0009(String d0009) {
		D0009 = d0009;
	}

	public String getD0010() {
		return D0010;
	}

	public void setD0010(String d0010) {
		D0010 = d0010;
	}

	@SerializedName("risk_expand")
	private String riskExpand;		//风控扩展信息

	public String getRiskExpand() {
		return riskExpand;
	}

	public void setRiskExpand(String riskExpand) {
		this.riskExpand = riskExpand;
	}
}
