package com.revature.aes.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.GlobalSetting;
import com.revature.aes.dao.GlobalSettingDao;

@Service
public class GlobalSettingServiceImpl implements GlobalSettingService {
	
	@Autowired
	GlobalSettingDao globalSettingDao;

	static final String CANDIDATES_CAN_REVIEW_ASSESSMENTS = "CANDIDATES_CAN_REVIEW_ASSESSMENTS";
	static final String MAX_HOURS_CANDIDATES_CAN_REVIEW = "MAX_HOURS_CANDIDATES_CAN_REVIEW";
	
	@Override
	public boolean getCanCandidatesReview() {
		return Boolean.getBoolean(this.getSettingValue(CANDIDATES_CAN_REVIEW_ASSESSMENTS));
	}

	@Override
	public void setCanCandidatesReview(boolean b) {
		this.setSetting(CANDIDATES_CAN_REVIEW_ASSESSMENTS, Boolean.toString(b));
		
	}

	@Override
	public int getMaxHoursCandidatesCanReview() {
		return Integer.valueOf(this.getSettingValue(MAX_HOURS_CANDIDATES_CAN_REVIEW));
	}

	@Override
	public void setMaxHoursCandidatesCanReview(int hours) {
		this.setSetting(MAX_HOURS_CANDIDATES_CAN_REVIEW, String.valueOf(hours));
	}

	@Override
	public String getSettingValue(String propertyName) {
		return globalSettingDao.getGlobalSettingByPropertyName(propertyName).getPropertyName();
	}

	
	@Override
	public boolean setSetting(String propertyName, String propertyValue) {
		GlobalSetting gs = globalSettingDao.getGlobalSettingByPropertyName(propertyName);
		if (gs != null){
			gs.setPropertyValue(propertyValue);
			globalSettingDao.save(gs);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public GlobalSetting getSetting(String propertyName) {
		return globalSettingDao.getGlobalSettingByPropertyName(propertyName);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<GlobalSetting> getSettings(){
		System.out.println(globalSettingDao.getGlobalSettingByPropertyId(1));
		
		return globalSettingDao.findAll();
	}

}
