package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Entity
@Component
@Table(name="AES_TAGS")
public class Tag implements Serializable
{
	/**
	 * @tagId The unique Identifier for the Class
	 */
	@Id
	@SequenceGenerator(name = "AES_TAGS_SEQ", sequenceName = "AES_TAGS_SEQ")
	@GeneratedValue(generator = "AES_TAGS_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "TAG_ID")
	private Integer tagId;
	
	/**
	 * @tagName A String representation to describe the tag of a question.
	 */
	@Column(name="TAG_NAME")
	private String tagName;

	public Tag() {
		super();
	}
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", tagName=" + tagName + "]";
	}	
}