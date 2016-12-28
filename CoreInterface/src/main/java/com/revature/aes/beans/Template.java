package com.revature.aes.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "template")
	private TemplateQuestion templateQuestion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "templates")
	private Assessment asssessment;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User creator;

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

	public TemplateQuestion getTemplateQuestion() {
		return templateQuestion;
	}

	public void setTemplateQuestion(TemplateQuestion templateQuestion) {
		this.templateQuestion = templateQuestion;
	}

	public Assessment getAsssessment() {
		return asssessment;
	}

	public void setAsssessment(Assessment asssessment) {
		this.asssessment = asssessment;
	}

	public Template() {
		super();
	}

	public User getUser() {
		return creator;
	}

	public void setUser(User user) {
		this.creator = user;
	}

	public Template(int templateId, LocalDateTime createTimeStamp, TemplateQuestion templateQuestion,
			Assessment asssessment, User user) {
		this();
		this.templateId = templateId;
		this.createTimeStamp = createTimeStamp;
		this.templateQuestion = templateQuestion;
		this.asssessment = asssessment;
		this.creator = user;
	}

	@Override
	public String toString() {
		return "Template [templateId=" + templateId + ", createTimeStamp=" + createTimeStamp + ", templateQuestion="
				+ templateQuestion + ", asssessment=" + asssessment + ", user=" + creator + "]";
	}
}
