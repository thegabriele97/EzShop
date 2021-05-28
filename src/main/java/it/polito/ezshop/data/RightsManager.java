package it.polito.ezshop.data;

public class RightsManager {

    enum Role { Administrator, Cashier, ShopManager }

    private static RightsManager instance;

    private RightsManager() {
        
    }

    public static RightsManager getInstance() {
        
        if (instance == null) {
            instance = new RightsManager();
        }

        return instance;
    }

    public boolean canManageUsers(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) == Role.Administrator;
    }

    public boolean canManageProductsCatalogue(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) != Role.Cashier;
    }

    public boolean canListAllProductTypes(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user);
    }

    public boolean canManageInventory(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) != Role.Cashier;
    }

    public boolean canManageCustomers(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user);
    }

    public boolean canManageSaleTransactions(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user);
    }

    public boolean canManagePayments(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user);
    }

    public boolean canManageAccounting(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) != Role.Cashier;
    }

    public boolean canManageBalanceTransactions(it.polito.ezshop.model.User user) {
        return LoginManager.getInstance().isUserLogged(user) && Role.valueOf(user.getRole()) != Role.Cashier;
    }

}
