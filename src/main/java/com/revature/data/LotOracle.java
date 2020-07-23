package com.revature.data;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.beans.Offers;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.LogUtil;

public class LotOracle implements LotDAO {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private static Logger log = Logger.getLogger(LotOracle.class);
	
	
	// ADDS CAR TO DATABASE
	public static void addCar(Integer id, String make, String color, String model, Integer year, Integer price, Integer carMileage) throws SQLException {
		Car c = new Car(id, make, color, model, year, price, carMileage);
		
		String sql = "INSERT INTO carLot (carMake, carModel, carColor, carMileage, carYear, carPrice)" +  "VALUES (?, ?, ?, ?, ?, ?)";
		Connection con = cu.getConnection();
		PreparedStatement stmt = null;
		
		try {
			con.setAutoCommit(false);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, make);
			stmt.setString(2, model);
			stmt.setString(3, color);
			stmt.setInt(4, carMileage);
			stmt.setInt(5, year);
			stmt.setInt(6, price);
			
			stmt.executeUpdate();
			con.commit();	
		} catch (Exception e) {
			LogUtil.rollback(e, con, LotOracle.class);
			System.out.println(e);
			
		} finally { con.close(); }
		
	}
	
	// REMOVE CAR BASED ON INPUT ID
	public static void removeCar(int id){
		log.trace("Removing car of id" + id);
		Connection con = cu.getConnection();
		try {
			con.setAutoCommit(false);
			String sql = "delete from carLot where cID=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);
			int test = pstm.executeUpdate();
			
			// CHECKING IF DELETION SUCCESSFUL, USING TEST VARIABLE
			if (test ==1 ) { log.trace("Car successfully deleted"); con.commit(); }
				else { log.trace("Car deletion failed, please try again"); con.rollback(); }
		} catch (SQLException e) {
				LogUtil.rollback(e, con, LotOracle.class);
		} finally {
			try {
					con.close();
			} catch (SQLException e) {
				LogUtil.logException(e, LotOracle.class);
			}
		}
					
	}
	
	// GET CAR BY ID
	public static Car getCarByCarID(int id){
		log.trace("Retrieving car of id " + id);
		Connection con = cu.getConnection();
		Car k = new Car();

		try {
			con.setAutoCommit(false);
			String sql = "Select * from carLot where cID = '" + id + "'";
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery(sql);
			
			if (rs.next()) {
				log.trace("Car Found!");
				k.setId(rs.getInt("cID"));
				k.setMake(rs.getString("carMake"));
				k.setModel(rs.getString("carModel"));
				k.setColor(rs.getString("carColor"));
				k.setYear(rs.getInt("carYear"));
				k.setPrice(rs.getInt("carPrice"));
				k.setCarMileage(rs.getInt("carMileage"));
				return k;
			}		
			} catch (Exception e) { LogUtil.logException(e, LotOracle.class);}
		
			return null;
	}
	
	
	
	// RETRIEVE LIST OF CARS ON THE LOT
	public static List<Car> getAllCars() throws SQLException{
		List<Car> cars = new ArrayList<>();
		log.trace("Getting all available cars on lot.");
		
		try (Connection con = cu.getConnection()) {
			String sql = "SELECT * from carLot";
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery(sql);
			
			while (rs.next()){
					Car k = new Car();
					k.setId(rs.getInt("cID"));
					k.setMake(rs.getString("carMake"));
					k.setModel(rs.getString("carModel"));
					k.setColor(rs.getString("carColor"));
					k.setYear(rs.getInt("carYear"));
					k.setPrice(rs.getInt("carPrice"));
					k.setCarMileage(rs.getInt("carMileage"));
					// set owner
					cars.add(k); }
		} catch (Exception e){
			LogUtil.logException(e, LotOracle.class);	
		}
		return cars;
		
	}
	
	// RETRIEVE CARS THE CUSTOMER OWNS
	public static List<Car> getCarsOfCustomer(int id) throws SQLException{
		List<Car> cars = new ArrayList<>();
		log.trace("Getting cars that belong to customer.");
		
		try (Connection con = cu.getConnection()) {
			String sql = "SELECT * from carLot where ownerID = '" + id + "'";
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery(sql);
			
			while (rs.next()){
					Car k = new Car();
					k.setId(rs.getInt("cID"));
					k.setMake(rs.getString("carMake"));
					k.setModel(rs.getString("carModel"));
					k.setColor(rs.getString("carColor"));
					k.setYear(rs.getInt("carYear"));
					k.setPrice(rs.getInt("carPrice"));
					k.setCarMileage(rs.getInt("carMileage"));
					k.setOwnerID(rs.getInt("ownerID"));
					cars.add(k);}
		} catch (Exception e){
			LogUtil.logException(e, LotOracle.class);	
		}
		return cars;
		
	}
	
	public static List<Offers> getRemainingPayments(int id){
		log.trace("Getting the remaining payments for customer.");
		List<Offers> offer = new ArrayList<>();
		
		try (Connection con = cu.getConnection()) {
			String sql = "SELECT * from offers where userID = '" + id + "'";
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery(sql);
			
			while (rs.next()){
				log.trace("Account with remaining payments found!");
				Offers o = new Offers();
				o.setId(rs.getInt("id"));
				o.setPerson(rs.getInt("userID"));
				o.setC(rs.getInt("carID"));
				o.setTotal(rs.getInt("offer"));
				o.setState("accepted");
				o.setDownPayment(rs.getInt("downPayment"));
				offer.add(o);} 
			
		} catch (Exception e){ LogUtil.logException(e, LotOracle.class); }
		
		return offer;
	}
		
	
	
}
