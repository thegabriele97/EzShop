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

    class LoginManager {

    }

    class RightsManager {

    }

    class DataManager {

    }

    note top of LoginManager
     <b>Singleton class</b> 
     to manage logged 
     user sessions or login
     requests
    end note

    note top of RightsManager
     <b>Singleton class</b> 
     to manage users' rights
    end note

    note right of DataManager
     <b>Singleton class</b> 
     to manage all App's data
    end note

    ezshop -up-|> ezinterface : <<implements>
    ezshop .right.> LoginManager
    ezshop .left.> RightsManager
    ezshop .down.> DataManager
    

}
```





# Verification traceability matrix

\<for each functional requirement from the requirement document, list which classes concur to implement it>











# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

