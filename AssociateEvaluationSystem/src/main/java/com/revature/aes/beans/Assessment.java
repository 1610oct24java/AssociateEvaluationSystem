package com.revature.aes.beans;

import java.io.Serializable;
import java.util.Set;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aes_assessment")
public class Assessment implements Serializable {
	
	private static final long serialVersionUID = -6152668317029130986L;
	@Id
	@Column(name = "assessment_id")
	@SequenceGenerator(sequenceName = "aes_assessment_seq", name = "aes_assessment_seq", allocationSize=1)
	@GeneratedValue(generator = "aes_assessment_seq", strategy = GenerationType.SEQUENCE)
	private int assessmentId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "grade")
	private int grade;

	@Column(name = "time_limit")
	private int timeLimit;

	@Column(name = "created_timestamp")
	private Timestamp createdTimeStamp;

	@Column(name = "finished_timestamp")
	private Timestamp finishedTimeStamp;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "template_id")
	private Template template;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aes_assessment_options", 
		joinColumns = @JoinColumn(name = "assessment_id"), 
		inverseJoinColumns = @JoinColumn(name = "option_id"))
	private Set<Option> options;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "assessmentId")
	private Set<AssessmentDragDrop> assessmentDragDrop;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy ="assessmentId")
	private Set<FileUpload> fileUpload;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aes_snippet_response", 
		joinColumns = @JoinColumn(name = "snippet_template_id"), 
		inverseJoinColumns = @JoinColumn(name = "assessment_id"))
	private Set<SnippetTemplate> snippedTemplate;

	
}
