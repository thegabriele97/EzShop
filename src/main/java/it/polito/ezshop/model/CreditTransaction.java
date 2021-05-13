package it.polito.ezshop.model;

import it.polito.ezshop.data.DataManager;

public class CreditTransaction extends BalanceTransaction {

    private ICredit relatedCreditOperation;
 
    public CreditTransaction(int balanceId, ICredit credit) {
        super(balanceId, credit.getTotalValue());
        setRelatedCreditOperation(credit);
    }

    public void setRelatedCreditOperation(ICredit credit){
        this.relatedCreditOperation = credit;
        this.setValue(credit.getTotalValue());
        DataManager.getInstance().updateBalanceTransaction(this);
    }

    public ICredit getRelatedCreditOperation(){
        return this.relatedCreditOperation;
    }
    
}
