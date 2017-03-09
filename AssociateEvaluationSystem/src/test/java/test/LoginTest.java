package test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.LoginPage;

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
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://35.162.177.133:8090/aes/login");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void loginTest() {
		loginPage = new LoginPage(driver);
		loginPage.loginToAES("nickolas.jurczak@revature.com", "password");
		
		try {
			Thread.sleep(3000);
			
			// Make sure login is successful
			assertEquals("AES | Recruiter Home", loginPage.getTitle());	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
