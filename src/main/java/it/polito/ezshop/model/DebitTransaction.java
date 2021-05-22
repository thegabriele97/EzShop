package it.polito.ezshop.model;

import it.polito.ezshop.data.DataManager;

//Baldaz

public class DebitTransaction extends BalanceTransaction {
    
    private IDebit relatedDebitOperation;

    public DebitTransaction(int balanceId, IDebit debit){
        super(balanceId, debit.getTotalValue());
        setRelatedDebitOperation(debit); //TODO: prima era senza set e non faceva controlli sul null, è un problema?
    }

    public void setRelatedDebitOperation(IDebit debit){

        if (debit == null) throw new IllegalArgumentException();

        this.relatedDebitOperation = debit;
        this.setValue(debit.getTotalValue());
        DataManager.getInstance().updateBalanceTransaction(this);
    }

    public IDebit getRelatedDebitOperation(){
        return this.relatedDebitOperation;
    }
}
