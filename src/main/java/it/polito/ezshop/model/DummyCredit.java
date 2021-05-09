package it.polito.ezshop.model;

import java.io.Serializable;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.exceptions.InvalidPaymentException;

//Baldaz

public class DummyCredit implements Serializable, ICredit {

    private Double value;

    public DummyCredit(Double value) throws InvalidPaymentException{
        setValue(value);
        DataManager.getInstance().updateDummyCredit(this);
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

    private Boolean checkValue(Double value) throws InvalidPaymentException{
        if(value>0){
            return true;
        }
        throw new InvalidPaymentException();
    }
    
}
