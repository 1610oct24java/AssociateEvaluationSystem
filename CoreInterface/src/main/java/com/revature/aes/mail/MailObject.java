package com.revature.aes.mail;

public class MailObject {
	
	private String link;
	private String tempPass;
	private String type;
	private String assesmentId;
	
	MailObject(){
		super();
	}
	
	public MailObject(String link, String tempPass, String type, String assesmentId) {
		super();
		this.link = link;
		this.tempPass = tempPass;
		this.type = type;
		this.assesmentId = assesmentId;
	}
	
	public void setAssesmentId(String assesmentId){
		this.assesmentId=assesmentId;
	}
	
	public String getAssesmentId(){
		return assesmentId;
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