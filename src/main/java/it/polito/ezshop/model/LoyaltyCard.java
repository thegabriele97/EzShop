package it.polito.ezshop.model;
//Simone
import java.io.Serializable;

public class LoyaltyCard implements Serializable {
	private String ID;
	private Integer points;
	private Customer customer;
	
	public LoyaltyCard(String iD, Integer points, Customer customer) {
		this.setID(iD);
		this.points = points;
		this.addCustomer(customer);
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {

		if (iD == null || iD.isEmpty() || iD.length() != 10 || !iD.chars().allMatch(ch -> ch >= '0' && ch <= '9')) {
            throw new IllegalArgumentException();
        }

		this.ID = iD;
	}

	public Integer getPoints() {
		return points;
	}

	public void addPoints(Integer points) {
		this.points += points;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void addCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(this.ID);
	}

	@Override
	public boolean equals(Object obj) {
		return this.ID.equals(((LoyaltyCard)obj).ID);
	}
    
}
