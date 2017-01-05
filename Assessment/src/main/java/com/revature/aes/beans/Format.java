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
@Table(name = "AES_FORMATS")
public class Format implements Serializable {

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 9090154484770826813L;

	/**
	 * @formatId The unique Identifier for the Class
	 */
	@Id
	@SequenceGenerator(name = "AES_FORMATS_SEQ", sequenceName = "AES_FORMATS_SEQ")
	@GeneratedValue(generator = "AES_FORMATS_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "FORMAT_ID")
	private Integer formatId;

	/**
	 * @formatName A String representation of the type of question format.
	 */
	@Column(name = "FORMAT_NAME")
	private String formatName;

	public Integer getFormatId() {
		return formatId;
	}

	public void setFormatId(Integer formatId) {
		this.formatId = formatId;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formatId == null) ? 0 : formatId.hashCode());
		result = prime * result + ((formatName == null) ? 0 : formatName.hashCode());
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
		Format other = (Format) obj;
		if (formatId == null) {
			if (other.formatId != null)
				return false;
		} else if (!formatId.equals(other.formatId))
			return false;
		if (formatName == null) {
			if (other.formatName != null)
				return false;
		} else if (!formatName.equals(other.formatName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Format [formatId=" + formatId + ", formatName=" + formatName + "]";
	}

	public Format() {
		super();
		// TODO Auto-generated constructor stub
	}

}