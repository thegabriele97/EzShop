package it.polito.ezshop.data;

class CreditCardSystem {
    
    private static CreditCardSystem instance;

    private CreditCardSystem() {
        
    }

    static CreditCardSystem getInstance() {
        
        if (instance == null) {
            instance = new CreditCardSystem();
        }

        return instance;
    }

    boolean isValidNumber(String creditCard) {
        
        int sum = 0;
        boolean skip = false;

        for (int i = creditCard.length() - 2; i >= 0; i--, skip ^= true) {

            int curr_digit = Integer.parseInt(creditCard.substring(i, i + 1));
            int mul_2 = !skip ? curr_digit << 1 : curr_digit;
            sum += (mul_2 % 10) + (mul_2 / 10);

        }

        return ((sum * 9) % 10) == Integer.parseInt(creditCard.substring(creditCard.length() - 1));
    }

    boolean isRegistered(String creditCard) {
        throw new UnsupportedOperationException();
    }

    boolean hasEnoughBalance(String creditCard) {
        throw new UnsupportedOperationException();
    }

    boolean updateBalance(String creditCard) {
        throw new UnsupportedOperationException();
    }

}
