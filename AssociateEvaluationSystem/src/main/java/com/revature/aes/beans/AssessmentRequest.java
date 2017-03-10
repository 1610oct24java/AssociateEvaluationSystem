package com.revature.aes.beans;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AssessmentRequest {

	private List<CategoryRequest> categoryRequestList;
    private String link;
    private String userEmail;
    private Integer timeLimit;
    
	public AssessmentRequest() {
		super();
		
	}

	public AssessmentRequest(List<CategoryRequest> categoryRequestList, String link, String userEmail, Integer timeLimit) {
		this.categoryRequestList = categoryRequestList;
		this.link = link;
		this.userEmail = userEmail;
		this.timeLimit = timeLimit;
	}

	public List<CategoryRequest> getCategoryRequestList() {
		return categoryRequestList;
	}

	public void setCategoryRequestList(List<CategoryRequest> categoryRequestList) {
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
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		AssessmentRequest that = (AssessmentRequest) o;

		if (getCategoryRequestList() != null ? !getCategoryRequestList().equals(that.getCategoryRequestList()) : that.getCategoryRequestList() != null)
			return false;
		if (getLink() != null ? !getLink().equals(that.getLink()) : that.getLink() != null) {
			return false;
		}
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
