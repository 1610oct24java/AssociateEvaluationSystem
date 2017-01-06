package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "aes_template_question")
public class TemplateQuestion implements Serializable {

	private static final long serialVersionUID = -8227667088089601251L;
	
	@Id
	@Column(name = "template_question_id")
	@SequenceGenerator(sequenceName = "aes_template_question_seq", name = "aes_template_question_seq", allocationSize=1)
	@GeneratedValue(generator = "aes_template_question_seq", strategy = GenerationType.SEQUENCE)
	private int templateQuestionId;
	
	@Column(name = "weight")
	private int weight;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name = "question_id")
	private Question patternInquiry;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="TEMPLATE_ID")
	@JsonIgnore
	private Template template;
	
	public TemplateQuestion() {
		super();
	}

	@Override
	public String toString() {
		return "TemplateQuestion [templateQuestionId=" + templateQuestionId + ", weight=" + weight
				+ ", templateQuestion=" + patternInquiry +"]";
	}

	public int getTemplateQuestionId() {
		return templateQuestionId;
	}

	public void setTemplateQuestionId(int templateQuestionId) {
		this.templateQuestionId = templateQuestionId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Question getPatternInquiry() {
		return patternInquiry;
	}

	public void setPatternInquiry(Question templateQuestion) {
		this.patternInquiry = templateQuestion;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
}
