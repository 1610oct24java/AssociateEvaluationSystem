package com.revature.aes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AESJDBCImpl implements AESJDBC {
	
	private JdbcTemplate jdbc;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

	@Override
	public Integer getNumOfQuestions(Integer category, Integer type) {
		// TODO Auto-generated method stub
		Integer total = 0;
		try {
		    String sql = "SELECT COUNT(*) "
					+ "FROM AES_QUESTION AQ "
					+ "INNER JOIN AES_QUESTION_CATEGORY AQC "
					+ "ON AQ.QUESTION_ID = AQC.QUESTION_ID "
					+ "GROUP BY AQ.QUESTION_FORMAT_ID , AQC.CATEGORY_ID "
					+ "HAVING AQC.CATEGORY_ID = ? AND AQ.QUESTION_FORMAT_ID = ?";
			PreparedStatement ps = jdbc.getDataSource().getConnection().prepareStatement(sql);
			ps.setInt(1, category);
			ps.setInt(2, type);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				total = rs.getInt(1);
			}
			

		} catch (Exception e) {
			throw new NullPointerException();
		}
	    
	   
	    return total;
	}

	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
}
