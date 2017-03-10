package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	WebDriver driver;
	
	By email = By.id("input-email");
	By password = By.id("input-password");
	By login = By.className("btn-revature");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setEmail(String strEmail) {
		driver.findElement(email).sendKeys(strEmail);
	}
	
	public void setPassword(String strPassword) {
		driver.findElement(password).sendKeys(strPassword);
	}
	
	public void clickLogin() {
		driver.findElement(login).click();
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void loginToAES(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
		this.clickLogin();
	}

}