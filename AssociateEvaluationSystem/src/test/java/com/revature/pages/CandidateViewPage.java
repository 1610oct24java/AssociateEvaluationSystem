package com.revature.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CandidateViewPage {

	WebDriver driver;
	
	By registerCandidateLink = By.xpath("//a[@href='recruit']");
	By logoutButton = By.id("logout");
	
	public CandidateViewPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void clickRegisterCandidateLink() {
		driver.findElement(registerCandidateLink).click();
	}
	
	public void clickLogoutButton() {
		driver.findElement(logoutButton).click();
	}
	
	public boolean verifyCandidate(String name, String email) {
		boolean result = false;
		
		driver.findElement(By.xpath(""));
		
		return result;
	}
	
	public boolean testCandidateExistView() {
		
		boolean isCandidateDisplayedInView = false;
		
		List<WebElement> elements = driver.findElements(By.xpath("//tr[@ng-repeat='candidate in candidates']"));

		for(int i=0;i<elements.size(); i++) {
			WebElement element = elements.get(i);

			String[] str = element.getText().split(" ");
			String lastName = str[0].split(",")[0];
			String firstName = str[1];
			String email = str[2];
			
			if(lastName.equals("test") && firstName.equals("test") && email.equals("test@test.com")) {
				isCandidateDisplayedInView = true;
			}

		}
		
		return isCandidateDisplayedInView;
	}

}