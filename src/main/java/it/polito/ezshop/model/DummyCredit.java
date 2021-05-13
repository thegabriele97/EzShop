package it.polito.ezshop.model;

import java.io.Serializable;

import it.polito.ezshop.data.DataManager;

//Baldaz

public class DummyCredit implements Serializable, ICredit {

    private int id;
    private Double value;

    public DummyCredit(int id, double value){
        setValue(value);
        this.id = id;
    }

    public void setValue(double value){
        if(checkValue(value)){   
            this.value = value;
            DataManager.getInstance().updateDummyCredit(this);
        }
    }

    public double getValue(){
        return this.value;
    }

    public Double getTotalValue(){
        return getValue();
    }

    public int getId() {
        return this.id;
    }

    private boolean checkValue(double value) {
        return value >= 0;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((DummyCredit)obj).id;
    }
    
}
