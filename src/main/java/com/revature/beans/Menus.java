package com.revature.beans;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.revature.data.LotOracle;
import com.revature.data.OfferOracle;
import com.revature.driver.Driver;
import com.revature.beans.Offers;

public class Menus {
	
	static Scanner scan = new Scanner(System.in);
	
	private static int input;
	
	
	public static void CustomerMenu(User u) throws SQLException{
		System.out.println("\nWELCOME TO THE CUSTOMER PAGE\n");
		System.out.println("1. View Car Lot");
		System.out.println("2. View my remaining payments.");
		System.out.println("3. View cars I own.\n");
		System.out.println("Please select an option.");
		
		input = Integer.parseInt(scan.nextLine());
		
		if	(input == 1) { lotMenu(u); }
		else if (input == 2) {remainingPaymentsMenu(u); }
		else if (input == 3) { ownedCars(u); }
		else {System.out.println("Input not recognized, please try again"); CustomerMenu(u);};
	}
		
	private static void ownedCars(User u) throws SQLException {
		System.out.println("CARS YOU CURRENTLY OWN\n");
		
		// GET CARS THAT THE USER OWNS
		List<Car> cars = LotOracle.getCarsOfCustomer(u.getID());
		for (Car element : cars) {
		    System.out.println(element);
		}
		
		remainingPaymentsMenu(u);
		
	}

	private static void lotMenu(User u) throws SQLException {
		System.out.println("CARS AVAILABLE\n");
		
		// DISPLAY ALL THE CARS IN DEALERSHIP
		List<Car> cars = LotOracle.getAllCars();
		for (Car element : cars) {
		    System.out.println(element);
		}
		
		System.out.println("\nIf interested in making an offer for a car, enter the car ID");
		input = Integer.parseInt(scan.nextLine());
		if (input != 1){ offerMenu(input, u);}
			else{System.out.println("Input not valid, please try again"); lotMenu(u);}
		
	}

	private static void offerMenu(int id, User u) {
		//CAR THAT IS SELECTED IS PRINTED
		System.out.println("Car Selected\n");
		Car c = LotOracle.getCarByCarID(id);
		System.out.println(c);
		
		System.out.println("Enter in your offer: ");	
		input = Integer.parseInt(scan.nextLine());
		
		System.out.println("\nEnter down payment amount: ");	
		int dPayment = Integer.parseInt(scan.nextLine());
		
		System.out.println("\n Enter months of financing desired: ");
		int months = Integer.parseInt(scan.nextLine());
			
		// CREATING OFFER BASED ON INPUT
		Offers o = new Offers(1, u.getID(), c.getId(), "pending", dPayment, input);
		
		// ADDING OFFER
		OfferOracle.addOffer(o);
		
		// if valid, print
		System.out.println("\nYour offer has been submitted!");
	}

	private static void remainingPaymentsMenu(User u) {
		
		List<Offers> o = LotOracle.getRemainingPayments(u.getID());
		
		System.out.println(o);		
		System.out.println("\nPayments per month : 500");
		
	}

	public static void EmployeeMenu(User u) throws SQLException{
		System.out.println("******************************");
		System.out.println("\nWELCOME TO THE EMPLOYEE PAGE\n");
		System.out.println("******************************");
		System.out.println("1. Add car to car lot.");
		System.out.println("2. Remove car from car lot.");
		System.out.println("3. View all payments.");
		System.out.println("4. View offers.\n");
		System.out.println("Please select an option");
		
		input = Integer.parseInt(scan.nextLine());
		
		if	(input == 1) { addCarMenu(); }
		else if (input == 2) {removeCarMenu(); }
		else if (input == 3) { paymentMenu(u); }
		else if (input == 4) { seeOfferMenu(u); }
			else {System.out.println("Input not recognized, please try again"); EmployeeMenu(u);};
		
		
		}
	
	private static void seeOfferMenu(User u) throws SQLException {
		
		// PRINT ALL OFFERS
		List<Offers> offer = OfferOracle.retrieveOffers();
		System.out.println(" ");
		for (Offers i : offer) {
		    System.out.println(i.toString());
		}
	
		System.out.println("\nSelect an offer based on the id; to accept/reject");
		input = Integer.parseInt(scan.nextLine());
		
		System.out.println("\nOffer of id " + input + " selected, accept or reject?");
		String selection = scan.nextLine();
		
		if (selection.equals("accept")) {
			OfferOracle.acceptOffer(input);
		    // change status of car to car owned by user
			// reject all pending offers on that car
			System.out.println("offer has been accepted!");
		}
		else if (selection.equals("reject")){
			OfferOracle.rejectOffer(input);
			System.out.println("offer has been rejected!");
		}
		else if(input == 0) { EmployeeMenu(u);}
			else {System.out.println("Input not recognized, please try again"); seeOfferMenu(u);}
		
	}
	

	private static void removeCarMenu() throws SQLException {
		System.out.println("Car List:\n");
		
		// PRINTS LIST OF CARS
		List<Car> cars = LotOracle.getAllCars();
		for (Car element : cars) {
		    System.out.println(element);
		}
		
		
		System.out.println("\nSelect ID of car to be removed:\n");
		input = Integer.parseInt(scan.nextLine());
		
		// use database to remove car based on input
		LotOracle.removeCar(input);
	}

	private static void paymentMenu(User u) {
		System.out.println("PAYMENT PAGE\n");
		List<Offers> o = LotOracle.getRemainingPayments(u.getID());
		
		System.out.println(o);		
		
	}

	private static void addCarMenu() throws SQLException {
		System.out.println("Add A CAR PAGE\n");
		
		System.out.println("Type the make: ");
		String make = scan.nextLine();
		
		System.out.println("Type the model: ");
		String model = scan.nextLine();
		
		System.out.println("Type the color: ");
		String color = scan.nextLine();
		
		System.out.println("Type the year: ");
		int year = Integer.parseInt(scan.nextLine());
		
		System.out.println("Type the price: ");
		int price = Integer.parseInt(scan.nextLine());
		
		System.out.println("Type the mileage: ");
		int mileage = Integer.parseInt(scan.nextLine());
		
		// store car object into database based on the input
		
		LotOracle.addCar(1, make, color, model, year, price, mileage);
		
		// if car added successfully
		System.out.println("Car has been added, thanks!");
		Driver.login();
	}
	
}

