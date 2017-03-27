package com.revature.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.AdminEmployeeViewPage;
import com.revature.pages.LoginPage;

public class AdminViewTest {

	WebDriver driver;
	LoginPage loginPage;
	AdminEmployeeViewPage adminView;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		loginPage = new LoginPage(driver);
		
		driver.get("http://localhost:8090/aes/login");
		
		adminView = new AdminEmployeeViewPage(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() {
	}

}
