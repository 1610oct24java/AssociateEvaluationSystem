package com.revature.aes.mail;

public class MailObject {
	
	private String link;
	private String tempPass;
	private String type;
	private int assessmentId;
	
	MailObject(){
		super();
	}
	
	public MailObject(String link, String tempPass, String type, int assesmentId) {
		super();
		try{
			this.link = link;
			this.tempPass = tempPass;
			this.type = type;
			this.assessmentId = assesmentId;
		}catch(NullPointerException npe){
			this.link = link;
			this.tempPass = tempPass;
			this.type = type;
			this.assessmentId = 0;
		}
	}
	
	public void setAssesmentId(int assesmentId){
		this.assessmentId=assesmentId;
	}
	
	public int getAssesmentId(){
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
		return "MailObject [link=" + link + ", tempPass=" + tempPass + ", type=" + type + "]";
	}
}