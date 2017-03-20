package com.revature.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.AdminEmployeeViewPage;
import com.revature.pages.CandidateViewPage;
import com.revature.pages.LoginPage;

public class LoginTest {
	
	WebDriver driver;
	LoginPage loginPage;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		/**
		 * Setup driver and login to AES
		 */
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		loginPage = new LoginPage(driver);
		
		driver.get("http://localhost:8090/aes/login");
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}

	@Test
	public void recruiterLoginTest() {
		
		/**
		 * Determine if recruiter can login by checking the title
		 * of the candidate view page
		 */
		
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		CandidateViewPage cvp = new CandidateViewPage(driver);
		explicitWait(5);
		assertEquals("AES | Recruiter Home", cvp.getTitle());
	}
	
	@Test
	public void adminLoginTest() {
		
		/**
		 * Determine if trainer can login by checking the title
		 * of the employee view page
		 */
		
		loginPage.setEmail("trainers@revature.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		AdminEmployeeViewPage aevp = new AdminEmployeeViewPage(driver);
		explicitWait(5);
		assertEquals("AES | Admin Home", aevp.getTitle());
		
	}
	
	private void explicitWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}
