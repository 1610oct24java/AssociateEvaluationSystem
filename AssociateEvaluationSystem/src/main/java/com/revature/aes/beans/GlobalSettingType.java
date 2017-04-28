package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AES_GLOBAL_SETTINGS_TYPE")
public class GlobalSettingType implements Serializable {
	
	private static final long serialVersionUID = 6707472076548282449L;

	@Id
	@Column(name = "PROPERTY_TYPE_ID")
	int propertyTypeId;
	
	@Column(name = "PROPERTY_TYPE_NAME")
	String propertyType;
	
	public GlobalSettingType() {
		super();
	}

	public int getPropertyTypeId() {
		return propertyTypeId;
	}

	public void setPropertyTypeId(int propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	@Override
	public String toString() {
		return "GlobalSettingType [propertyTypeId=" + propertyTypeId + ", propertyType=" + propertyType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + propertyTypeId;
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
		GlobalSettingType other = (GlobalSettingType) obj;
		if (propertyTypeId != other.propertyTypeId)
			return false;
		return true;
	}


	
}
