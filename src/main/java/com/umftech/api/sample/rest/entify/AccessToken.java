package com.umftech.api.sample.rest.entify;

import com.google.gson.annotations.SerializedName;

public class AccessToken {
	@SerializedName("access_token")
	private String token;
	// remain time in seconds since acquiring.
	@SerializedName("expires_in")
	private long expiresIn;
	// unix time of acquire access token in second. 
	private long acquireTime;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	public boolean isExpired(){
		return false;
	}
}
