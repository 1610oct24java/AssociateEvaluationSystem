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

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8090/aes/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Test
	public void registerCandidateDBTest()  {
		loginPage = new LoginPage(driver);
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		candidateViewPage = new CandidateViewPage(driver);
		candidateViewPage.clickRegisterCandidateLink();
		registerCandidatePage = new RegisterCandidatePage(driver);
		registerCandidatePage.deleteCandidate("test@jonsfakemail.com");
		registerCandidatePage.registerCandidate("test", "test", "test@jonsfakemail.com", "SDET");
		explicitWait(3);
		assertTrue(registerCandidatePage.testCandidateExist("test@jonsfakemail.com"));
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
		loginPage = new LoginPage(driver);
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		explicitWait(5);
		// Remove test candidate before recreating
		registerCandidatePage.deleteCandidate("test@jonsfakemail.com");
		candidateViewPage = new CandidateViewPage(driver);
		candidateViewPage.clickRegisterCandidateLink();
		explicitWait(5);
		registerCandidatePage = new RegisterCandidatePage(driver);
		registerCandidatePage.registerCandidate("test", "test", "test@jonsfakemail.com", "SDET");
		registerCandidatePage.clickViewCandidates();		
		explicitWait(5);
		driver.navigate().refresh();
		explicitWait(5);
		assertTrue(candidateViewPage.verifyCandidateExistInView("test", "test", "test@jonsfakemail.com"));
	}
}
