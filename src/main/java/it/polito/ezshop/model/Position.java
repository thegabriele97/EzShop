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

		if (aisleID == null || aisleID <= 0) throw new IllegalArgumentException();

		this.aisleID = aisleID;
		DataManager.getInstance().updatePosition(this);
	}

	public String getRackID() {
		return rackID;
	}

	public void setRackID(String rackID) {

		if (rackID == null || rackID.isEmpty()) throw new IllegalArgumentException();

		this.rackID = rackID;
		DataManager.getInstance().updatePosition(this);
	}

	public Integer getLevelID() {
		return levelID;
	}

	public void setLevelID(Integer levelID) {
		
		if (levelID == null || levelID <= 0) throw new IllegalArgumentException();
		
		this.levelID = levelID;
		DataManager.getInstance().updatePosition(this);
	}

	public ProductType getAssignedProduct() {
		return product;
	}

	public void assignToProduct(ProductType product) {
		this.product = product;
		DataManager.getInstance().updatePosition(this);
	}

	public String toStringExtended() {
		return "Position of product"+ product.getId()+"[aisleID=" + getAisleID() + ", rackID=" + getRackID() + ", levelID=" + getLevelID() + "]";
	}
	
	@Override
	public String toString() {
		return (getAisleID() + "-" + getRackID() + "-" + getLevelID());	
	}
	
}
