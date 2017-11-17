package com.umftech.api.sample.rest.entify;

import com.umftech.api.sample.rest.entify.enums.LinkMethod;

/********************************
 * @description 返回商户可使用的URL实体类
 * @author lixiaohe
 * @date 20170320
 ********************************/
public class Link  {
	private String href;//相关的URL
	

	//	Self: 当前对象的链接，对于创建请求的返回值尤其有用。
//	Parent_payment：对于refund之类的对象有用。
//	Refund：退款的链接。
	private String ref;//与当前对象的关系

	private LinkMethod method;//HTTP请求类型，Enum类型：GET, POST, PUT
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public LinkMethod getMethod() {
		return method;
	}

	public void setMethod(LinkMethod method) {
		this.method = method;
	}
}
