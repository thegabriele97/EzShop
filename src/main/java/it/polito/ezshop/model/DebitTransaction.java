package it.polito.ezshop.model;

import it.polito.ezshop.data.DataManager;

//Baldaz

public class DebitTransaction extends BalanceTransaction {
    
    private IDebit relatedDebitOperation;

    public DebitTransaction(int balanceId, IDebit debit){
        super(balanceId, debit.getTotalValue());
        this.relatedDebitOperation = debit;
    }

    public void setRelatedDebitOperation(IDebit debit){
        this.relatedDebitOperation = debit;
        this.setValue(debit.getTotalValue());
        DataManager.getInstance().updateBalanceTransaction(this);
    }

    public IDebit getRelatedDebitOperation(){
        return this.relatedDebitOperation;
    }
}
