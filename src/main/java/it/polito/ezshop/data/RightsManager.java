package it.polito.ezshop.data;

class RightsManager {

    enum Role { Administrator, Cashier, ShopManager }

    private static RightsManager instance;

    private RightsManager() {
        
    }

    static RightsManager getInstance() {
        
        if (instance == null) {
            instance = new RightsManager();
        }

        return instance;
    }

    boolean canManageUsers(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) == Role.Administrator;
    }

    boolean canManageProductsCatalogue(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) != Role.Cashier;
    }

    boolean canListAllProductTypes(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user);
    }

    boolean canManageInventory(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) != Role.Cashier;
    }

    boolean canManageCustomers(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user);
    }

    boolean canManageSaleTransactions(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user);
    }

    boolean canManagePayments(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user);
    }

    boolean canManageAccounting(it.polito.ezshop.model.User user) throws IllegalArgumentException {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) != Role.Cashier;
    }

}
