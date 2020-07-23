package com.revature.beans;

public class Offers {
	private int id;
	private int person;
	private int car;
	private int total;
	private String state;
	private int downPayment;
	public static int staticTotal = 15000;
	public static int staticDownPayment = 2000;
	
	public Offers() {
		super();
	}
	
	public Offers(Integer id, Integer p, Integer c, String state, Integer downPayment, Integer total) {
		super();
		this.id = id;
		this.person = p;
		this.car = c;
		this.state = state;
		this.downPayment = downPayment;
		this.total = total;
}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPerson() {
		return person;
	}
	public void setPerson(int i) {
		this.person = i;
	}
	public int getC() {
		return car;
	}
	@Override
	public String toString() {
		return "Offers [id=" + id + ", person=" + person + ", car=" + car + ", total=" + total + ", state=" + state
				+ ", downPayment=" + downPayment + "]";
	}

	public void setC(int c) {
		this.car = c;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(int downPayment) {
		this.downPayment = downPayment;
	}
	
	//CALCULATES MONTHLY PAYMENT
	public static int monthlyPaymentCalc(int m){
		return (staticTotal - staticDownPayment) / m;
	}
	
}
