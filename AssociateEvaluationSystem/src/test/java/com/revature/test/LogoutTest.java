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
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://35.162.177.133:8090/aes/login");
		loginPage = new LoginPage(driver);
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		candidateViewPage = new CandidateViewPage(driver);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void logoutTest() {
		
		try {
			Thread.sleep(1000);
			candidateViewPage.clickLogoutButton();
			
			// Make sure its on the login page after logout
			Thread.sleep(1000);
			assertEquals("Revature | AES", driver.getTitle());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}	
	}
}
