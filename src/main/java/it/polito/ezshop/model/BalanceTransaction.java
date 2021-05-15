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
    private String type; // I hate this  //(me too)

    public BalanceTransaction(int balanceId, double value){
        setBalanceId(balanceId);
        setValue(value);

        this.date = LocalDate.now();
        this.type = (this instanceof CreditTransaction) ? "CREDIT" : "DEBIT";
    }

    @Override
    public int getBalanceId(){
        return this.balanceId;
    }

    @Override
    public void setBalanceId(int balanceId){
        if(balanceId <= 0) return;
        this.balanceId = balanceId;
        Update();
    }

    public void setDescription(String descr){
        if(descr.isEmpty() || descr == null) return;
        this.description = descr;
        Update();
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
        Update();
    }

    @Override
    public LocalDate getDate(){
        return this.date;
    }

    @Override
    public void setDate(LocalDate date){
        if(date == null) return;
        this.date = date;
        Update();
    }

    @Override
    public double getMoney() {
        return getValue();
    }

    @Override
    public void setMoney(double money) {
       setValue(money);
       Update();
    }

    @Override
    public String getType() {
        return this.type;
    }

    // Why this method exists?
    @Override
    public void setType(String type) {
        if (type == null || (!type.equals("CREDIT") && !type.equals("DEBIT"))) return;
        this.type = type;
        Update();
    }

    @Override
    public int hashCode() {
        return this.balanceId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.balanceId == ((BalanceTransaction)obj).balanceId;
    }

    private void Update() {
        DataManager.getInstance().updateBalanceTransaction(this);
    }

}
