package com.revature.hulq.bash;

import java.io.Serializable;

public class BashData implements Serializable{
	private static final long serialVersionUID = 8246479384523821053L;
	
	private String userInfo;
	private String keyInfo;
	
	
	public BashData() {
		super();
		
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public String getKeyInfo() {
		return keyInfo;
	}
	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyInfo == null) ? 0 : keyInfo.hashCode());
		result = prime * result + ((userInfo == null) ? 0 : userInfo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BashData other = (BashData) obj;
		if (keyInfo == null) {
			if (other.keyInfo != null)
				return false;
		} else if (!keyInfo.equals(other.keyInfo))
			return false;
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BashData [userInfo=" + userInfo + ", keyInfo=" + keyInfo + "]";
	}
	
	
	
}
