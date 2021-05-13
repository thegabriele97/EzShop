package it.polito.ezshop.model;

import java.io.Serializable;
import it.polito.ezshop.data.DataManager;

//Baldaz

public class DummyDebit implements Serializable, IDebit {

    public int id;
    private Double value;

    public DummyDebit(int id, Double value){
        setValue(value);
        this.id = id;
    }

    public void setValue(Double value){
        if(checkValue(value)){   
            this.value = value;
            DataManager.getInstance().updateDummyDebit(this);
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

    private boolean checkValue(Double value){
        return value > 0;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((DummyDebit)obj).id;
    }
    
}
