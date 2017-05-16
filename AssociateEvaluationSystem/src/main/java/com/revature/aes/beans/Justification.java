package com.revature.aes.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "aes_justification")
public class Justification {
	
	@Id
	private int justificationId;	
	
	@Column(name = "justification")
	private String explanation;

	public Justification() {
		super();
	}

	public int getJustificationId() {
		return justificationId;
	}

	public void setJustificationId(int justificationId) {
		this.justificationId = justificationId;
	}
	
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Override
	public String toString() {
		return "Justification [justificationId=" + justificationId + ", explanation=" + explanation + "]";
	}
}
