package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Option;

@Repository("optionDao")
public interface OptionsDAO extends JpaRepository<Option, Integer>{

}
