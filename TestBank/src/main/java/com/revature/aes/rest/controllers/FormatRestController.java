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

package com.revature.aes.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Format;
import com.revature.aes.services.FormatService;

/**
 * The Rest Controller that handles HTTP Requests and Response for the Format
 * object.
 */
@RestController
public class FormatRestController
{
	/**
	 * @formatService The service used to handle HTTPs Requests and Response
	 *                  for the format class.
	 */
	@Autowired
	private FormatService formatService;
	
	/**
	 * Retrieves a List of all formats stored in a database.
	 * @return The List of all formats
	 */
	@RequestMapping(value="format", method=RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE}) 
	public List<Format> getAllFormats(){
		return formatService.findAllFormats();
	}
	
	/**
	 * Retrieves a specific format based of the unique identifier from a database
	 * @param id The unique identifier for the format object
	 * @return The format object or null if invalid id.
	 */
	@RequestMapping(value="format/{id}", method=RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public Format getFormat(@PathVariable Integer id){
		return formatService.findFormatById(id);
	}
	

}
