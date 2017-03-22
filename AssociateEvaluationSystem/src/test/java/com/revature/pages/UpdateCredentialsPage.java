package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UpdateCredentialsPage {
	
	WebDriver driver;
	
	By currentEmail = By.id("currentEmail");
	By newEmail = By.id("newEmail");
	By firstName = By.id("inputFirstName");
	By lastName = By.id("inputLastName");
	By currentPassword = By.xpath("//input[@ng-model='oldPassword']");
	By newPassword = By.xpath("//input[@ng-model='newPassword']");
	By confirmNewPassword = By.xpath("//input[@ng-model='confirmNewPassword']");
	By registerCandidateLink = By.xpath("//a[@href='recruit']");
    By viewCandidatesLink = By.xpath("//a[@href='view']");
	By updateButton = By.id("btn-update");
	By logoutButton = By.id("logout");
	
	/**
	 * The driver for the register update credentials page.
	 * @param driver particular to the update credentials page
	 */
	public UpdateCredentialsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Type current email address in current email address field.
	 * @param strCurrentEmail current email address
	 */
	public void setCurrentEmail(String strCurrentEmail) {
		driver.findElement(currentEmail).sendKeys(strCurrentEmail);
	}
	
	/**
	 * Type new email address in new email address field.
	 * @param strNewEmail new email address
	 */
	public void setNewEmail(String strNewEmail) {
		driver.findElement(newEmail).sendKeys(strNewEmail);
	}
	
	/**
	 * Type first name in first name field.
	 * @param strFirstName first name
	 */
	public void setFirstName(String strFirstName) {
		driver.findElement(firstName).sendKeys(strFirstName);
	}
	
	/**
	 * Type last name in last name field.
	 * @param strLastName last name
	 */
	public void setLastName(String strLastName) {
		driver.findElement(firstName).sendKeys(strLastName);
	}
	
	/**
	 * Type current password in current password field.
	 * @param strCurrentPassword current password
	 */
	public void setCurrentPassword(String strCurrentPassword) {
		driver.findElement(currentPassword).sendKeys(strCurrentPassword);
	}
	
	/**
	 * Type new password in new password field.
	 * @param strNewPassword new password
	 */
	public void setNewPassword(String strNewPassword) {
		driver.findElement(newPassword).sendKeys(strNewPassword);
	}
	
	/**
	 * Type confirm new password in confirm new password field.
	 * @param strConfirmNewPassword confirm new password
	 */
	public void setConfirmNewPassword(String strConfirmNewPassword) {
		driver.findElement(confirmNewPassword).sendKeys(strConfirmNewPassword);
	}
	
	public void clickRegisterCandidate() {
		driver.findElement(registerCandidateLink).click();
	}
	
	public void clickViewCandidates() {
		driver.findElement(viewCandidatesLink).click();
	}
	
	public void clickUpdate() {
		driver.findElement(updateButton).click();
	}
	
	public void clickLogout() {
		driver.findElement(logoutButton).click();
	}
	
	/**
	 * Update credentials
	 * @param currentEmail current email
	 * @param newEmail new email
	 * @param firstname first name
	 * @param lastname last name
	 * @param currentPassword current password
	 * @param newPassword new password
	 * @param confirmNewPassword confirm new password
	 */
	public void updateCredentials(String currentEmail, 
								  String newEmail, 
								  String firstname, 
								  String lastname, 
								  String currentPassword,
								  String newPassword, 
								  String confirmNewPassword ) {
		this.setCurrentEmail(currentEmail);
		this.setNewEmail(newEmail);
		this.setFirstName(firstname);
		this.setLastName(lastname);
		this.setCurrentPassword(currentPassword);
		this.setNewPassword(newPassword);
		this.setConfirmNewPassword(confirmNewPassword);
	}
	
	public void updatePassword(String currentEmail, String currentPassword, String newPassword, String confirmNewPassword) {
		this.setCurrentEmail(currentEmail);
		this.setCurrentPassword(currentPassword);
		this.setNewPassword(newPassword);
		this.setConfirmNewPassword(confirmNewPassword);
	}
	
	public void updateFirstName(String currentEmail, String newFirstName) {
		this.setCurrentEmail(currentEmail);
		this.setFirstName(newFirstName);
	}
	
	public void updateLastName(String currentEmail, String newLastName) {
		this.setCurrentEmail(currentEmail);
		this.setLastName(newLastName);
	}
	
}
