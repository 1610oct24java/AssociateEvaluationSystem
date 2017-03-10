package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterCandidatePage {

	WebDriver driver;
	
	By firstName = By.id("inputFirstName");
	By lastName = By.id("inputLastName");
	By email = By.id("inputEmail");
	By program = By.id("courseSelect");
	By register = By.id("btn-register-recruit");
	
	public RegisterCandidatePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setFirstName(String strFirstName) {
		driver.findElement(firstName).sendKeys(strFirstName);
	}
	
	public void setLastName(String strLastName) {
		driver.findElement(lastName).sendKeys(strLastName);
	}
	
	public void setEmail(String strEmail) {
		driver.findElement(email).sendKeys(strEmail);
	}
	
	public void selectProgram(String strProgram) {
		Select select = new Select(driver.findElement(program));
		select.selectByVisibleText(strProgram);
	}
	
	public void clickRegister() {
		driver.findElement(register).click();
	}
	
	public void registerCandidate(String firstName, String lastName, String email, String program) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.selectProgram(program);
		this.clickRegister();
	}
}