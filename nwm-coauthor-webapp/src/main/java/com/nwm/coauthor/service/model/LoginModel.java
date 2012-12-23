package com.nwm.coauthor.service.model;

public class LoginModel extends BaseModel{
	private String _id;
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

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
}
