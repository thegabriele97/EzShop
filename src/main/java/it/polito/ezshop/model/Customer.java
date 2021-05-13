package it.polito.ezshop.model;
//Simone
import java.io.Serializable;
import java.util.Optional;

import it.polito.ezshop.data.*;

public class Customer implements Serializable, it.polito.ezshop.data.Customer {
	private Integer ID;
	private String name;
	private LoyaltyCard loyaltyCard; 
	
	public Customer(Integer iD, String name, LoyaltyCard loyaltyCard) {
		this.setId(iD);
		this.setCustomerName(name);
		this.loyaltyCard = loyaltyCard;
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
		return loyaltyCard != null ? loyaltyCard.getID() : "";
	}

	@Override
	public void setCustomerCard(String customerCard) {
		
		if (customerCard.isEmpty()) {
			this.loyaltyCard.addCustomer(null);
			DataManager.getInstance().updateLoyaltyCard(this.loyaltyCard);
			
			this.loyaltyCard = null;
			DataManager.getInstance().updateCustomer(this);
			return;
		}
		
		if (customerCard.length() != 10 || !customerCard.chars().allMatch(ch -> ch >= '0' && ch <= '9')) return;

		Optional<LoyaltyCard> card = DataManager.getInstance()
            .getLoyaltyCards()
            .stream()
            .filter(c -> c.getID().equals(customerCard))
            .findFirst();

		if (!(card.isPresent()) || card.get().getCustomer() != null) return;

		if (this.loyaltyCard != null) {
			this.loyaltyCard.addCustomer(null);
			DataManager.getInstance().updateLoyaltyCard(this.loyaltyCard);
		}

		this.loyaltyCard = card.get();
		card.get().addCustomer(this);

		DataManager.getInstance().updateLoyaltyCard(card.get());
		DataManager.getInstance().updateCustomer(this);
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
		return loyaltyCard != null ? this.loyaltyCard.getPoints() : 0;
	}

	@Override
	public void setPoints(Integer points) {
		
		if (loyaltyCard == null) return;

		if (points < 0 && loyaltyCard.getPoints() < points) {
			return;
		}

		loyaltyCard.addPoints(points);
		DataManager.getInstance().updateCustomer(this);
	}

	public LoyaltyCard getLoyaltyCard() {
		return this.loyaltyCard;
	}

	@Override
	public int hashCode() {
		return this.ID;
	}

	@Override
	public boolean equals(Object obj) {
		return this.ID == ((Customer)obj).ID;
	}
    
}
