package it.polito.ezshop.model;

//Baldaz

public class DebitTransaction extends BalanceTransaction {
    
    private IDebit relatedDebitOperation;

    public DebitTransaction(int balanceId, double value, IDebit debit){
        super(balanceId, value);
        this.relatedDebitOperation = debit;
    }

    public IDebit setRelatedDebitOperation(IDebit debit){
        return this.relatedDebitOperation;
    }

    public IDebit getRelatedDebitOperation(){
        return this.relatedDebitOperation;
    }
}
