package com.nwm.coauthor.service.model;

public class LoginModel {
	private String fbId;
	private String coToken;
	
	public LoginModel(){
		
	}
	
	public LoginModel(String fbId, String coToken){
		this.fbId = fbId;
		this.coToken = coToken;
	}
	
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getCoToken() {
		return coToken;
	}
	public void setCoToken(String coToken) {
		this.coToken = coToken;
	}
}
