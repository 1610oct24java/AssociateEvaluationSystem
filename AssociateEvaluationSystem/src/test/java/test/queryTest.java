package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilties.DataBaseConnection;
import java.sql.Connection;

public class queryTest {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn;
		PreparedStatement ps;
		
		deleteTestCandidate();
		
		
//		try {
//			String sql = "select * from aes_users";
//			conn = DataBaseConnection.getConnection();
//			ps = conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);
//			while(rs.next()) {
//				System.out.println(rs.getInt("USER_ID"));
//			}
//					
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

	}
	
	public static void deleteTestCandidate() {
		
		Connection conn;
		PreparedStatement ps;
		int userId = 0;
		
		try {
			String getUserIdSQL = "SELECT user_id FROM aes_users WHERE email = ?";
			conn = DataBaseConnection.getConnection();
			ps = conn.prepareStatement(getUserIdSQL);
			
			ps.setString(1, "test@test.com");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean testCandidateExist() {
		
		Connection conn;
		PreparedStatement ps;
		int userId = 0;
		
		try {
			String getUserIdSQL = "SELECT user_id FROM aes_users WHERE email = ?";
			conn = DataBaseConnection.getConnection();
			ps = conn.prepareStatement(getUserIdSQL);
			
			ps.setString(1, "test@test.com");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("USER_ID");
			}
			
			if(userId != 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

}

//get userid
//select user_id from aes_users where email = 'test@test.com';
// int userId = rs.getInt(userId);

//DELETE FROM aes_users WHERE userId = ? ;
//stmt.setInt(1, userId);
//stmt.executeUpdate();  

//DELETE FROM aes_security WHERE userId = ? ;
//stmt.setInt(1, userId);
//stmt.executeUpdate(); 

//DELETE FROM aes_assessment_auth WHERE userId = ? ;
//stmt.setInt(1, userId);
//stmt.executeUpdate(); 

//DELETE FROM aes_assessment WHERE userId = ? ;
//stmt.setInt(1, userId);
//stmt.executeUpdate(); 

//DELETE FROM aes_users WHERE userId = ? ;
//stmt.setInt(1, userId);
//stmt.executeUpdate(); 

