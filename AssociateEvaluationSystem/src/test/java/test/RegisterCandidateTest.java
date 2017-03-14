package test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.CandidateViewPage;
import pages.LoginPage;
import pages.RegisterCandidatePage;

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
		loginPage = new LoginPage(driver);
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		candidateViewPage = new CandidateViewPage(driver);
		registerCandidatePage = new RegisterCandidatePage(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	@Ignore
	@Test
	public void registerCandidateDatabaseTest()  {
		
		try {
			Thread.sleep(3000);

			// Remove test candidate before recreating
			registerCandidatePage.deleteTestCandidate();

			Thread.sleep(3000);
			registerCandidatePage.registerCandidate("test", "test", "test@test.com", "SDET");
			
			// Check if test candidate is in the database
			assertTrue(registerCandidatePage.testCandidateExistDB());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void registerCandidateGUITest()  {
		try {
			Thread.sleep(3000);

			// Remove test candidate before recreating
			registerCandidatePage.deleteTestCandidate();
			candidateViewPage.clickRegisterCandidateLink();
			
			Thread.sleep(3000);
			registerCandidatePage.registerCandidate("test", "test", "test@test.com", "SDET");
			
			registerCandidatePage.clickViewCandidateLink();
			
			Thread.sleep(3000);
			driver.navigate().refresh();
			Thread.sleep(3000);
			assertTrue(candidateViewPage.testCandidateExistView());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
