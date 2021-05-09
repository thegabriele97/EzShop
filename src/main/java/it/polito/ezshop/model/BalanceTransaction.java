package it.polito.ezshop.model;
import java.io.*;
import java.time.LocalDate;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.DataManager;

//Baldaz

public abstract class BalanceTransaction implements Serializable, BalanceOperation {
    
    private int balanceId;
    private String description;
    private double value;

    public BalanceTransaction(int balanceId, String description, double value){
        setBalanceId(balanceId);
        setDescription(description);
        setValue(value);
    }

    public int getBalanceId(){
        return this.balanceId;
    }

    public void setBalanceId(int balanceId){
        if(balanceId <= 0) return;
        this.balanceId = balanceId;
        DataManager.getInstance().updateBalanceTransaction(this);
    }

    public void setDescription(String descr){
        if(descr.isEmpty() || descr.isBlank() || descr == null) return;
        this.description = descr;
        DataManager.getInstance().updateBalanceTransaction(this);

    }

    public void setValue(double value){
        if(value == 0) return;
        this.value = value;
        DataManager.getInstance().updateBalanceTransaction(this);
    }

    /*public LocalDate getDate(){

    }

    void setDate(LocalDate date){

    }

    double getMoney();

    void setMoney(double money);

    String getType();

    void setType(String type);*/

}
