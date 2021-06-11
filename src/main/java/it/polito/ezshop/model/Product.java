package it.polito.ezshop.model;

import java.io.Serializable;

public class Product implements Serializable {
    
    private String rfid;
    private ProductType relativeProductType;
    private boolean available;

    public Product(String rfid, ProductType relProductType) {
        setRFID(rfid);
        setRelativeProductType(relProductType);
        this.available = true;
    }

    public String getRFID() {
        return this.rfid;
    }

    public ProductType getRelativeProductType() {
        return this.relativeProductType;
    }

    public void setAvailable(boolean val) {
        this.available = val;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setRFID(String rfid){
        if(!it.polito.ezshop.data.EZShop.isValidRFID(rfid)) {
            throw new IllegalArgumentException();
        }
        
        this.rfid = rfid;
    }
    
    public void setRelativeProductType(ProductType pt){
        if(pt == null) {
            throw new IllegalArgumentException();
        }
        
        this.relativeProductType = pt;
    }
    
    @Override
    public int hashCode() {
        return rfid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.rfid.equals(((Product)obj).rfid);
    }

}
