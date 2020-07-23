package com.revature.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.revature.beans.Menus;
import com.revature.beans.User;
import com.revature.data.UserOracle;

public class Driver {
	
	private static int input;
	
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {	
			
		startMenu();	
	}
	
 	public static void startMenu() throws SQLException {
		System.out.println("*********************************");
		System.out.println("WELCOME TO THE AUTO DEALERSHIP  \n");
		System.out.println("*********************************");
		System.out.println("1.LOGIN\n" + "2.REGISTER\n");
		
		System.out.println("Please select an option: ");		
		input = Integer.parseInt(scan.nextLine());
		
		boolean check = true;
		
		switch(input) {	
			case 1: check = false; login(); break; 
			case 2: check = false; register(); break;
			default: System.out.println("Please enter valid input"); startMenu(); break;
			}	
		
	}

	private static void register() throws SQLException {
		System.out.println("**********************");
		System.out.println("\nREGISTER PAGE\n");
		System.out.println("**********************");
		System.out.println("Enter a username: ");
		String username = scan.nextLine();
		
		System.out.println("Enter a password: ");
		String password = scan.nextLine();
		
		int x = UserOracle.addUser(username, password, "customer");
		
		// CHECKING IF REGISTRATION IS SUCCESSFUL
		if (x == 0) {System.out.println("\n\nUsername is already taken. Please try again"); register();}
			else { System.out.println("\n\nRegistration successful!\n" + "Please login with your information\n"); login();}
	
	}

	public static void login() throws SQLException {
		System.out.println("\nLOGIN PAGE\n");
		System.out.println("Enter Username:");
		String username = scan.nextLine();
		username = username.toLowerCase();
		
		System.out.println("Enter a password: ");
		String password = scan.nextLine();
		password = password.toLowerCase();
		
		// RETRIEVING ALL USERS AND STORING INTO LIST "checkUsername"
		List<User> checkUsername = new ArrayList<>();		
		checkUsername = UserOracle.retrieveUsers();
		
		// SEARCHING FOR USER IN DATABASE. IF FOUND, OPENS EITHER EMPLOYEE OR CUSTOMER MENU
		for(User i : checkUsername){
			if(i.getUsername().equals(username) && i.getPassword().equals(password)){
				if(i.getDataAccess().equals("customer")) { Menus.CustomerMenu(i);}
				
				else if (i.getDataAccess().equals("employee")) {Menus.EmployeeMenu(i);}			
			}
		//{System.out.println("Username/Password not found, please try again"); login(); }
		}
		
		}
		
	}
