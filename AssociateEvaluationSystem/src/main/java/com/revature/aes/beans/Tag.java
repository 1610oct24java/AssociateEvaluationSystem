package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Table(name="AES_TAGS")
public class Tag implements Serializable
{
	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 538081587142842391L;
	
	/**
	 * @tagId The unique Identifier for the Class
	 */
	@Id
	@GeneratedValue(generator = "AES_TAGS_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_TAGS_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_TAGS_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		Tag other = (Tag) obj;
		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId))
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", tagName=" + tagName + "]";
	}

}


	

