package it.polito.ezshop.model;
//Simone
import java.io.Serializable;
import it.polito.ezshop.data.*;

public class Customer implements Serializable, it.polito.ezshop.data.Customer {
	private Integer ID;
	private String name;
	private LoyaltyCard loyaltyCard; 
	
	public Customer(Integer iD, String name, String loyaltyCard) {
		this.setId(iD);
		this.setCustomerName(name);
		this.setCustomerCard(loyaltyCard);
	}

	
	@Override
	public String getCustomerName() {
		return this.name;
	}


	@Override
	public void setCustomerName(String customerName) {
		this.name=customerName;
		DataManager.getInstance().updateCustomer(this);
	}

	@Override
	public String getCustomerCard() {
		return loyaltyCard.getID();
	}

	@Override
	public void setCustomerCard(String customerCard) {
		
		
	}

	@Override
	public Integer getId() {
		return this.ID;
	}

	@Override
	public void setId(Integer id) {
		if (id <= 0) return;
		this.ID=id;
		DataManager.getInstance().updateCustomer(this);
		
	}

	@Override
	public Integer getPoints() {		
		return this.loyaltyCard.getPoints();
	}

	@Override
	public void setPoints(Integer points) {
		
	}
    
}
