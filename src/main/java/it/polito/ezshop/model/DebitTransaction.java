package it.polito.ezshop.model;

//Baldaz

public class DebitTransaction extends BalanceTransaction {
    
    private IDebit relatedDebitOperation;

    public DebitTransaction(IDebit debit){
        super(0, null, 0.0); //TODO: to be implemented
        setRelatedDebitOperation(debit);
    }

    public void setRelatedDebitOperation(IDebit debit){
        this.relatedDebitOperation = debit;
    }
}
