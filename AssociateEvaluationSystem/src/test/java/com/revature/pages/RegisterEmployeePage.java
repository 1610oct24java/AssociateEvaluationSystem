package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterEmployeePage {
	
	WebDriver driver;
	
	By firstName = By.id("inputFirstName");
	By lastName = By.id("inputLastName");
	By email = By.id("inputEmail");
	By register = By.id("btn-register-recruit");
	By viewEmployeesLink = By.xpath("//a[@href='viewEmployees']");
	By setAssessmentTemplateLink = By.xpath("//a[@href='createAssessment']");
	By logout = By.id("logout");
	
	/**
	 * The driver for the register employee page.
	 * @param driver particular to the register employee page
	 */
	public RegisterEmployeePage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Type employee first name in first name field.
	 * @param strFirstName employee first name
	 */
	public void setFirstName(String strFirstName) {
		driver.findElement(firstName).sendKeys(strFirstName);
	}
	
	/**
	 * Type employee last name in last name field.
	 * @param strLastName employee last name
	 */
	public void setLastName(String strLastName) {
		driver.findElement(lastName).sendKeys(strLastName);
	}
	
	/**
	 * Type employee email in email field.
	 * @param strFirstName employee email
	 */
	public void setEmail(String strEmail) {
		driver.findElement(email).sendKeys(strEmail);
	}
	
	public void clickRegister() {
		driver.findElement(register).click();
	}
	
	public void clickViewEmployees() {
		driver.findElement(viewEmployeesLink).click();
	}
	
	public void clickSetAssessmentTemplateLink() {
		driver.findElement(setAssessmentTemplateLink).click();
	}
	
	public void clickLogoutButton() {
		driver.findElement(logout).click();
	}
	
	/**
	 * Register employee in the database
	 * @param firstName employee first name
	 * @param lastName employee last name
	 * @param email employee email
	 */
	public void registerEmployee(String firstName, String lastName, String email) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.clickRegister();
	}
}
