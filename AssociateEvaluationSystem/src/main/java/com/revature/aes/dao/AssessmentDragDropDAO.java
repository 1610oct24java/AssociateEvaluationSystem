package com.revature.aes.dao;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.AssessmentDragDrop;

@Repository("assessmentDDrop")
public interface AssessmentDragDropDAO extends JpaRepository<AssessmentDragDrop, Integer> {
	public AssessmentDragDrop getByassessmentDragDropId(Integer assessmentDragDropId);
}