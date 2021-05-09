package it.polito.ezshop.model;

//Baldaz

public class DebitTransaction extends BalanceTransaction {
    
    private IDebit relatedDebitOperation;

    public DebitTransaction(IDebit debit){
        setRelatedDebitOperation(debit);
    }

    public void setRelatedDebitOperation(IDebit debit){
        this.relatedDebitOperation = debit;
    }
}
