package it.polito.ezshop.model;

import java.io.Serializable;
import it.polito.ezshop.data.DataManager;

//Baldaz

public class DummyDebit implements Serializable, IDebit {

    public int id;
    private Double value;

    public DummyDebit(int id, Double value){
        setValue(value);
        setId(id);
    }

    public void setValue(Double value){

        if (value <= 0.0 || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException();  
        }

        this.value = value;
        DataManager.getInstance().updateDummyDebit(this);
    }

    private void setId(int id){
        if(id <= 0) throw new IllegalArgumentException();
        this.id = id;
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

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((DummyDebit)obj).id;
    }
    
}
