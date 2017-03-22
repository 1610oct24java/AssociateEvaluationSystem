package com.revature.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CandidateViewPage {

	WebDriver driver;
	
	By updateUserInfo = By.xpath("//a[@href='updateCredentials']");
	By registerCandidateLink = By.xpath("//a[@href='recruit']");
	By logoutButton = By.id("logout");
	
	/**
	 * The driver for the candidate view page.
	 * @param driver particular to the candidate view page
	 */
	public CandidateViewPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Retrieve title
	 * @return title of candidate view page
	 */
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void clickUpdateUserInfo() {
		driver.findElement(updateUserInfo).click();
	}
	
	public void clickRegisterCandidateLink() {
		driver.findElement(registerCandidateLink).click();
	}
	
	public void clickLogoutButton() {
		driver.findElement(logoutButton).click();
	}
	
	/**
	 * Check if candidate shows up in the candidate view page
	 * @param strFirstName candidate first name
	 * @param strLastName candidate last name
	 * @param strEmail candidate email
	 * @return boolean value if candidate exist or not
	 */
	public boolean verifyCandidateExistInView(String strFirstName, String strLastName, String strEmail) {
		
		boolean isCandidateDisplayedInView = false;
		
		// Get all the candidates in the list
		List<WebElement> elements = driver.findElements(By.xpath("//tr[@ng-repeat-start='candidate in candidates']"));

		// Check each candidate to match if the desired candidate exists
		for(int i=0;i<elements.size(); i++) {
			WebElement element = elements.get(i);

			String[] str = element.getText().split(" ");
			String lastName = str[0].split(",")[0];
			String firstName = str[1];
			String email = str[2];
			
			if(lastName.equals(strLastName) && firstName.equals(strFirstName) && email.equals(strEmail)) {
				isCandidateDisplayedInView = true;
				break; // Candidate found, no more search required
			}
		}
		return isCandidateDisplayedInView;
	}
}