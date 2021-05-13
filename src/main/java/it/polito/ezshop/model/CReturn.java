package it.polito.ezshop.model;

import java.io.Serializable;

public class CReturn extends ProductList implements Serializable, IDebit {
    
    private Integer returnId;
    private Sale saleTransaction;
    private boolean committed;
    private Integer balanceId;
    
    public CReturn(Integer returnId, Sale saleTransaction) {
        this.returnId = returnId;
        this.saleTransaction = saleTransaction;
        this.committed = false;
        
        //saleTransaction.addReturnTransaction(this); TODO: is it necessary?
    }
    
    public void setAsCommitted(){
        this.committed = true;
    }
    
    public Integer getReturnid(){
        return this.returnId;
    }
    
    public boolean isCommitted(){
        return this.committed;
    }

    public Sale getSaleTransaction() {
        return saleTransaction;
    }
    
    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }


    @Override
    public Double getTotalValue() {
        // TODO: to be implemented
        return null;
    }

}
