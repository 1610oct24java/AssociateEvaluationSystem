package com.revature.aes.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.logging.Logging;

@Component
public class MailObject {

	@Autowired
	private Logging log;
	
	private String link;
	private String tempPass;
	private String type;
	private int assessmentId;
	
	MailObject(){
		super();
	}
	
	public MailObject(String link, String tempPass, String type, int assessmentId) {
		super();

			this.link = link;
			this.tempPass = tempPass;
			this.type = type;
			this.assessmentId = assessmentId;

	}
	
	public void setAssessmentId(int assessmentId){
		this.assessmentId=assessmentId;
	}
	
	public int getAssessmentId(){
		return assessmentId;
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
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type=type;
	}

	@Override
	public String toString() {
		return "MailObject [link=" + link + ", tempPass=" + tempPass + ", type=" + type + ", assessmentId=" + assessmentId + "]";
	}
}