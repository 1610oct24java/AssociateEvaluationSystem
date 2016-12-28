package com.revature.aes.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aes_templates")
public class Template implements Serializable {
	private static final long serialVersionUID = -8060916464018913931L;
	@Id
	@Column(name = "template_id")
	@SequenceGenerator(sequenceName = "aes_templates_seq", name = "aes_templates_seq")
	@GeneratedValue(generator = "aes_templates_seq", strategy = GenerationType.SEQUENCE)
	private int templateId;

	@Column(name = "create_timestamp")
	private LocalDateTime createTimeStamp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CREATOR_ID", referencedColumnName="USER_ID")
	private User creator;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="TEMPLATE_ID")
	private List<TemplateQuestion> templateQuestion;

	public Template() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Template [templateId=" + templateId + ", createTimeStamp=" + createTimeStamp + ", creator=" + creator
				+ ", templateQuestion=" + templateQuestion + "]";
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public LocalDateTime getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(LocalDateTime createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<TemplateQuestion> getTemplateQuestion() {
		return templateQuestion;
	}

	public void setTemplateQuestion(List<TemplateQuestion> templateQuestion) {
		this.templateQuestion = templateQuestion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
