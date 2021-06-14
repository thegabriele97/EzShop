package it.polito.ezshop.model;

import static it.polito.ezshop.data.EZShop.*;
import static java.util.stream.Collectors.*;

import java.util.*;

public class CReturn extends ProductList implements IDebit {
    
    private Integer returnId;
    private Sale saleTransaction;
    private boolean committed;
    private Integer balanceId;
    private Set<Product> productRFIDs;

    
    public CReturn(Integer returnId, Sale saleTransaction) {
        setReturnId(returnId);
        setSaleTransaction(saleTransaction);
        
        this.committed = false;
        this.productRFIDs = new HashSet<>();

        saleTransaction.addReturnTransaction(this);
    }
    
    public void setAsCommitted(){
        this.committed = true;
    }
    
    public Integer getReturnId(){
        return this.returnId;
    }

    public void setReturnId(Integer returnId){
        if (returnId == null || returnId < 1) throw new IllegalArgumentException();
        this.returnId = returnId;
    }
    
    public boolean isCommitted(){
        return this.committed;
    }

    public Sale getSaleTransaction() {
        return saleTransaction;
    }

    public void setSaleTransaction(Sale saleTransaction){
        if (saleTransaction == null) throw new IllegalArgumentException();
        this.saleTransaction = saleTransaction;
    }

    public Integer getBalanceId() {
        return this.balanceId;
    }
    
    public void setBalanceId(Integer balanceId) {
        if (balanceId < 1) throw new IllegalArgumentException();
        this.balanceId = balanceId;
    }

    public boolean addProductRFID(Product prod) {
        return this.productRFIDs.add(prod);
    }

    public boolean deleteProductRFID(Product prod) {
        return this.productRFIDs.remove(prod);
    }

    public List<Product> getProducRFIDs() {
        return this.productRFIDs
            .stream()
            .collect(toList());
    }

    @Override
    public Double getTotalValue() {
        double value = 0.0;
        for (it.polito.ezshop.data.ProductType prod : getProductsList()) {
            it.polito.ezshop.model.ProductType xProd = (it.polito.ezshop.model.ProductType)prod;

            value += (xProd.getPricePerUnit() * this.products.get(xProd))*(1-saleTransaction.getDiscountRateForProductGroup(xProd));
        }

        value *= (1-saleTransaction.getDiscountRate());

        return getRightDoublePrecision(value);
    }

    @Override
    public int hashCode() {
        return this.getReturnId();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getReturnId().equals(((CReturn)obj).getReturnId());
    }

}
