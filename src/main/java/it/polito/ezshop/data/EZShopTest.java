package it.polito.ezshop.data;

public class EZShopTest {
    
    public static void main(String[] args) {
        
        //String card = "9254347527611304";

        /*boolean v1 = CreditCardSystem.getInstance().isValidNumber(card);
        boolean v2 = CreditCardSystem.getInstance().hasEnoughBalance(card, 3.87);
        boolean v3 = CreditCardSystem.getInstance().hasEnoughBalance(card, 9.87);
        boolean v4 = CreditCardSystem.getInstance().hasEnoughBalance(card, 11.65);
        boolean v5 = CreditCardSystem.getInstance().updateBalance(card, 4.69);*/

        //DataManager.getInstance().deleteBalanceTransaction(DataManager.getInstance().getBalanceTransactions().stream().filter(b -> b.getValue() < 0).findFirst().get());

        EZShopInterface ez = new EZShop();

        try {

            ez.reset();

            ez.createUser("ciao", "pwd", "Administrator");
            ez.login("ciao", "pwd");

            Integer customer = ez.defineCustomer("Peppe Girolamo");
            String card = ez.createCard();
            ez.attachCardToCustomer(card, customer);

            boolean t1 = ez.modifyPointsOnCard(card, 3);
            boolean t2 = ez.modifyPointsOnCard(card, -11);

            if (t1 == t2) {
                return;
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
        

    }

}
