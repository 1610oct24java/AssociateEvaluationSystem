package pages;

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
	By logoutButton = By.id("logout");
	
	
	public AdminEmployeeViewPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void clickRegisterEmployeeLink() {
		driver.findElement(registerEmployeeLink).click();
	}
	
	public void clickUpdateEmployeeLink() {
		driver.findElement(updateEmployeeLink).click();
	}
	
	public void clickLogoutButton() {
		driver.findElement(logoutButton).click();
	}
	
	public void selectSearchOrder(String order) {
		WebElement wb = driver.findElement(searchOrderDropDown);
		Select se = new Select(wb);
		se.selectByVisibleText(order);
	}
}