package com.revature.aes.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "aes_templates")
public class Template implements Serializable {
	private static final long serialVersionUID = -8060916464018913931L;
	
	@Id
	@Column(name = "template_id")
	@GeneratedValue(generator = "AES_TEMPLATES_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_TEMPLATES_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_TEMPLATES_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	private int templateId;

	@Column(name = "create_timestamp")
	private Timestamp createTimeStamp;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CREATOR_ID", referencedColumnName="USER_ID")
	private User creator;

	@OneToMany(fetch = FetchType.EAGER, mappedBy ="template", cascade = CascadeType.ALL)
	private Set<TemplateQuestion> templateQuestion;

	public Template() {
		super();
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

	public Timestamp getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Timestamp createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Set<TemplateQuestion> getTemplateQuestion() {
		return templateQuestion;
	}

	public void setTemplateQuestion(Set<TemplateQuestion> templateQuestion) {
		this.templateQuestion = templateQuestion;
	}
}
