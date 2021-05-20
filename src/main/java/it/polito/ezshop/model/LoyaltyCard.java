package it.polito.ezshop.model;
//Simone
import java.io.Serializable;

public class LoyaltyCard implements Serializable {
	private String ID;
	private Integer points;
	private Customer customer;
	
	public LoyaltyCard(String iD, Integer points, Customer customer) {
		this.setID(iD);
		this.setPoints(points); 
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

	private void setPoints(Integer points){
		if(points == null || points < 0) throw new IllegalArgumentException();
		this.points = points;
	}

	public Integer getPoints() { 
		return points;
	}

	public boolean addPoints(Integer points) { 
		if(points == null || this.points + points < 0) return false;
		this.points += points;
		return true;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void addCustomer(Customer customer) { 
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		//return Integer.parseInt(this.ID);
		return this.ID.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.ID.equals(((LoyaltyCard)obj).ID);
	}
    
}
