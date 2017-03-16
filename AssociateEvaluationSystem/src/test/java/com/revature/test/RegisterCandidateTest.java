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
		driver.get("http://localhost:8090/aes/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		loginPage = new LoginPage(driver);
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		candidateViewPage = new CandidateViewPage(driver);
		registerCandidatePage = new RegisterCandidatePage(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	@Test
	public void registerCandidateDBTest()  {
		candidateViewPage.clickRegisterCandidateLink();
		registerCandidatePage.registerCandidate("test", "test", "test@jonsfakemail.com", "SDET");
		explicitWait(10);
		assertTrue(registerCandidatePage.testCandidateExist("test@jonsfakemail.com"));
		registerCandidatePage.clickViewCandidates();
		explicitWait(10);
		registerCandidatePage.deleteCandidate("test@jonsfakemail.com");
	}
	
	private void explicitWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void registerCandidateGUITest()  {
		
		explicitWait(10);

		// Remove test candidate before recreating
		registerCandidatePage.deleteCandidate("test@jonsfakemail.com");
		candidateViewPage.clickRegisterCandidateLink();
		
		explicitWait(10);
		registerCandidatePage.registerCandidate("test", "test", "test@jonsfakemail.com", "SDET");
		
		registerCandidatePage.clickViewCandidates();
		
		explicitWait(10);
		driver.navigate().refresh();
		explicitWait(10);
		assertTrue(candidateViewPage.testCandidateExistView());
		
	}
}
