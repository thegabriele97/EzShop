package it.polito.ezshop.model;

import java.io.Serializable;

import static it.polito.ezshop.data.EZShop.*;

public class CReturn extends ProductList implements Serializable, IDebit {
    
    private Integer returnId;
    private Sale saleTransaction;
    private boolean committed;
    private Integer balanceId;
    
    public CReturn(Integer returnId, Sale saleTransaction) {
        setReturnId(returnId);
        setSaleTransaction(saleTransaction);
        this.committed = false;

        //saleTransaction.addReturnTransaction(this); TODO: is it necessary?
    }
    
    public void setAsCommitted(){
        this.committed = true;
    }
    
    public Integer getReturnId(){
        return this.returnId;
    }

    public void setReturnId(Integer returnId){
        if (returnId < 1) return;
        this.returnId = returnId;
    }
    
    public boolean isCommitted(){
        return this.committed;
    }

    public Sale getSaleTransaction() {
        return saleTransaction;
    }

    public void setSaleTransaction(Sale saleTransaction){
        if (saleTransaction == null) return;
        this.saleTransaction = saleTransaction;
    }

    public Integer getBalanceId() {return this.balanceId;}
    
    public void setBalanceId(Integer balanceId) {
        if (balanceId < 1) return;
        this.balanceId = balanceId;
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

}
