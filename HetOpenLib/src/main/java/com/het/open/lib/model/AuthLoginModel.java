package com.het.open.lib.model;

import java.io.Serializable;

/**
 * OauthModel
 * 
 * @author xuchao
 *
 */
public class AuthLoginModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String accessToken = "";
	private String expiresIn = "";
	private String refreshToken = "";
	private String openId = "";
	private String phone = "";

	@Override
	public String toString() {
		return "AuthLoginModel{" +
				"accessToken='" + accessToken + '\'' +
				", expiresIn='" + expiresIn + '\'' +
				", refreshToken='" + refreshToken + '\'' +
				", openId='" + openId + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}

	public AuthLoginModel() {
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}



	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}



}