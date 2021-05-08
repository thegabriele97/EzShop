package it.polito.ezshop.model;
//Simone
import java.io.Serializable;

public class LoyaltyCard implements Serializable {
	private String ID;
	private Integer points;
	private Customer customer;
	
	public LoyaltyCard(String iD, Integer points, Customer customer) {
		this.setID(iD);
		this.addPoints(points);
		this.addCustomer(customer);
	}
	
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

	public Integer getPoints() {
		return points;
	}

	public void addPoints(Integer points) {
		this.points = points;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void addCustomer(Customer customer) {
		this.customer = customer;
	}

	
	
    
}
