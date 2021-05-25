# Integration and API Test Documentation

Authors:

Date:

Version:

# Contents

- [Dependency graph](#dependency graph)

- [Integration and API Test Documentation](#integration-and-api-test-documentation)
- [Contents](#contents)
- [Dependency graph](#dependency-graph)
- [Integration approach](#integration-approach)
- [Tests](#tests)
  - [Step 1](#step-1)
  - [Step 2](#step-2)
  - [Step 3](#step-3)
  - [Step 4](#step-4)
- [Coverage of Scenarios and FR](#coverage-of-scenarios-and-fr)
- [Coverage of Non Functional Requirements](#coverage-of-non-functional-requirements)
    - [](#)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph 

```plantuml

package "it.polito.ezshop.data" as data {

    class "EZShop" as ezshop {

    }

    class LoginManager << (S,#FF7700) Singleton >> {
        
    }

    class RightsManager << (S,#FF7700) Singleton >> {
        
    }

    class "DataManager" as DataManager << (S,#FF7700) Singleton >> {
        
    }

    class CreditCardSystem << (S,#FF7700) Singleton >> {
       
    }

    ezshop ..> LoginManager
    ezshop ..> RightsManager
    ezshop .right.> DataManager
    ezshop .left.> CreditCardSystem

    RightsManager .left.> LoginManager
    LoginManager .up.> DataManager
    

}

package "it.polito.ezshop.model" as model {
    
    class User {

    }

    class ProductType {
        
    }

    class Position {
        
    }

    abstract ProductList {
        
    }

    class Sale {
        
    }

    class CReturn {
        
    }

    class Order {
        
    }

    abstract BalanceTransaction {

    }

    class Customer {

    }

    class LoyaltyCard {

    }

    interface ICredit <<interface>> {
        
    }

    interface IDebit <<interface>> {
        
    }

    class CreditTransaction {
        
    }

    class DebitTransaction {
        
    }

    class DummyCredit {
        
    }

    class DummyDebit {
        
    }

    ProductType <-left-> Position
    Sale <-right- CReturn

    LoyaltyCard <--> Customer

    Order --> ProductType

    ProductList <|-up- Sale
    ProductList <|-up- CReturn
    ProductList --> ProductType

    Sale -up-|> ICredit
    Order -up-|> IDebit
    CReturn -up-|> IDebit
    DummyCredit -left-|> ICredit
    DummyDebit -left-|> IDebit

    BalanceTransaction <|-- CreditTransaction
    BalanceTransaction <|-- DebitTransaction

    CreditTransaction --> ICredit
    DebitTransaction --> IDebit

}

ezshop --> BalanceTransaction
ezshop --> LoyaltyCard
ezshop --> Customer
ezshop --> User
ezshop --> DummyCredit
ezshop --> DummyDebit
ezshop --> Sale
ezshop --> Order
ezshop --> CReturn
ezshop --> Position
ezshop --> ProductType

```

# Integration approach

    <Write here the integration sequence you adopted, in general terms (top down, bottom up, mixed) and as sequence
    (ex: step1: class A, step 2: class A+B, step 3: class A+B+C, etc)> 
    <Some steps may  correspond to unit testing (ex step1 in ex above), presented in other document UnitTestReport.md>
    <One step will  correspond to API testing>

We adopted a bottom up approach. Starting from the leaf classes (Unit Testing) we started to go up in the dependency graph until EZShop that represents our complete Integration of the developed software.


#  Tests

   <define below a table for each integration step. For each integration step report the group of classes under test, and the names of
     JUnit test cases applied to them> JUnit test classes should be here src/test/java/it/polito/ezshop

## Step 1
| Classes  | JUnit test cases |
|---|---|
| Position | PositionTest.java |
| Customer | CustomerTest.java |


## Step 2
| Classes  | JUnit test cases |
|---|---|
|Creturn|CreturnTest.java|
|Sale|SaleTest.java|
|Order|OrderTest.java|


## Step 3
| Classes  | JUnit test cases |
|---|---|
|DebitTransaction|DebitTransaction.java|
|CreditTransaction|CreditTransaction.java|
|RightManager|RightManagerTest.java|
|LoginManager|LoginManagerTest.java|

   

## Step 4
| Classes  | JUnit test cases |
|---|---|
|EZShop|EZShopTest.java|

# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR. 
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered    | JUnit  Test(s)                                                                                                                                                                                                                                                                                                                                 |
|-------------|------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ..          | FRx                                |                                                                                                                                                                                                                                                                                                                                                |
| ..          | FRy                                |                                                                                                                                                                                                                                                                                                                                                |
| ...         |                                    |                                                                                                                                                                                                                                                                                                                                                |
| 1.1          | FR3.1                                |testCreateProductType()                                                                                                                                                                                                                                                                                                                                                |
| 1.2         | FR3.4, FR4.2                       | testUpdatePosition()                                                                                                                                                                                                                                                                                                                                               |
| 1.3 | FR3.1 | testUpdateProductWithInvalidPricePerUnit() |
| 2.1         | FR1                                | testCreateUserAsAdministrator()                                                                                                                                                                                                                                                                                                                |
| 2.2          | F1.2                                |testDeleteUserWithAdministratorRights()                                                                                                                                                                                                                                                                                                                                                |
| 2.3         | FR1.5                              | testValidUpdateUserRights()                                                                                                                                                                                                                                                                                                                                               |
| 3.1 | FR4.3 | testUnauthorizedIssueOrder(), testValidIssueOrder(), testWrongParamsIssueOrder(), testMissingProductIssueOrder() |
| 3.2         | FR4.5                              | testPayOrderTotVal(), testPayOrderNotIssued(), testPayOrderWithNotExisitingOrder(), testPayOrderWithNotValidOrderId(), testPayOrderCashier()                                                                                                                                                                                                   |
| 3.3          | FR4.6                                |testRecordOrderArrivalWithRightsAndRightStatus()                                                                                                                                                                                                                                                                                                                                                |
| 4.1         | FR5.1                              | testDefineCustomerWithRightsAndCorrectInserting()                                                                                                                                                                                                                                                                                                                                               |
| 4.2 | FR5.5, FR5.6 | testValidAttachCardToCustomer(), testUnauthorizedAttachCardToCustomer(), testWrongParamsAttachCardToCustomer(), testMissingCustomerCardToCustomer(), testMissingCardAttachCardToCustomer(), testCreateCardWithoutUser(), testModifyPointsOnCard() |
| 4.3         | FR5.1                              | testNoCardModifyCustomer()                                                                                                                                                                                                                                                                                                                     |
| 4.4          | FR5.1                                |testValidModifyCustomer()                                                                                                                                                                                                                                                                                                                                                |
| 5.1         | FR1                                | testLoginValidUser()                                                                                                                                                                                                                                                                                                                                               |
| 5.2 | FR1 | testLogoutWithoutLoggedUser(), testLogoutWithLoggedUser() |
| 6.1         | FR6.1, FR6.2, FR6.10, FR7.1, FR7.2 | testStartSaleTransactionWithNoLoggedUser(), testStartSaleTransactionWithCashierRights(), testStartSaleTransactionWithShopManagerRights(), testStartSaleTransactionWithAdministratorRights(), testValidAddProductToSale(), testEndSaleTransactionWithMultipleCalls(), testValidReceiveCashPayment(), testReceiveCreditCardPaymentSuccessfully() |
| 6.2         | FR6.1 , FR6.7, FR6.2, FR4.1, FR6.5, FR6.10, FR7, FR6.8, FR8.2                                   |testStartSaleTransactionWithCashierRights(), testapplyDiscountRateToProductWithRightsAndValidSingleProduct(), testValidAddProductToSale(), testEndSaleTransactionWithMultipleCalls()                                                                                                                                                                                                                                                                                                                                                |
| 6.3         | FR6.1 , FR6.7, FR6.2, FR4.1, FR6.4, FR6.10, FR7, FR6.8, FR8.2 | testStartSaleTransactionWithCashierRights(), testValidAddProductToSale(), testValidApplyDiscountRateToSale(), testEndSaleTransactionWithMultipleCalls() |
| 6.4 | FR6.2, FR6.7, FR6.6 FR6.8, FR6.10 | testStartSaleTransactionWithNoLoggedUser(), testStartSaleTransactionWithCashierRights(), testStartSaleTransactionWithShopManagerRights(), testStartSaleTransactionWithAdministratorRights(), testEndSaleTransactionWithoutUSer(), testEndSaleTransaction(), testEndSaleTransactionWithMultipleCalls(), testEndSaleTransactionWithNoValidId(), testModifyPointsOnCardWithoutUser(), testModifyPointsOnCardWithInvalidCard(), testModifyPointsOnCardWithCardNotPresent(), testModifyPointsOnCard() |
| 6.5         | FR6.1, FR6.2, FR6.10, FR6.11       | testStartSaleTransactionWithNoLoggedUser(), testStartSaleTransactionWithCashierRights(), testStartSaleTransactionWithShopManagerRights(), testStartSaleTransactionWithAdministratorRights(), testValidAddProductToSale(), testEndSaleTransactionWithMultipleCalls(), testDeleteSaleTransaction()                                               |
| 6.6          | FR6.1 , FR6.7, FR6.2, FR4.1, FR6.10, FR7.1, FR6.8, FR8.2                                |testStartSaleTransactionWithCashierRights(), testapplyDiscountRateToProductWithRightsAndValidSingleProduct(), testValidAddProductToSale(), testEndSaleTransactionWithMultipleCalls(), testValidReceiveCashPayment()                                                                                                                                                                                                                                                                                                                                             
| 7.1         | FR7.2                              | testReceiveCreditCardPaymentSuccessfully()                                                                                                                                                                                                                                                                                                                                               |
| 7.2 | FR7.2 | testReceiveCreditCardPaymentWithNotRegisteredCreditCard() |
| 7.3         | FR7.2                              | testReceiveCreditCardPaymentWithNotEnoughMoneyOnTheCreditCard()                                                                                                                                                                                                                                                                                |
| 7.4          | FR7.1                               | testValidReceiveCashPayment()                                                                                                                                                                                                                                                                                                                                               |
| 8.1         | FR6.12, FR6.7, FR6.13, FR7.4, FR6.14 | testValidGetSaleTransaction(), testStartReturnTransactionWithCashierRights(), testReturnProductWithCashierRights(), testReturnCreditCardPayamentWithRightsAndEverythingCorrect(), testEndReturnTransactionWithCashierRights()                                                                                                                |
| 8.2 | FR7.4 | testReturnCashPaymentWithNoLoggedUser(), testReturnCashPaymentWithRightsAndTransIdEqual0(), testReturnCashPaymentWithRightsAndTransIdNegative(), testReturnCashPaymentWithRightsAndNoCompletedTrans(), testReturnCashPaymentWithRightsAndNoExistingTrans(), testReturnCashPaymentWithRightsAndValid(), testReturnCashPaymentWithRightsAndValidMultipleProducts() |
| 9.1         | FR8.3                              | testGetCreditsAndDebitsWithBothParameters()                                                                                                                                                                                                                                                                                                    |
| 10.1          | FR7.2                               |testReceiveCreditCardPaymentSuccessfully()                                                                                                                                                                                                                                                                                                                                                |
| 10.2        | FR7.3                                | testReturnCashPaymentWithRightsAndValid()                                                                                                                                                                                                                                                                                                                                               |


# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
| NFR4 | BBisValidBarcodeTest.java/* |
| NFR5 | BBCreditCardSystemTest.java/* |
| NFR6 | testModifyPointsOnCardWithInvalidCard() |

