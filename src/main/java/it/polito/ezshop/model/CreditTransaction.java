package it.polito.ezshop.model;

public class CreditTransaction extends BalanceTransaction {

    private ICredit relatedCreditOperation;
 
    public CreditTransaction(ICredit credit) {
        super(0, null, 0.0); //TODO: to be implemented
        setRelatedCreditOperation(credit);
    }

    public void setRelatedCreditOperation(ICredit credit){
        this.relatedCreditOperation = credit;
    }

    public ICredit getRelatedCreditOperation(){
        return this.relatedCreditOperation;
    }
    
}
