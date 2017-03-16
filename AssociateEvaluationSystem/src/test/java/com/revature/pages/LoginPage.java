package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	WebDriver driver;
	
	By email = By.id("input-email");
	By password = By.id("input-password");
	By login = By.className("btn-revature");
	
	/**
	 * The driver for the login page.
	 * @param driver particular to the login page
	 */
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Type email in email field.
	 * @param strEmail user email
	 */
	public void setEmail(String strEmail) {
		driver.findElement(email).sendKeys(strEmail);
	}
	
	/**
	 * Type password in password field.
	 * @param strPassword user password
	 */
	public void setPassword(String strPassword) {
		driver.findElement(password).sendKeys(strPassword);
	}
	
	public void clickLogin() {
		driver.findElement(login).click();
	}
	
	/**
	 * Retrieve title
	 * @return title of login page
	 */
	public String getTitle() {
		return driver.getTitle();
	}
	
	/**
	 * It will log the user in to AES.
	 * @param email the user email
	 * @param password the user password
	 */
	public void loginToAES(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
		this.clickLogin();
	}

}