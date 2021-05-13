package it.polito.ezshop.model;

public class CreditTransaction extends BalanceTransaction {

    private ICredit relatedCreditOperation;
 
    public CreditTransaction(int balanceId, double value, ICredit credit) {
        super(balanceId, value);
        setRelatedCreditOperation(credit);
    }

    public void setRelatedCreditOperation(ICredit credit){
        this.relatedCreditOperation = credit;
    }

    public ICredit getRelatedCreditOperation(){
        return this.relatedCreditOperation;
    }
    
}
