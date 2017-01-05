package com.revature.aes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Tag;
import com.revature.aes.daos.TagDAO;

@Service
@Transactional
public class TagServiceImpl implements TagService {

	@Autowired
	@Qualifier("tagDao")
	private TagDAO tdao;
	
	@Override
	public List<Tag> getAllTags() {
		return tdao.findAll();
	}
}
