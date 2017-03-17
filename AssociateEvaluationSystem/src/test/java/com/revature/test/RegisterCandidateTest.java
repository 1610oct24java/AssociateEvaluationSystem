package com.revature.test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.CandidateViewPage;
import com.revature.pages.LoginPage;
import com.revature.pages.RegisterCandidatePage;

public class RegisterCandidateTest {
	
	WebDriver driver;
	LoginPage loginPage;
	CandidateViewPage candidateViewPage;
	RegisterCandidatePage registerCandidatePage;
	
	/**
	 * Specify candidate to be tested
	 */
	
	String firstName = "test";
	String lastName = "test";
	String email = "test@jonsfakemail.com";
	String program = "Java";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		/**
		 * Setup driver and page objects and login to AES
		 */
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get("http://localhost:8090/aes/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		loginPage = new LoginPage(driver);
		candidateViewPage = new CandidateViewPage(driver);
		registerCandidatePage = new RegisterCandidatePage(driver);
		
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		
		// Delete candidate before creating the same candidate
		registerCandidatePage.deleteCandidate(email);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Test
	public void registerCandidateDBTest()  {
		
		/**
		 * Register candidate and check if candidate exist in database
		 */
		
		candidateViewPage.clickRegisterCandidateLink();
		registerCandidatePage.registerCandidate(firstName, lastName, email, program);
		explicitWait(3);
		assertTrue(registerCandidatePage.testCandidateExist(email));
	}

	@Test
	public void registerCandidateGUITest()  {
		
		/**
		 * Register candidate and check if candidate shows up in the view
		 */
		
		candidateViewPage.clickRegisterCandidateLink();
		explicitWait(3);
		registerCandidatePage.registerCandidate(firstName, lastName, email, program);
		registerCandidatePage.clickViewCandidates();		
		explicitWait(3);
		driver.navigate().refresh();
		explicitWait(3);
		assertTrue(candidateViewPage.verifyCandidateExistInView(firstName, lastName, email));
	}
	
	private void explicitWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
