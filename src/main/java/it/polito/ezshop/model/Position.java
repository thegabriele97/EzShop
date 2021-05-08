package it.polito.ezshop.model;
//Simone
import java.io.Serializable;

import it.polito.ezshop.data.*;

public class Position implements Serializable{
	
	private Integer aisleID;
	private String rackID;
	private Integer levelID;
	private ProductType product;
	
	

	public Position(Integer aisleID, String rackID, Integer levelID, ProductType product) {
		this.setAisleID(aisleID);
		this.setRackID(rackID);
		this.setLevelID(levelID);
		this.assignToProduct(product);
	}

	public Integer getAisleID() {
		return aisleID;
	}

	public void setAisleID(Integer aisleID) {
		if(aisleID>0) {
			this.aisleID = aisleID;
			DataManager.getInstance().updatePosition(this);
		}
		else
			return;
	}

	public String getRackID() {
		return rackID;
	}

	public void setRackID(String rackID) {
		this.rackID = rackID;
		DataManager.getInstance().updatePosition(this);
	}

	public Integer getLevelID() {
		return levelID;
	}

	public void setLevelID(Integer levelID) {
		if(levelID>0) {
			this.levelID = levelID;
			DataManager.getInstance().updatePosition(this);
		}
			
		else
			return;
	
	}

	public ProductType getAssignedProduct() {
		return product;
	}

	public void assignToProduct(ProductType product) {
		this.product = product;
		DataManager.getInstance().updatePosition(this);
	}


	@Override
	public String toString() {
		return "Position of product"+ product.getId()+"[aisleID=" + getAisleID() + ", rackID=" + getRackID() + ", levelID=" + getLevelID() + "]";
	}
	
	public String toStringP() {
		return (getAisleID() + "-" + getRackID() + "-" + getLevelID());	
	}
	
}
