package it.polito.ezshop.data;
import it.polito.ezshop.model.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.io.*;

public class DataManager implements Serializable {
    
    private static DataManager instance = null;

    private Set<BalanceTransaction> balanceTransactions;
    private Set<it.polito.ezshop.model.User> users;
    private Set<it.polito.ezshop.model.ProductType> productTypes;
    private Set<Position> positions;
    private Set<it.polito.ezshop.model.Order> orders;
    private Set<it.polito.ezshop.model.Customer> customers;
    private Set<LoyaltyCard> loyaltyCards;
    private Set<Sale> sales;
    private Set<CReturn> cReturns;
    private Set<DummyCredit> dummyCredits;
    private Set<DummyDebit> dummyDebits;


    private DataManager() {
        users = new HashSet<>();
        productTypes = new HashSet<>();
        positions = new HashSet<>();
        orders = new HashSet<>();
        customers = new HashSet<>();
        loyaltyCards = new HashSet<>();
        sales = new HashSet<>();
        cReturns = new HashSet<>();
        dummyCredits = new HashSet<>();
        dummyDebits = new HashSet<>();
        balanceTransactions = new HashSet<>();
    }

    private boolean save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("ezshop.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(this);
            
            out.close();
            fileOut.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private <T> boolean insertIntoAndSave(Set<T> list, T o) {

        if (list.add(o)) {
            return this.save();
        }

        return false;
    }

    private <T> boolean updateIntoAndSave(Set<T> list, T o) {
        
        if (deleteIntoAndSave(list, o, false)) {
            if (list.add(o)) {
                return this.save();
            }
        }

        return false;
    }

    private <T> boolean deleteIntoAndSave(Set<T> list, T o) {
        return deleteIntoAndSave(list, o, true);
    }

    private <T> boolean deleteIntoAndSave(Set<T> list, T o, boolean commit) {
        
        if (!list.contains(o)) {
            return false;
        }

        if (list.remove(o)) {
            if (commit) return this.save();
            return true;
        }

        return false;
    }

    public static DataManager getInstance() {
        
        if (instance == null) {
            
            try {
                FileInputStream fileIn = new FileInputStream("ezshop.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                
                instance = (DataManager)in.readObject();
                
                in.close();
                fileIn.close();
            } catch (Exception e) {
                instance = new DataManager();
            }

        }

        return instance;
    }

    public List<it.polito.ezshop.model.User> getUsers() {
        return users.stream()
            .collect(toList());
    }

    public List<it.polito.ezshop.model.ProductType> getProductTypes() {
        return productTypes.stream()
            .collect(toList());
    }

    public List<Position> getPositions() {
        return positions.stream()
            .collect(toList());
    }

    public List<it.polito.ezshop.model.Order> getOrders() {
        return orders.stream()
            .collect(toList());
    }

    public List<it.polito.ezshop.model.Customer> getCustomers() {
        return customers.stream()
            .collect(toList());
    }

    public List<LoyaltyCard> getLoyaltyCards() {
        return loyaltyCards.stream()
            .collect(toList());
    }

    public List<Sale> getSales() {
        return sales.stream()
            .collect(toList());
    }

    public List<CReturn> getReturns() {
        return cReturns.stream()
            .collect(toList());
    }

    public List<DummyCredit> getDummyCredits() {
        return dummyCredits.stream()
            .collect(toList());
    }

    public List<DummyDebit> getDummyDebits() {
        return dummyDebits.stream()
            .collect(toList());
    }

    public List<BalanceTransaction> getBalanceTransactions() {
        return balanceTransactions.stream()
            .collect(toList());
    }

    public boolean insertUser(it.polito.ezshop.model.User o) {
        return insertIntoAndSave(users, o);
    }

    public boolean insertProductTypes(it.polito.ezshop.model.ProductType o) {
        return insertIntoAndSave(productTypes, o);
    }

    public boolean insertPosition(Position o) {
        return insertIntoAndSave(positions, o);
    }

    public boolean insertOrder(it.polito.ezshop.model.Order o) {
        return insertIntoAndSave(orders, o);
    }

    public boolean insertCustomer(it.polito.ezshop.model.Customer o) {
        return insertIntoAndSave(customers, o);
    }

    public boolean insertLoyaltyCard(LoyaltyCard o) {
        return insertIntoAndSave(loyaltyCards, o);
    }

    public boolean insertSale(Sale o) {
        return insertIntoAndSave(sales, o);
    }

    public boolean insertReturn(CReturn o) {
        return insertIntoAndSave(cReturns, o);
    }

    public boolean insertDummyCredit(DummyCredit o) {
        return insertIntoAndSave(dummyCredits, o);
    }

    public boolean insertDummyDebit(DummyDebit o) {
        return insertIntoAndSave(dummyDebits, o);
    }

    public boolean insertBalanceTransaction(BalanceTransaction o) {
        return insertIntoAndSave(balanceTransactions, o);
    }

    public boolean updateUser(it.polito.ezshop.model.User o) {
        return updateIntoAndSave(users, o);
    }
    
    public boolean updateProductTypes(it.polito.ezshop.model.ProductType o) {
        return updateIntoAndSave(productTypes, o);
    }
    
    public boolean updatePosition(Position o) {
        return updateIntoAndSave(positions, o);
    }
    
    public boolean updateOrder(it.polito.ezshop.model.Order o) {
        return updateIntoAndSave(orders, o);
    }
    
    public boolean updateCustomer(it.polito.ezshop.model.Customer o) {
        return updateIntoAndSave(customers, o);
    }
    
    public boolean updateLoyaltyCard(LoyaltyCard o) {
        return updateIntoAndSave(loyaltyCards, o);
    }
    
    public boolean updateSale(Sale o) {
        return updateIntoAndSave(sales, o);
    }
    
    public boolean updateReturn(CReturn o) {
        return updateIntoAndSave(cReturns, o);
    }
    
    public boolean updateDummyCredit(DummyCredit o) {
        return updateIntoAndSave(dummyCredits, o);
    }
    
    public boolean updateDummyDebit(DummyDebit o) {
        return updateIntoAndSave(dummyDebits, o);
    }
    
    public boolean updateBalanceTransaction(BalanceTransaction o) {
        return updateIntoAndSave(balanceTransactions, o);
    }

    public boolean deleteProductTypes(it.polito.ezshop.model.ProductType o) {
        return deleteIntoAndSave(productTypes, o);
    }
    
    public boolean deletePosition(Position o) {
        return deleteIntoAndSave(positions, o);
    }
    
    public boolean deleteOrder(it.polito.ezshop.model.Order o) {
        return deleteIntoAndSave(orders, o);
    }
    
    public boolean deleteCustomer(it.polito.ezshop.model.Customer o) {
        return deleteIntoAndSave(customers, o);
    }
    
    public boolean deleteLoyaltyCard(LoyaltyCard o) {
        return deleteIntoAndSave(loyaltyCards, o);
    }
    
    public boolean deleteSale(Sale o) {
        return deleteIntoAndSave(sales, o);
    }
    
    public boolean deleteReturn(CReturn o) {
        return deleteIntoAndSave(cReturns, o);
    }
    
    public boolean deleteDummyCredit(DummyCredit o) {
        return deleteIntoAndSave(dummyCredits, o);
    }
    
    public boolean deleteDummyDebit(DummyDebit o) {
        return deleteIntoAndSave(dummyDebits, o);
    }
    
    public boolean deleteBalanceTransaction(BalanceTransaction o) {
        return deleteIntoAndSave(balanceTransactions, o);
    }

}
