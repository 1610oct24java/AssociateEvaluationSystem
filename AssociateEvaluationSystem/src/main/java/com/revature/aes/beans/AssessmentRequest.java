package com.revature.aes.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="AES_ASSESSMENT_REQUEST")
public class AssessmentRequest implements Serializable{

	private static final long serialVersionUID = 4857861341651025701L;
	
	@Id
	@GeneratedValue(generator = "AES_ASSESSMENT_REQUEST_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_ASSESSMENT_REQUEST_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_ASSESSMENT_REQUEST_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	private int assessmentRequestId;

	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="assessmentRequest")
	private Set<CategoryRequest> categoryRequestList;

	@Transient
    private String link;

	@Transient
    private String userEmail;
    
    @Column(name="TIMELIMIT")
    private Integer timeLimit;
    
	public AssessmentRequest() {
		super();
		
	}

	public AssessmentRequest(Set<CategoryRequest> categoryRequestList, String link, String userEmail, Integer timeLimit) {
		this.categoryRequestList = categoryRequestList;
		this.link = link;
		this.userEmail = userEmail;
		this.timeLimit = timeLimit;
	}

	public Set<CategoryRequest> getCategoryRequestList() {
		return categoryRequestList;
	}

	public void setCategoryRequestList(Set<CategoryRequest> categoryRequestList) {
		this.categoryRequestList = categoryRequestList;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (o == null || getClass() != o.getClass()) 
			return false;

		AssessmentRequest that = (AssessmentRequest) o;

		if (getCategoryRequestList() != null ? !getCategoryRequestList().equals(that.getCategoryRequestList()) : that.getCategoryRequestList() != null)
			return false;
		if (getLink() != null ? !getLink().equals(that.getLink()) : that.getLink() != null) 
			return false;
		if (getUserEmail() != null ? !getUserEmail().equals(that.getUserEmail()) : that.getUserEmail() != null)
			return false;
		
		return getTimeLimit() != null ? getTimeLimit().equals(that.getTimeLimit()) : that.getTimeLimit() == null;
	}

	@Override
	public int hashCode() {
		int result = getCategoryRequestList() != null ? getCategoryRequestList().hashCode() : 0;
		result = 31 * result + (getLink() != null ? getLink().hashCode() : 0);
		result = 31 * result + (getUserEmail() != null ? getUserEmail().hashCode() : 0);
		result = 31 * result + (getTimeLimit() != null ? getTimeLimit().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "AssessmentRequest{" +
				"categoryRequestList=" + categoryRequestList +
				", link='" + link + '\'' +
				", userEmail='" + userEmail + '\'' +
				", timeLimit=" + timeLimit +
				'}';
	}
}
