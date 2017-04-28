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
	@Column(name = "justification_id")
	@GeneratedValue(generator = "AES_JUSTIFICATION_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_ASSESSMENT_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_JUSTIFICATION_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	private int justificationId;	
	
	@Column(name = "justification")
	private String justification;

	public Justification() {
		super();
	}

	public int getJustificationId() {
		return justificationId;
	}

	public void setJustificationId(int justificationId) {
		this.justificationId = justificationId;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	@Override
	public String toString() {
		return "Justification [justificationId=" + justificationId + ", justification=" + justification + "]";
	}
}
