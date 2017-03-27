package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateAssessmentPage {
	
	WebDriver driver;
	
	By registerEmployeeLink = By.xpath("//a[@href='registerEmployee']");
	By viewEmployeesLink = By.xpath("//a[@href='viewEmployees']");
	By selectTime = By.name("timeSelect");
	By selectCategory; // Incomplete
    By selectType; // Incomplete
    By questionQuantity; // Incomplete
    By addRow; // Incomplete
    By submit; // Incomplete
    By logout = By.id("logout");
    
	/**
	 * The driver for the create assessment page.
	 * @param driver particular to the create assessment page
	 */
	public CreateAssessmentPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * 
	 * 
	 * INCOMPLETE 
	 * Waiting for page completion
	 *
	 */
    
    
}
