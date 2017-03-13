package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Component;

/**
 * Created by mpski on 2/22/17.
 */

@Entity
@Table(name="AES_CATEGORY_REQUEST")
public class CategoryRequest implements Serializable{

	
	@Id
	@GeneratedValue(generator = "AES_CATEGORY_REQUEST_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_CATEGORY_REQUEST_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_CATEGORY_REQUEST_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
    private String category;
	
	@Column(name="MSQUESTIONS")
    private Integer msQuestions; //number of multiple select questions
	@Column(name="MCQUESTIONS")
    private Integer mcQuestions; //number of multiple choice questions
	@Column(name="DDQUESTIONS")
    private Integer ddQuestions; //number of drag-n-drop questions
	@Column(name="CSQUESTIONS")
    private Integer csQuestions; //number of code-snippet questions

    public CategoryRequest() {
        super();
    }

    public CategoryRequest(String category, Integer msQuestions, Integer mcQuestions, Integer ddQuestions, Integer csQuestions) {
        this.category = category;
        this.msQuestions = msQuestions;
        this.mcQuestions = mcQuestions;
        this.ddQuestions = ddQuestions;
        this.csQuestions = csQuestions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMsQuestions() {
        return msQuestions;
    }

    public void setMsQuestions(Integer msQuestions) {
        this.msQuestions = msQuestions;
    }

    public Integer getMcQuestions() {
        return mcQuestions;
    }

    public void setMcQuestions(Integer mcQuestions) {
        this.mcQuestions = mcQuestions;
    }

    public Integer getDdQuestions() {
        return ddQuestions;
    }

    public void setDdQuestions(Integer ddQuestions) {
        this.ddQuestions = ddQuestions;
    }

    public Integer getCsQuestions() {
        return csQuestions;
    }

    public void setCsQuestions(Integer csQuestions) {
        this.csQuestions = csQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryRequest that = (CategoryRequest) o;

        if (getCategory() != null ? !getCategory().equals(that.getCategory()) : that.getCategory() != null)
            return false;
        if (getMsQuestions() != null ? !getMsQuestions().equals(that.getMsQuestions()) : that.getMsQuestions() != null)
            return false;
        if (getMcQuestions() != null ? !getMcQuestions().equals(that.getMcQuestions()) : that.getMcQuestions() != null)
            return false;
        if (getDdQuestions() != null ? !getDdQuestions().equals(that.getDdQuestions()) : that.getDdQuestions() != null)
            return false;
        return getCsQuestions() != null ? getCsQuestions().equals(that.getCsQuestions()) : that.getCsQuestions() == null;
    }

    @Override
    public int hashCode() {
        int result = getCategory() != null ? getCategory().hashCode() : 0;
        result = 31 * result + (getMsQuestions() != null ? getMsQuestions().hashCode() : 0);
        result = 31 * result + (getMcQuestions() != null ? getMcQuestions().hashCode() : 0);
        result = 31 * result + (getDdQuestions() != null ? getDdQuestions().hashCode() : 0);
        result = 31 * result + (getCsQuestions() != null ? getCsQuestions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryRequest{" +
                "category='" + category + '\'' +
                ", msQuestions=" + msQuestions +
                ", mcQuestions=" + mcQuestions +
                ", ddQuestions=" + ddQuestions +
                ", csQuestions=" + csQuestions +
                '}';
    }
}
