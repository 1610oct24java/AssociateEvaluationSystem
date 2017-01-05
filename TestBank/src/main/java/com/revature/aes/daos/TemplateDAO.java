package com.revature.aes.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Template;

@Repository
public interface TemplateDAO extends JpaRepository<Template, Integer>{

}
