package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "aes_global_settings")
public class GlobalSetting implements Serializable {

	private static final long serialVersionUID = -4222238979371700842L;

	@Id
	@Column(name = "property_id")
	int propertyId;
	
	@Column(name = "property_name")
	String propertyName;
	
	@Column(name = "property_value")
	String propertyValue;
	
	@ManyToOne
	@JoinColumn(name = "property_type_id")
	GlobalSettingType propertyType;

	public GlobalSetting() {
		super();
	}

	
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public GlobalSettingType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(GlobalSettingType propertyType) {
		this.propertyType = propertyType;
	}
	
	@Override
	public String toString() {
		return "GlobalSettings [propertyId=" + propertyId + ", propertyName=" + propertyName + ", propertyValue="
				+ propertyValue + ", propertyType=" + propertyType + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + propertyId;
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
		GlobalSetting other = (GlobalSetting) obj;
		if (propertyId != other.propertyId)
			return false;
		return true;
	}
}
