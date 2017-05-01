package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.GlobalSetting;
import com.revature.aes.beans.GlobalSettingType;

@Repository
public interface GlobalSettingTypeDAO extends JpaRepository<GlobalSettingType, Integer> {

}
