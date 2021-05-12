package it.polito.ezshop.model;

//Baldaz

public class DebitTransaction extends BalanceTransaction {
    
    private IDebit relatedDebitOperation;

    public DebitTransaction(int balanceId, double value, IDebit debit){
        super(balanceId, value);
        this.relatedDebitOperation = debit;
    }

    public IDebit getRelated(IDebit debit){
        return this.relatedDebitOperation;
    }
}
