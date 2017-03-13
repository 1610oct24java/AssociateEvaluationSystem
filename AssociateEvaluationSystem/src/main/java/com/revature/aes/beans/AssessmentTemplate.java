package com.revature.aes.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

public class AssessmentTemplate {
	
	
	@Id
	@GeneratedValue(generator = "AES__SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_CATEGORIES_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_CATEGORIES_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	@Column(name="assess_template_id")
	private int assessTempateId;
	
	@Column
	private List<CategoryRequest> categoryRequestList;
    private Integer timeLimit;
    
    

}
