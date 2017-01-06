/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/

package com.revature.aes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Format;
import com.revature.aes.daos.FormatDAO;

@Service("FormatService")
@Transactional
public class FormatServiceImpl implements FormatService {
	
	@Autowired
	private FormatDAO fdao;

	/**
	 * Retrieves a format abject from a database;
	 * @param id - A unique identifier used.
	 * @return The format object to be returned or null if no object found;
	 */
	@Override
	public Format findFormatById(Integer id) {
		return fdao.findOne(id);
	}

	/**
	 * Retrieves a List of Format objects
	 * @return The List of Format Objects
	 */
	@Override
	public List<Format> findAllFormats() {
		return fdao.findAll();
	}

	@Override
	public Format getFormatByName(String name) {
		Format format = fdao.getByFormatName(name);
		return fdao.getByFormatName(name);
	}

}
