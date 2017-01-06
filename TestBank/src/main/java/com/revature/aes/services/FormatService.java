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

import com.revature.aes.beans.Format;

public interface FormatService
{
	//c
	//r
		public Format findFormatById(Integer id);
		public List<Format> findAllFormats();
	//u
	//d
	public Format getFormatByName(String name);
}
