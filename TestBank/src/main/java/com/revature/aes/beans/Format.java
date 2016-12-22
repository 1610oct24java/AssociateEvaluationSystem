/****************************************************************
 * Project Name: Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="AES_FORMATS")
public class Format implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9090154484770826813L;

}
