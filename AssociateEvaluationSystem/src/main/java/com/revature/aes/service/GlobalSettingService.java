package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.GlobalSetting;

public interface GlobalSettingService {

	boolean getCanCandidatesReview();
	void setCanCandidatesReview(boolean b);
	
	int getMaxHoursCandidatesCanReview();
	void setMaxHoursCandidatesCanReview(int hours);
	
	GlobalSetting getSetting(String propertyName);
	String getSettingValue(String propertyName);

	boolean setSetting(String propertyName, String propertyValue);
	List<GlobalSetting> getSettings();
	
}
