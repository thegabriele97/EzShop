package it.polito.ezshop.model;

import java.io.Serializable;

public class CReturn extends ProductList implements Serializable {

    private Integer returnId;
    private Sale saleTransaction;
    private boolean committed;

    public CReturn(Integer returnId, Sale saleTransaction) {
        this.returnId = returnId;
        this.saleTransaction = saleTransaction;
        this.committed = false;
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

}
