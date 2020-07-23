package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Offers;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class OfferOracle implements OfferDAO {
	private static Logger log = Logger.getLogger(UserOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	
	// CALLING STORED PROCEDURE, TO ACCEPT OFFER/REJECT OFFERS RELATED TO THE ACCEPTED OFFER.
	public static void acceptOffer(int o) {
		log.trace("Attempting to run Stored Procedure to accept offer in SQL File.");
		try (Connection con = cu.getConnection()) {
			String sqlString = "call acceptOffer(?)";
			CallableStatement cstmt = con.prepareCall(sqlString);
			cstmt.setInt(1, o);
			cstmt.executeUpdate();
			log.trace("Successfully completed stored procedure: acceptOffer");
		} catch(Exception e) {
			LogUtil.logException(e, OfferOracle.class);
		}
	}
	
	// REJECT OFFER OF ID (INT O)
	public static void rejectOffer(int o){
		log.trace("Updated offer " + o + " in database to 'rejected'");
		
		Connection con = cu.getConnection();
		try {
			con.setAutoCommit(false);
			String sql = "update offers set statee = 'rejected' where id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, o);
			int test = pstm.executeUpdate();
			
			// CHECKING IF DELETION SUCCESSFUL, USING TEST VARIABLE
			if (test ==1 ) { log.trace("Offer successfully updated"); con.commit(); }
				else { log.trace("Offer rejecton failed, please try again"); con.rollback(); }
		} catch (SQLException e) {
				LogUtil.rollback(e, con, OfferOracle.class);
		} finally {
			try {
					con.close();
			} catch (SQLException e) {
				LogUtil.logException(e, OfferOracle.class);
			}
		}
		
	}
	
	
	
	// GETTING ALL OFFERS FROM THE DATABASE
	public static List<Offers> retrieveOffers() {
		List<Offers> offers = new ArrayList<>();
		log.trace("Getting all offers in the database");
		
		try (Connection con = cu.getConnection()){
			String sql = "SELECT * from offers";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			while (rs.next()){
				log.trace("Offer found!");
				Offers o = new Offers();
				o.setId(rs.getInt("id"));
				o.setPerson(rs.getInt("userID"));
				o.setC(rs.getInt("carID"));
				o.setTotal(rs.getInt("offer"));
				o.setState(rs.getString("statee"));
				o.setDownPayment(rs.getInt("downPayment"));
				offers.add(o);
			}
				
		} catch (Exception e) {
			// log exception
			LogUtil.logException(e, UserOracle.class);
		}
		
		return offers;		
	}
	
	// ADDING OFFER TO THE DATABASE
	public static int addOffer(Offers o){
		int x = 0;
		log.trace("Inserting offer into database");
		log.trace(o);
		Connection con = cu.getConnection();
		try {	
				con.setAutoCommit(false);
				String sql = "insert into offers (userID, carID, offer, statee, downPayment) values (?,?,?,?,?)";
				String[] keys = { "id" };
				PreparedStatement pstm = con.prepareStatement(sql, keys);
				pstm.setInt(1, o.getPerson());
				pstm.setInt(2, o.getC());
				pstm.setInt(3, o.getTotal());
				pstm.setString(4, o.getState());
				pstm.setInt(5, o.getDownPayment());
				
				pstm.executeUpdate();
				ResultSet rs = pstm.getGeneratedKeys();
				
				if (rs.next()) {
					log.trace("Offer successfully created.");
					x = rs.getInt(1);
					o.setId(x);
					con.commit();
				} else {
						log.trace("Offer failed to be created.");
						con.rollback();  }
				
			}	catch (SQLException e) { LogUtil.rollback(e, con, OfferOracle.class); }
				finally {
						try { con.close();
						
						} catch (SQLException e) {
							LogUtil.logException(e, OfferOracle.class);
						}
				}
		
		
		return x;
	}
	
	
	
}
	
