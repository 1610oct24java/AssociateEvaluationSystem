package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CandidateViewPage {

	WebDriver driver;
	
	By registerCandidateLink = By.xpath("//a[@href='recruit']");
	By logoutButton = By.id("logout");
	
	public CandidateViewPage(WebDriver driver) {
		this.driver = driver;
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

}