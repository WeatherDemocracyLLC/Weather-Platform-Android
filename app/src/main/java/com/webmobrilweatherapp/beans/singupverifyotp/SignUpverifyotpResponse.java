package com.webmobrilweatherapp.beans.singupverifyotp;

import com.google.gson.annotations.SerializedName;

public class SignUpverifyotpResponse{

	@SerializedName("code")
	private int code;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("token")
	private String token;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}