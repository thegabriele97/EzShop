package it.polito.ezshop.model;
//simone

import java.io.Serializable;
import java.util.Optional;

import it.polito.ezshop.data.*;

public class ProductType implements Serializable, it.polito.ezshop.data.ProductType {

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

	@Override
	public String getLocation() {

		if (this.position == null) {
			return "";
		}

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

	@Override
	public Double getPricePerUnit() {
		return this.selfPrice;
	}
	@Override
	public String getProductDescription() {
		return this.description;
	}


	public void setDiscountRate(Double discountRate) {
		
		if (discountRate == null || discountRate < 0.0 || discountRate >= 1.0 || Double.isNaN(discountRate) || Double.isInfinite(discountRate)) {
            throw new IllegalArgumentException();  
        }

		this.discountRate = discountRate;
		DataManager.getInstance().updateProductType(this);
	}

	@Override
	public void setQuantity(Integer quantity) {
		
		if (quantity < 0) throw new IllegalArgumentException();
		
		this.quantity = quantity;
		DataManager.getInstance().updateProductType(this);
	}


	@Override
	public void setLocation(String location) {

		if (location == null || location.isEmpty()) {
			if (this.position != null) {
				this.position.assignToProduct(null);
			}

			this.position = null;
			return;
		}

		if (!(location.matches("[1-9]+-[a-zA-Z]+-[1-9]+"))) throw new IllegalArgumentException();
		
		Optional<Position> pos = DataManager.getInstance()
			.getPositions()
			.stream()
			.filter(ps -> ps.toString().equals(location))
			.findFirst();

		Position assignTo = pos.orElse(null);
		
		if (!(pos.isPresent())) {
			String[] pieces = location.split("-");

			if (pieces.length != 3) {
				throw new IllegalArgumentException();
			}

			assignTo = new Position(Integer.valueOf(pieces[0]),pieces[1],Integer.valueOf(pieces[2]), null);
			DataManager.getInstance().insertPosition(assignTo);
		}

		if (assignTo.getAssignedProduct() != this && !assingToPosition(assignTo)) {
			throw new IllegalArgumentException();
		}
		
		DataManager.getInstance().updateProductType(this);
	}

	@Override
	public void setNote(String note) {
		this.notes=note;
		DataManager.getInstance().updateProductType(this);
	}

	@Override
	public void setProductDescription(String productDescription) {
		
		if (productDescription == null || productDescription.isEmpty()) {
            throw new IllegalArgumentException();
        }

		this.description=productDescription;
		DataManager.getInstance().updateProductType(this);

	}

	@Override
	public void setBarCode(String barCode) {

		if (!EZShop.isValidBarcode(barCode)) throw new IllegalArgumentException();

		this.barcode=barCode;
		DataManager.getInstance().updateProductType(this);

	}

	@Override
	public void setPricePerUnit(Double pricePerUnit) {

		if (pricePerUnit == null || pricePerUnit <= 0 || Double.isNaN(pricePerUnit) || Double.isInfinite(pricePerUnit)) {
            throw new IllegalArgumentException();  
        }

		this.selfPrice = pricePerUnit;
		DataManager.getInstance().updateProductType(this);

	}

	@Override
	public void setId(Integer id) {
		if (id <= 0) throw new IllegalArgumentException();
		this.productId=id;
		DataManager.getInstance().updateProductType(this);

	}

	public boolean addQuantityOffset(int quantity) {

		if (getQuantity() + quantity < 0) return false;

		this.quantity+=quantity;
		DataManager.getInstance().updateProductType(this);

		return true;
	}

	public boolean assingToPosition(Position pos) {

		if (pos.getAssignedProduct() != null) {
			return false;
		}

		if (this.position != null) {
			pos.assignToProduct(null);
		}
		
		this.position = pos;
		pos.assignToProduct(this);
		
		return true;
	}

	public Position getAssignedPosition() {
		return this.position;
	}

	@Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((ProductType)obj).getId();
    }

}