package com.revature.beans;

public class Car {
	private Integer id;
	private String color;
	private String make;
	private String model;
	private Integer year;
	private Integer price;
	private Integer carMileage;
	private Integer ownerID;

	public Car() {
		super();
	}


	public Car(Integer id, String make, String color, String model, Integer year, Integer price, Integer carMileage) {
			super();
			this.id = id;
			this.color = color;
			this.make = make;
			this.model = model;
			this.year = year;
			this.price = price;
			this.carMileage = carMileage;
	}


	public Integer getCarMileage() {
		return carMileage;
	}


	public void setCarMileage(Integer carMileage) {
		this.carMileage = carMileage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getOwnerID() {
		return ownerID;
	}


	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownerID == null) ? 0 : ownerID.hashCode());
		result = prime * result + ((carMileage == null) ? 0 : carMileage.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (ownerID == null) {
			if (other.ownerID != null)
				return false;
		} else if (!ownerID.equals(other.ownerID))
			return false;
		if (carMileage == null) {
			if (other.carMileage != null)
				return false;
		} else if (!carMileage.equals(other.carMileage))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Car [id=" + id + ", color=" + color + ", make=" + make + ", model=" + model + ", year=" + year
				+ ", price=" + price + ", carMileage=" + carMileage + ", ownerID = " + ownerID + "]";
	}
}

