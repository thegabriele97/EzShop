# Design Document 


Authors: 

Date:

Version:


# Contents

- [High level design](#package-diagram)
- [Low level design](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

# High level design 

<discuss architectural styles used, if any>
<report package diagram>

```plantuml
package "GUI" as GUI #DDDDDD {

}

package "Application Logic" as Backend {

    package "it.polito.ezshop.data" as data {

    }

    package "it.polito.ezshop.exceptions" as excp {

    }

    package "it.polito.ezshop.model" as model {

    }

    data ..> excp
    data ..> model

}

GUI .up.> data
```

# Low level design

<for each package, report class diagram>


```plantuml
package "it.polito.ezshop.data" as data {

    interface "EZShopInterface" as ezinterface {
        
        {method} +reset(): void
        +createUser(): Integer
        +deleteUser(): boolean
        +getAllUsers(): List<User>
        +getUser(): User
        +updateUserRights(): boolean
        +login(): User
        +logout(): boolean
        +createProductType(): Integer
        +updateProduct(): boolean
        +deleteProductType(): boolean
        +getAllProductTypes(): List<ProductType>
        +getProductTypeByBarCode(): ProductType
        +getProductTypesByDescription(): List<ProductType>
        +updateQuantity(): boolean
        +updatePosition(): boolean
        +issueReorder(): Integer
        +payOrderFor(): Integer
        +payOrder(): boolean
        +recordOrderArrival(): boolean
        +getAllOrders(): List<Order>
        +defineCustomer(): Integer
        +modifyCustomer(): boolean
        +deleteCustomer(): boolean
        +getCustomer(): Customer
        +getAllCustomers(): List<Customer>
        +createCard(): String
        +attachCardToCustomer(): boolean
        +modifyPointsOnCard(): boolean
        +startSaleTransaction(): Integer
        +addProductToSale(): boolean
        +deleteProductFromSale(): boolean
    }

    class "EZShop" as ezshop {

    }

    class LoginManager << (S,#FF7700) Singleton >> {
        -loggedUser: User
        +tryLogin(): boolean
        +tryLogout(): boolean
        +isUserLogged(): boolean
        +getLoggedUser(): User
    }

    class RightsManager << (S,#FF7700) Singleton >> {
        +canManageUsers(): boolean
        +canManageProductCatalog(): boolean
        +canListAllProductsTypes(): boolean
        +canManageInventory(): boolean
        +canManageCustomers(): boolean
        +canManageSaleTransaction(): boolean
        +canUseBarcodeReader(): boolean
        +canManageAccounting(): boolean
    }

    class "DataManager" as DataManager << (S,#FF7700) Singleton >> {
        ..Getters..
        {method} +getUsers(): List<User>
        +getProductTypes(): List<ProductType>
        +getOrders(): List<Order>
        +getCustomers(): List<Customer>
        ..Data Insert..
        +insertUser(): User
        ..Data Update..
        +updateUser(): boolean
        ..Data Delete..
        +deleteUser(): boolean
    }

    note top of RightsManager
        <b>Manages users' rights</b>
        (checks if a generic User
        (passed as argument) can
        do a certain action)
    end note

    note top of DataManager
        <b>Manages all App's data</b>
        (interface towards backend data
        storage, complete transparent to
        the entire system)
    end note

    note left of LoginManager::isUserLogged
        This method accepts both
        0 or 1 argument.
        - If 0, checks if an user is logged in
        (i.e. getLoggedUser() != null)
        - If 1, it's an <<User>> object and checks
        if this user is actually logged
        (i.e. getLoggedUser() == arg_user)
    end note

    ezshop -up-|> ezinterface : <<implements>>
    ezshop ..> LoginManager
    ezshop ..> RightsManager
    ezshop .left.> DataManager

    RightsManager .left.> LoginManager
    LoginManager .up.> DataManager
    

}

```


```plantuml


package "it.polito.ezshop.model" as model {
    class User {
        -ID: Integer
        -username: String
        -password: String
        -role: String
        {method} +setRole(): void
        +getRole(): string
    }

    class ProductType {
        -barcode: String
        -description: String
        -selfPrice: Double
        -quantity: Integer
        -discountRate: Double
        -notes: String
        -position: Position
        +addQuantityOffset(): boolean
        +getAssignedPosition(): Position
        +assignToPosition(): void
    }

    class Position {
        -aisleID: Integer
        -rackID: String
        -levelID: Integer
        -product: ProductType
        ~assignToProduct(): void
        +getAssignedProduct(): ProductType
        +toString(): String
    }

    class SaleTransaction {
        -relatedSale: Sale
        +getRelatedTransaction(): Sale
    }

    abstract ProductList <<abstract>> {
        ~products: Map<ProductType, Integer>
        +getProductsList(): List<ProductType>
        +getQuantityByProduct(): Integer
        +addProduct(): void
    }

    class Sale {
        -saleId: Integer
        -date: Date
        -discountRate: Double
        -loyaltyCard: LoyaltyCard
        -committed: boolean
        -productsDiscountRate: Map<ProductType, double>
        -returnTransaction: List<ReturnTransaction>
        ~addReturnTransaction(): void
        +getTotalCost(): Double
        +applyDiscountRateToSale(): void
        +applyDiscountRateToProductGroup(): void
        +attachLoyaltyCard(): void
        +getAttachedLoyaltyCard(): LoyaltyCard
        +setAsCommitted(): void
        +getSaleId(): Integer
        +isCommitted(): boolean
    }

    class ReturnTransaction {
        -relatedReturnTransaction: CReturn
        +getRelatedTransaction(): CReturn
    }

    class CReturn {
        -returnId: Intger
        -saleTransaction: Sale
        -committed: boolean
        +addProduct(): void <<override>>
        +setAsCommitted(): void
        +getTotalReturnValue(): Double
        +getReturnid(): Double
        +isCommitted(): boolean
    }

    class OrderTransaction {
        -relatedOrder: Order
        +getRelatedOrder(): Order
    }

    enum EOrderStatus {
        +ISSUED
        +PAYED
        +COMPLETED
    }

    class Order {
        -orderId: Integer
        -supplier: String
        -pricePerUnit: Double
        -quantity: Integer
        -product: ProductType
        -status: EOrderStatus 
        +getOrderID(): Integer
        +getQuantity(): Integer
        +getRelatedProduct(): ProductType
        +getStatus(): EOrderStatus
        +setAsPayed(): void
        +setAsCompleted(): void
    }

    enum ETransactionType {
        +CREDIT
        +DEBIT
    }

    abstract BalanceTransaction <<abstract>> {
        -transactionType: ETransactionType
        -description: String
        -value: Double
        +getTransactionType(): ETransactionType
        +getTotalValue(): Double
    }

    class DummyTransaction {

    }

    class Customer {
        -customerID: Integer
        -name: String
        -loyaltyCard: LoyaltyCard
        +attachLoyaltyCard(): void
        +setName(): void
    }

    class LoyaltyCard {
        -ID: String
        -points: Integer
        -customer: Customer
        -SaleTransactions: List<SaleTransaction>
        +addCustomer(): void
        +addPoints(): void
        +getPoints(): void
    }


    ProductType <--> Position
    Sale <-right- CReturn

    LoyaltyCard <--> Customer
    Sale <--> LoyaltyCard

    BalanceTransaction <|-- SaleTransaction
    BalanceTransaction <|-- ReturnTransaction
    BalanceTransaction <|-- OrderTransaction
    BalanceTransaction <|-- DummyTransaction

    OrderTransaction --> Order
    Order --> ProductType
    SaleTransaction --> Sale

    CReturn <-up- ReturnTransaction 

    BalanceTransaction -right-> ETransactionType
    Order -right-> EOrderStatus

    ProductList <|-up- Sale
    ProductList <|-up- CReturn
    ProductList -right-> ProductType

}

```





# Verification traceability matrix
\<NON DIMENTICARE DI CONTROLLARE LA CORRISPONDENZA DEGLI FR>
\<for each functional requirement from the requirement document, list which classes concur to implement it>
|      | EZShop | DataManager | LoginManager | RightsManager | User | Ticket | ReturnTransaction | OrderTransaction | DummyTransaction | Sale | Return | Order | LoyaltyCard | ProductType | Customer | Position |              
| ----- | ---- | --- | ---- | --- | ---- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ------ | ---- | ---- |  --- |
| FR1 | X | X | X | X | X | | | | | | | |   |   |   |   | 
| FR3 | X | X | X | X |   | | | | | | | |   | X |   | X |
| FR4 | X | X | X | X |   | | | | | | | X |   | X |   | X |
| FR5 | X | X | X | X |   | | | | | | | | X |   | X |   |
| FR6 | X | X | X | X |   | X | X | | | X | X |  | X | X | | |
| FR7 | X | X | X | X |   | X | X | | | X | X |  |   |   | | |
| FR8 | X | X | X | X |   | X | X | X | X |   | | | | | | |















# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

## Scenario 2-1

```plantuml
participant GUI

GUI -> EZShop: createUser()
activate EZShop

EZShop -> RightManager: canManageUsers()
activate RightManager

RightManager -> LoginManager: isUserLogged()
activate LoginManager
LoginManager -> RightManager: user is logged
deactivate LoginManager

RightManager -> EZShop: user has permissions
deactivate RightManager

EZShop -> DataManager: insertUser()
activate DataManager
DataManager -> EZShop: user created and saved in the DB
deactivate DataManager

EZShop -> GUI: Done
deactivate EZShop
```
