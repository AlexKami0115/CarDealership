package com.revature.data;

public interface LotDAO {
	
	public static void addCar(Integer id, String make, String color, String model, Integer year, Integer price, Integer carMileage) {}
	
	public static void getAllCars() {};
	
	public static void removeCar(int id){};
	
	public static void getCarByCarId(int id){};
	
	public static void getCarsOfCustomer(int id){};
	
	public static void getRemainingPayments(int id){};

}
