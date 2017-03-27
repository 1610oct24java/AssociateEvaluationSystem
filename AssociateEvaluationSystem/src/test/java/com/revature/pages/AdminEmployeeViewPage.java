package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AdminEmployeeViewPage {

	WebDriver driver;
	
	By searchOrderDropDown = By.xpath("/html/body/div/select");
	By searchBox = By.xpath("/html/body/div/input");
	By registerEmployeeLink = By.xpath("//a[@href='registerEmployee']");
	By updateEmployeeLink = By.xpath("//a[@href='updateCredentials']");
	By createAssessmentLink = By.xpath("//a[@href='createAssessment']");
	By logoutButton = By.id("logout");
	
	/**
	 * The driver for the admin employee view page
	 * @param driver particular to the admin employee view page 
	 */
	public AdminEmployeeViewPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Retrieve title
	 * @return title of admin employee view page
	 */
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void clickRegisterEmployeeLink() {
		driver.findElement(registerEmployeeLink).click();
	}
	
	public void clickUpdateEmployeeLink() {
		driver.findElement(updateEmployeeLink).click();
	}
	
	public void clickCreateAssessmentLink() {
		driver.findElement(createAssessmentLink).click();
	}
	
	public void clickLogoutButton() {
		driver.findElement(logoutButton).click();
	}
	
	/**
	 * Select the order of how employees will be displayed.
	 * @param order of employee
	 */
	public void selectSearchOrder(String order) {
		WebElement wb = driver.findElement(searchOrderDropDown);
		Select se = new Select(wb);
		se.selectByVisibleText(order);
	}
	
	public boolean verifyRegisterEmployeeLink() {
		return driver.findElement(registerEmployeeLink).isDisplayed();
	}
}