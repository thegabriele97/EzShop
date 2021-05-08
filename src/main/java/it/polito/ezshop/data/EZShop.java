package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;

import java.time.LocalDate;
import java.util.*;

import javax.management.Descriptor;
import javax.xml.crypto.Data;

import static java.util.stream.Collectors.*;


public class EZShop implements EZShopInterface {


    @Override
    public void reset() {

    }

    @Override
    public Integer createUser(String username, String password, String role) throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
        
        if (!(role.equals("Administrator") || role.equals("Cashier") || role.equals("ShopManager"))) {
            throw new InvalidRoleException();
        }

        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameException();
        }

        if (password == null || password.isEmpty()) {
            throw new InvalidPasswordException();
        }

        boolean usernameExists = DataManager.getInstance()
            .getUsers()
            .stream()
            .anyMatch(user -> user.getUsername().equals(username));

        if (usernameExists) {
            return -1;
        }

        OptionalInt maxId = DataManager.getInstance()
            .getUsers()
            .stream()
            .mapToInt(it.polito.ezshop.model.User::getId)
            .max();

        int newId = !maxId.isPresent() ? 1 : (maxId.getAsInt() + 1);
        if (!DataManager.getInstance().insertUser(new it.polito.ezshop.model.User(newId, username, password, role))) {
            return -1;
        }

        return newId;
    }

    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        
        if (id == null || id <= 0) throw new InvalidUserIdException();

        if (!RightsManager.getInstance().canManageUsers(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        Optional<it.polito.ezshop.model.User> optUser = DataManager.getInstance()
            .getUsers()
            .stream()
            .filter(u -> u.getId().equals(id))
            .findFirst();

        if (!optUser.isPresent()) return false; 
        
        return DataManager.getInstance().deleteUser(optUser.get());
    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {

        if (!RightsManager.getInstance().canManageUsers(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        return DataManager.getInstance()
            .getUsers()
            .stream()
            .map(u -> (User)u)
            .collect(toList());
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        
        if (id == null || id <= 0) throw new InvalidUserIdException();

        if (!RightsManager.getInstance().canManageUsers(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        Optional<it.polito.ezshop.model.User> optUser = DataManager.getInstance()
            .getUsers()
            .stream()
            .filter(u -> u.getId().equals(id))
            .findFirst();

        return optUser.orElse(null);
    }

    @Override
    public boolean updateUserRights(Integer id, String role) throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {

        if (id == null || id <= 0) throw new InvalidUserIdException();

        if (!(role.equals("Administrator") || role.equals("Cashier") || role.equals("ShopManager"))) {
            throw new InvalidRoleException();
        }

        if (!RightsManager.getInstance().canManageUsers(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        Optional<it.polito.ezshop.model.User> optUser = DataManager.getInstance()
            .getUsers()
            .stream()
            .filter(u -> u.getId().equals(id))
            .findFirst();

        optUser.ifPresent(u -> {
            u.setId(id);
            DataManager.getInstance().updateUser(u);
        });

        return optUser.isPresent();       
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        
        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameException();
        }

        if (password == null || password.isEmpty()) {
            throw new InvalidPasswordException();
        }

        if (!LoginManager.getInstance().tryLogin(username, password)) {
            return null;
        }

        return (User)LoginManager.getInstance().getLoggedUser();
    }

    @Override
    public boolean logout() {
        return LoginManager.getInstance().tryLogout();
    }

    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note) throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageProductsCatalogue(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }
        
        if (description == null || description.isEmpty()) {
            throw new InvalidProductDescriptionException();
        }

        if (productCode == null || productCode.isEmpty() || !isValidBarcode(productCode)) {
            throw new InvalidProductCodeException();
        }

        if (pricePerUnit <= 0.0) {
            throw new InvalidPricePerUnitException();
        }

        if (DataManager.getInstance().getProductTypes().stream().anyMatch(p -> p.getBarCode().equals(productCode))) {
            return -1;
        }

        OptionalInt maxId = DataManager.getInstance()
            .getProductTypes()
            .stream()
            .mapToInt(it.polito.ezshop.model.ProductType::getId)
            .max();

        int newId = !maxId.isPresent() ? 1 : (maxId.getAsInt() + 1);
        it.polito.ezshop.model.ProductType newProd = new it.polito.ezshop.model.ProductType(newId, productCode, description, pricePerUnit, 0, 0.0, note, null);
        if (!DataManager.getInstance().insertProductType(newProd)) {
            return -1;
        }

        return newId;
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote) throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageProductsCatalogue(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (id == null || id <= 0) {
            throw new InvalidProductIdException();
        }

        if (newDescription == null || newDescription.isEmpty()) {
            throw new InvalidProductDescriptionException();
        }

        if (newCode == null || newCode.isEmpty() || !isValidBarcode(newCode)) {
            throw new InvalidProductCodeException();
        }

        if (newPrice <= 0.0) {
            throw new InvalidPricePerUnitException();
        }

        Optional<it.polito.ezshop.model.ProductType> prod = DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> p.getId() == id)
            .findFirst();

        boolean isNewCodeAlreadyUsed = DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> !p.equals(prod.get()))
            .anyMatch(p -> p.getBarCode().equals(newCode));
            

        if (isNewCodeAlreadyUsed || prod.isEmpty()) {
            return false;
        }

        prod.get().setBarCode(newCode);
        prod.get().setProductDescription(newDescription);
        prod.get().setPricePerUnit(newPrice);
        prod.get().setNote(newNote);

        return DataManager.getInstance().updateProductType(prod.get());
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageProductsCatalogue(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (id == null || id <= 0) {
            throw new InvalidProductIdException();
        }

        Optional<it.polito.ezshop.model.ProductType> prod = DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> p.getId() == id)
            .findFirst();

        if (prod.isEmpty()) return false;

        return DataManager.getInstance().deleteProductType(prod.get());
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {

        if (!RightsManager.getInstance().canListAllProductTypes(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        return DataManager.getInstance()
            .getProductTypes()
            .stream()
            .collect(toList());
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode) throws InvalidProductCodeException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageProductsCatalogue(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (barCode == null || barCode.isEmpty() || !isValidBarcode(barCode)) {
            throw new InvalidProductCodeException();
        }

        return DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> p.getBarCode().equals(barCode))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageProductsCatalogue(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        return DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> p.getProductDescription().contains(description == null ? "" : description))
            .collect(toList());
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded) throws InvalidProductIdException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageProductsCatalogue(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (productId == null || productId <= 0) {
            throw new InvalidProductIdException();
        }

        Optional<it.polito.ezshop.model.ProductType> prod = DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> p.getId() == productId)
            .findFirst();

        if (prod.get().getQuantity() + toBeAdded < 0) return false;

        if (prod.get().getLocation() == "") return false;

        prod.get().addQuantityOffset(toBeAdded);
        return DataManager.getInstance().updateProductType(prod.get());
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos) throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageProductsCatalogue(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (productId == null || productId <= 0) {
            throw new InvalidProductIdException();
        }

        Optional<it.polito.ezshop.model.ProductType> prod = DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> p.getId() == productId)
            .findFirst();

        if (newPos == null || newPos.isEmpty()) {
            
            Position oldpos = prod.get().getAssignedPosition();
            prod.get().setLocation("");

            DataManager.getInstance().updateProductType(prod.get());
            DataManager.getInstance().updatePosition(oldpos);

            return true;
        }

        if (!(newPos.matches("[0-9]+-[a-zA-Z]+-[0-9]+"))) {
            throw new InvalidLocationException();
        }

        Optional<Position> pos = DataManager.getInstance()
            .getPositions()
            .stream()
            .filter(ps -> ps.toString().equals(newPos))
            .findFirst();
        
        String[] pieces = newPos.split("-");
        Position actualPos = pos.isEmpty() ? new Position(Integer.valueOf(pieces[0]),pieces[1],Integer.valueOf(pieces[2]), null) : pos.get();

        if (pos.isEmpty()) {
            DataManager.getInstance().insertPosition(actualPos);
        }

        if (!prod.get().assingToPosition(actualPos)) {
            return false;
        }

        DataManager.getInstance().updatePosition(actualPos);
        return DataManager.getInstance().updateProductType(prod.get());
    }

    @Override
    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean recordOrderArrival(Integer orderId) throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        return false;
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
        return new ArrayList<>(); //TODO: to be implemented
    }

    @Override
    public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageCustomers(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (customerName == null || customerName.isEmpty()) {
            throw new InvalidCustomerNameException();
        }

        if (DataManager.getInstance().getCustomers().stream().anyMatch(c -> c.getCustomerName().equals(customerName))) {
            return -1;
        }

        OptionalInt maxId = DataManager.getInstance()
            .getCustomers()
            .stream()
            .mapToInt(it.polito.ezshop.model.Customer::getId)
            .max();

        int newId = !maxId.isPresent() ? 1 : (maxId.getAsInt() + 1);
        it.polito.ezshop.model.Customer newCustomer = new it.polito.ezshop.model.Customer(newId, customerName, null);
        if (!DataManager.getInstance().insertCustomer(newCustomer)) {
            return -1;
        }

        return newId;
    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard) throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageCustomers(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (id == null || id <= 0) {
            throw new InvalidCustomerIdException();
        }

        if (newCustomerName == null || newCustomerName.isEmpty()) {
            throw new InvalidCustomerNameException();
        }

        Optional<it.polito.ezshop.model.Customer> customer = DataManager.getInstance()
            .getCustomers()
            .stream()
            .filter(s -> s.getId() == id)
            .findFirst();

        if (customer.isEmpty()) return false;

        boolean isCardBusy = DataManager.getInstance()
            .getLoyaltyCards()
            .stream()
            .anyMatch(c -> c.getID().equals(newCustomerCard));

        if (isCardBusy) {
            // TODO: to be implemented
        }

    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return false;
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {
        return new ArrayList<>(); //TODO: to be implemented
    }

    @Override
    public String createCard() throws UnauthorizedException {
        return null;
    }

    @Override
    public boolean attachCardToCustomer(String customerCard, Integer customerId) throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded) throws InvalidCustomerCardException, UnauthorizedException {
        return false;
    }

    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageSaleTransactions(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        OptionalInt maxId = DataManager.getInstance()
            .getSales()
            .stream()
            .mapToInt(Sale::getTicketNumber)
            .max();

        int newId = !maxId.isPresent() ? 1 : (maxId.getAsInt() + 1);
        Sale newSale = new Sale(newId, LocalDate.now(), 0.0, null);

        DataManager.getInstance().insertSale(newSale);
        return newId;
    }

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        
        if (!RightsManager.getInstance().canManageSaleTransactions(LoginManager.getInstance().getLoggedUser())) {
            throw new UnauthorizedException();
        }

        if (transactionId == null || transactionId <= 0) {
            throw new InvalidTransactionIdException();
        }

        if (!isValidBarcode(productCode)) {
            throw new InvalidProductCodeException();
        }

        if (amount < 0) {
            throw new InvalidQuantityException();
        }

        if (true) { //TODO: to be completed
            return false;
        }

        Optional<Sale> sale = DataManager.getInstance()
            .getSales()
            .stream()
            .filter(s -> s.getTicketNumber() == transactionId)
            .findFirst();

        if (!sale.isPresent() || sale.get().isCommitted()) {
            return false;
        }

        Optional<it.polito.ezshop.model.ProductType> prod = DataManager.getInstance()
            .getProductTypes()
            .stream()
            .filter(p -> p.getBarCode().equals(productCode))
            .findFirst();

        if (!prod.isPresent() /** TODO: || prod.get().getQuantity() < amount */) {
            return false;
        }

        //TODO: decrease available quantity for productype
        sale.get().addProduct(prod.get(), amount);
        DataManager.getInstance().updateSale(sale.get());

        return true;
    }

    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate) throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
        return false;
    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean endSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteSaleTransaction(Integer saleNumber) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        return null;
    }

    @Override
    public Integer startReturnTransaction(Integer saleNumber) throws /*InvalidTicketNumberException,*/InvalidTransactionIdException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash) throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return false;
    }

    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        return 0;
    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
        return false;
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        return new ArrayList<>(); //TODO: to be implemented
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        return 0;
    }

    private boolean isValidBarcode(String barcode) {
        //TODO: The barcode number related to a product type should be a string of digits of either 
        //12, 13 or 14 numbers validated following this algorithm  https://www.gs1.org/services/how-calculate-check-digit-manually
        return true;
    }

}
