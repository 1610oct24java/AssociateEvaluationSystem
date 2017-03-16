package com.revature.pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.revature.utilities.DataBaseConnection;

public class RegisterCandidatePage {

	WebDriver driver;
	
	By firstName = By.id("inputFirstName");
	By lastName = By.id("inputLastName");
	By email = By.id("inputEmail");
	By program = By.id("courseSelect");
	By register = By.id("btn-register-recruit");
	By viewCandidatesLink = By.xpath("//a[@href='view']");

	/**
	 * The driver for the register candidate page.
	 * @param driver particular to the register candidate page
	 */
	public RegisterCandidatePage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Type candidate first name in first name field.
	 * @param strFirstName candidate first name
	 */
	public void setFirstName(String strFirstName) {
		driver.findElement(firstName).sendKeys(strFirstName);
	}
	
	/**
	 * Type candidate last name in last name field.
	 * @param strLastName candidate last name
	 */
	public void setLastName(String strLastName) {
		driver.findElement(lastName).sendKeys(strLastName);
	}
	
	/**
	 * Type candidate email in email field.
	 * @param strFirstName candidate email
	 */
	public void setEmail(String strEmail) {
		driver.findElement(email).sendKeys(strEmail);
	}
	
	/**
	 * Select program for candidate.
	 * @param strProgram name of program
	 */
	public void selectProgram(String strProgram) {
		Select select = new Select(driver.findElement(program));
		select.selectByVisibleText(strProgram);
	}
	
	public void clickRegister() {
		driver.findElement(register).click();
	}
	
	public void clickViewCandidates() {
		driver.findElement(viewCandidatesLink).click();
	}
	
	/**
	 * Remove candidate from database.
	 * @param userEmail user email
	 */
	public void deleteCandidate(String userEmail) {
		
		Connection conn;
		PreparedStatement ps;
		int userId = 0;
		
		try {
			String getUserIdSQL = "SELECT user_id FROM aes_users WHERE email = ?";
			conn = DataBaseConnection.getConnection();
			ps = conn.prepareStatement(getUserIdSQL);
			
			ps.setString(1, userEmail);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("USER_ID");
			}
			
			if(userId != 0) {
				
				String deleteTestUserSQL = "DELETE FROM aes_users WHERE USER_ID = ? ";
				ps = conn.prepareStatement(deleteTestUserSQL);
				ps.setInt(1, userId);
				ps.executeQuery();
				
				String deleteTestUserSQL2 = "DELETE FROM aes_security WHERE USER_ID = ? ";
				ps = conn.prepareStatement(deleteTestUserSQL2);
				ps.setInt(1, userId);
				ps.executeQuery();
				
				String deleteTestUserSQL3 = "DELETE FROM aes_assessment WHERE USER_ID = ? ";
				ps = conn.prepareStatement(deleteTestUserSQL3);
				ps.setInt(1, userId);
				ps.executeQuery();
				
				String deleteTestUserSQL4 = "DELETE FROM aes_assessment_auth WHERE USER_ID = ? ";
				ps = conn.prepareStatement(deleteTestUserSQL4);
				ps.setInt(1, userId);
				ps.executeQuery();	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Verify if candidate exists in database.
	 * @param userEmail user email
	 * @return boolean value if candidate exists or not
	 */
	public boolean testCandidateExist(String userEmail) {
		
		Connection conn;
		PreparedStatement ps;
		int userId = 0;
		
		try {
			String getUserIdSQL = "SELECT user_id FROM aes_users WHERE email = ?";
			conn = DataBaseConnection.getConnection();
			ps = conn.prepareStatement(getUserIdSQL);
			
			ps.setString(1, userEmail);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("USER_ID");
			}
			
			if(userId != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Register candidate in the database
	 * @param firstName candidate first name
	 * @param lastName candidate last name
	 * @param email candidate email
	 * @param program candidate program
	 */
	public void registerCandidate(String firstName, String lastName, String email, String program) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.selectProgram(program);
		this.clickRegister();
	}
}