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
    private LocalDate date;

    public BalanceTransaction(int balanceId, double value){
        setBalanceId(balanceId);
        setValue(value);
        date = LocalDate.now();
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

    public String getDescription(){
        return this.description;
    }

    public double getValue(){
        return this.value;
    }

    public void setValue(double value){
        if(value == 0) return;
        this.value = value;
        DataManager.getInstance().updateBalanceTransaction(this);
    }

    public LocalDate getDate(){
        return this.date;
    }

    public void setDate(LocalDate date){
        if(date == null) return;
        this.date = date;
        DataManager.getInstance().updateBalanceTransaction(this);

    }

    public double getMoney() {
        return getValue();
    }

    public void setMoney(double money) {
       setValue(money);
    }

    public String getType() {
        if(value > 0){
            return "credit";
        }
        if(value < 0){
            return "debit";
        }
        return null;
    }

    public void setType(String type) {
        throw new UnsupportedOperationException(); //TODO: to be implemented, but how?
    }

    @Override
    public int hashCode() {
        return this.balanceId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.balanceId == ((BalanceTransaction)obj).balanceId;
    }

}
