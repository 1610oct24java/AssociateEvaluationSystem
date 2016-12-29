package com.revature.aes.mail;

public class MailObject {
	
	private String candidateEmail;
	private String link;
	private String tempPass;
	private String candidateName;
	private String recruiterEmail;
	private String grade;
	
	MailObject(){
		super();
	}
	
	MailObject(String string1, String string2, String string3, int id){
		if(id==1)
		{
			this.candidateEmail= string1;
			this.link=string2;
			this.tempPass=string3;
		}
		else if(id==2)
		{
			this.candidateEmail=string1;
			this.recruiterEmail=string2;
			this.candidateName=string3;
		}
		else if(id==3)
		{
			this.recruiterEmail=string1;
			this.candidateName=string2;
			this.grade=string3;
		}
	}
	
	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String canidateEmail) {
		this.candidateEmail = canidateEmail;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTempPass() {
		return tempPass;
	}

	public void setTempPass(String tempPass) {
		this.tempPass = tempPass;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getRecruiterEmail() {
		return recruiterEmail;
	}

	public void setRecruiterEmail(String recruiterEmail) {
		this.recruiterEmail = recruiterEmail;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "MailObject [candidateEmail=" + candidateEmail + ", link=" + link + ", tempPass=" + tempPass
				+ ", candidateName=" + candidateName + ", recruiterEmail=" + recruiterEmail + ", grade=" + grade + "]";
	}
}