package com.revature.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.CandidateViewPage;
import com.revature.pages.LoginPage;

public class LogoutTest {
	
	WebDriver driver;
	LoginPage loginPage;
	CandidateViewPage candidateViewPage;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		/**
		 * Setup driver and page objects and login to AES.
		 */
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get("http://localhost:8090/aes/login");
		
		loginPage = new LoginPage(driver);
		candidateViewPage = new CandidateViewPage(driver);
		
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void logoutTest() {
		
		/**
		 * Determine if user is logged out by checking home page title
		 */
		
		explicitWait(3);
		candidateViewPage.clickLogoutButton();
		explicitWait(3);
		
		// Check if home page title is matched
		assertEquals("Revature | AES", driver.getTitle());
		
	}
	
	private void explicitWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
