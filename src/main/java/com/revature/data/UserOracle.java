package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.data.UserDAO;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.beans.Offers;
import com.revature.beans.User;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class UserOracle implements UserDAO {
	private static Logger log = Logger.getLogger(UserOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	
	// REGISTER USER TO DATABASE
	public static int addUser(String username, String passwrd, String stringAccess) {
		int key = 0;
		int result = 0;
		log.trace("Registering account with the database.");
		Connection conn = cu.getConnection();
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(passwrd);
		u.setDataAccess(stringAccess);
		
		try{
			conn.setAutoCommit(false);
			String sql = "insert into appUser (username,passwrd,stringAccess) values(?,?,?)";
			String[] keys = {"usrID"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setString(1,username);
			pstm.setString(2,passwrd);
			pstm.setString(3,stringAccess);
			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("User account successfully registered.");
				key = rs.getInt(1);
				u.setID(key);
				result += 1;
				conn.commit();
			} else {
				log.trace("User account not registered.");
				conn.rollback(); }
			
		} catch(SQLException e) { LogUtil.rollback(e,conn,UserOracle.class); }
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,UserOracle.class);
			}
		}
		return result;
	}

	// GETTING ALL USERS IN THE DATABASE
	public static List<User> retrieveUsers() {
		List<User> people = new ArrayList<>();
		log.trace("Getting all registered users in the database");
		
		try (Connection con = cu.getConnection()){
			String sql = "SELECT * from appUser";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			while (rs.next()){
				User u = new User();
				u.setID(rs.getInt("usrID"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwrd"));
				u.setDataAccess(rs.getString("stringAccess"));
				people.add(u);
			}
				
		} catch (Exception e) {
			// log exception
			LogUtil.logException(e, UserOracle.class);
		}
		
		return people;		
	}
	
}
