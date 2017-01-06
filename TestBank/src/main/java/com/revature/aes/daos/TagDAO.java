package com.revature.aes.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Tag;

@Repository("tagDao")
public interface TagDAO extends JpaRepository<Tag, Integer>{
	public void deleteByTagName(String name);
}
