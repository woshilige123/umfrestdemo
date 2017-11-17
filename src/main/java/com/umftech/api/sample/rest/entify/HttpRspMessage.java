package com.umftech.api.sample.rest.entify;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpRspMessage implements Serializable {
	private static final long serialVersionUID = -1260201204880657574L;

	/**
	 * 返回信息
	 */
	private String rspBody = "";

	/**
	 * 耗时 （单位ms）
	 */
	private long tookTime;

	/**
	 * HTTP返回码
	 */
	private int rspCode;


	public int getRspCode() {
		return rspCode;
	}

	public void setRspCode(int rspCode) {
		this.rspCode = rspCode;
	}

	public HttpRspMessage() {
	}

	public String toString() {
		return "HttpResponseCode: " + rspCode + "\n HttpResponseBody: " + rspBody;
	}

	public long getTookTime() {

		return tookTime;
	}

	public String getRspBody() {
		return rspBody;
	}

	public void setRspBody(String rspBody) {
		this.rspBody = rspBody;
	}

	public void setTookTime(long tookTime) {

		this.tookTime = tookTime;
	}

}
