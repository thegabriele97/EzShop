package it.polito.ezshop.model;

import it.polito.ezshop.data.DataManager;

public class CreditTransaction extends BalanceTransaction {

    private ICredit relatedCreditOperation;
 
    public CreditTransaction(int balanceId, ICredit credit) {
        super(balanceId, 0.0);

        if (credit == null) throw new IllegalArgumentException();

        this.setValue(credit.getTotalValue());
        setRelatedCreditOperation(credit);
    }

    public void setRelatedCreditOperation(ICredit credit){

        if (credit == null) throw new IllegalArgumentException();

        this.relatedCreditOperation = credit;
        this.setValue(credit.getTotalValue());
        DataManager.getInstance().updateBalanceTransaction(this);
    }

    public ICredit getRelatedCreditOperation(){
        return this.relatedCreditOperation;
    }
    
}
