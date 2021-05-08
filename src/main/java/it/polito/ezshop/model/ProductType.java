package it.polito.ezshop.model;
//simone
import java.io.Serializable;

import it.polito.ezshop.data.*;

public class ProductType implements Serializable,  it.polito.ezshop.data.ProductType {

	private int productId;
	private String barcode;
	private String description;
	private Double selfPrice;
	private int quantity;
	private Double discountRate;
	private String notes;
	private Position position;

	public ProductType(int productId, String barcode, String description, Double selfPrice, int quantity, Double discountRate, String notes, String position) {
		this.setId(productId);
		this.setBarCode(barcode);
		this.setProductDescription(description);
		this.setPricePerUnit(selfPrice);
		this.setQuantity(quantity);//quantity
		this.setDiscountRate(discountRate);//discountRate
		this.setNote(notes);//notes
		this.setLocation(position);//position
	}

	public Double getSelfPrice() {
		return this.selfPrice;
	}

	public Double getDiscountRate() {
		return this.discountRate;
	}

	@Override
	public Integer getQuantity() {
		return this.quantity;
	}

	@Override ///////////////////************** CONTROLLARE ****************//////////////////////
	public String getLocation() {
		return this.position.toString();
	}

	@Override
	public Integer getId() {
		return this.productId;
	}
	@Override
	public String getBarCode() {
		return this.barcode;
	}
	@Override
	public String getNote() {
		return this.notes;
	}

	@Override //////////*************CONTROLLARE*************//////////////////
	public Double getPricePerUnit() {
		return this.selfPrice;
	}
	@Override
	public String getProductDescription() {
		return this.description;
	}


	public void setDiscountRate(Double discountRate) {
		if(discountRate>=0.0) {
			this.discountRate = discountRate;
			DataManager.getInstance().updateProductType(this);
		}
		else
			return;
	}

	@Override
	public void setQuantity(Integer quantity) {
		if(quantity >= 0) {
			this.quantity = quantity;
			DataManager.getInstance().updateProductType(this);
		}
		else
			return;
	}


	@Override
	public void setLocation(String location) {
		String[] pieces = location.split("-");
		Position p = new Position(Integer.valueOf(pieces[0]),pieces[1],Integer.valueOf(pieces[2]), this);
		this.position=p;
		DataManager.getInstance().updateProductType(this);
	}

	@Override
	public void setNote(String note) {
		this.notes=note;
		DataManager.getInstance().updateProductType(this);
	}

	@Override
	public void setProductDescription(String productDescription) {
		if(this.getNote()==null)
			this.description="";

		else 
		{	
			this.description=productDescription;
		}
		DataManager.getInstance().updateProductType(this);

	}

	@Override
	public void setBarCode(String barCode) {
		this.barcode=barCode;
		DataManager.getInstance().updateProductType(this);

	}

	@Override
	public void setPricePerUnit(Double pricePerUnit) {
		this.selfPrice=pricePerUnit;
		DataManager.getInstance().updateProductType(this);

	}

	@Override
	public void setId(Integer id) {
		if (id <= 0) return;
		this.productId=id;
		DataManager.getInstance().updateProductType(this);

	}
	public boolean addQuantityOffset(int quantity) {
		if(quantity==0)
			return false;
		else 
		{
			this.quantity+=quantity;
			DataManager.getInstance().updateProductType(this);
			return true;
		}
	}
	
	///SERVONO REALMENTE? avendo già set and get LOCATION	
	/*public Position getAssignedPosition() { 
		Position P = new Position();
		return P;
	}*/

	public void assingToPosition() {

	}


}
