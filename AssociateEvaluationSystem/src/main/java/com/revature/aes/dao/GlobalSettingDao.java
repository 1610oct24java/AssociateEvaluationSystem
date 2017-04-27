package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.aes.beans.GlobalSetting;

public interface GlobalSettingDao extends JpaRepository<GlobalSetting, Integer> {

	GlobalSetting getGlobalSettingByPropertyId(int id);
	GlobalSetting getGlobalSettingByPropertyName(String propertyName);
	
	
	
}
