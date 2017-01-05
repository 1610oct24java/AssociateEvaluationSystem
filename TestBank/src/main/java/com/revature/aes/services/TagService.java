package com.revature.aes.services;

import java.util.List;

import com.revature.aes.beans.Tag;

public interface TagService {
	public List<Tag> getAllTags();
	public void saveTag(Tag tag);
	public void deleteByTagName(String name);
}
