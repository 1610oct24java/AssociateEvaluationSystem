package com.revature.test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.CandidateViewPage;
import com.revature.pages.LoginPage;
import com.revature.pages.UpdateCredentialsPage;

public class RecUpdateCredentials {
	
	WebDriver driver;
	LoginPage loginPage;
	CandidateViewPage candidateViewPage;
	UpdateCredentialsPage updateCreds;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get("http://localhost:8090/aes/login");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		loginPage = new LoginPage(driver);
		
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void testUpdatePassword() {
		candidateViewPage = new CandidateViewPage(driver);
		candidateViewPage.clickUpdateUserInfo();
		updateCreds = new UpdateCredentialsPage(driver);
		updateCreds.updatePassword("nickolas.jurczak@revature.com", "password", "p4ssword", "p4ssword");
		updateCreds.clickUpdate();
		updateCreds.clickLogout();
		loginPage.loginToAES("nickolas.jurczak@revature.com", "p4ssword");
		assertEquals(candidateViewPage.getTitle(), "Revature | AES");
		assertTrue(candidateViewPage.verifyUpdateCredentials());
		//fail("Not yet implemented");
	}

}
