package it.polito.ezshop.model;

import java.io.Serializable;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.exceptions.InvalidPaymentException;

//Baldaz

public class DummyCredit implements Serializable, ICredit {

    private int id;
    private Double value;

    public DummyCredit(int id, Double value) throws InvalidPaymentException{
        setValue(value);
        this.id = id;
    }

    public void setValue(Double value) throws InvalidPaymentException{
        if(checkValue(value)){   
            this.value = value;
        }
    }

    public Double getValue(){
        return this.value;
    }

    public Double getTotalValue(){
        return getValue();
    }

    public int getId() {
        return this.id;
    }

    private Boolean checkValue(Double value) throws InvalidPaymentException {
        if(value>0){
            return true;
        }
        throw new InvalidPaymentException();
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
