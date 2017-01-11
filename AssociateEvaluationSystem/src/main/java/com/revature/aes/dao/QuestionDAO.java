package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Question;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {
}
