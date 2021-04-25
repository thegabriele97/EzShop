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
    }

    class Position {
        -aisleID: Integer
        -rackID: String
        -levelID: Integer
    }

    class Ticket {

    }

    class Sale {
        -transactionId: Integer
        -date: Date
        -cost: Double
        -paymentType: String
        -discountRate: Double
        -loyaltyCard: SaleTransaction
        -committed: boolean
        -Products: Map<ProductType, Integer>
        -ReturnTransaction: List<ReturnTransaction>
        {method} +addProduct(): void
    }

    class ReturnTransaction {

    }

    class CReturn {
        -SaleTransaction: SaleTransaction
        -returnedProduct: ProductType
        -quantity: Integer
        -returnedValue: Double
        -committed: boolean
    }

    class OrderTransaction {
        -relatedOrder: Order
    }

    class Order {
        -ID: Integer
        -supplier: String
        -pricePerUnit: Double
        -quantity: Integer
        -product: ProductType
        -status: 
    }

    class BalanceTransaction {
        -value: Double
        -description: String
    }

    abstract Credit <<abstract>> {

    }

    abstract Debit <<abstract>> {

    }

    class Customer {
        -name: String
        -surname: String
        - loyaltyCard: LoyaltyCard
    }

    class LoyaltyCard {
        -ID: String
        -points: Integer
        -customer: Customer
        -SaleTransactions: List<SaleTransaction>
        {method} +addCustomer(): boolean
        +addPoints(): void
        +getPoints(): void
    }


    ProductType --> Position
    CReturn --> ProductType
    Sale --> ProductType
    Sale <-- CReturn

    LoyaltyCard <--> Customer
    Sale <--> LoyaltyCard

    BalanceTransaction <|-- Credit
    BalanceTransaction <|-- Debit

    Credit <|-- Ticket
    Debit <|-- ReturnTransaction
    Debit <|-- OrderTransaction

    OrderTransaction --> Order
    Order --> ProductType
    Ticket --> Sale

    CReturn <-up- ReturnTransaction 

}

```





# Verification traceability matrix

\<for each functional requirement from the requirement document, list which classes concur to implement it>











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
