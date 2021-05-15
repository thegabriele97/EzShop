# Unit Testing Documentation

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed  
    to start tests
    >

 ### **Class DataManager - method deleteUser()**



**Criteria for method deleteUser():**
	
 - Validity of User object
 - Existence of User Object


**Predicates for method deleteUser():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of User object | Valid |
| - | NULL |
| Existence of User Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of User Object | Existence of User Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | User u = (1, "we", "mbare", "Administrator")<br>DataManager.getInstance().deleteUser(u)<br>-> false |testDeleteExistingUser()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingUser()|
| NULL                   | -                        | Invalid         | User u = null<br>DataManager.getInstance().deleteUser(u)<br>-> false|testDeleteNullUser()|

### **Class DataManager - method deleteProductType()**

**Criteria for method deleteProductType():**
	
 - Validity of ProductType object
 - Existence of ProductType Object


**Predicates for method deleteProductType():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of ProductType object | Valid |
| - | NULL |
| Existence of ProductType Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of ProductType Object | Existence of ProductType Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | ProductType u = (1, ...)<br>DataManager.getInstance().deleteProductType(u)<br>-> false |testDeleteExistingProductType()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingProductType()|
| NULL                   | -                        | Invalid         | ProductType u = null<br>DataManager.getInstance().deleteProductType(u)<br>-> false|testDeleteNullProductType()|

### **Class DataManager - method deletePosition()**

**Criteria for method deletePosition():**
	
 - Validity of Position object
 - Existence of Position Object


**Predicates for method deletePosition():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Position object | Valid |
| - | NULL |
| Existence of Position Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Position Object | Existence of Position Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | Position u = (1, ...)<br>DataManager.getInstance().deletePosition(u)<br>-> false |testDeleteExistingPosition()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingPosition()|
| NULL                   | -                        | Invalid         | Position u = null<br>DataManager.getInstance().deletePosition(u)<br>-> false|testDeleteNullPosition()|

### **Class DataManager - method deleteOrder()**

**Criteria for method deleteOrder():**
	
 - Validity of Order object
 - Existence of Order Object


**Predicates for method deleteOrder():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Order object | Valid |
| - | NULL |
| Existence of Order Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Order Object | Existence of Order Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | Order u = (1, ...)<br>DataManager.getInstance().deleteOrder(u)<br>-> false |testDeleteExistingOrder()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingOrder()|
| NULL                   | -                        | Invalid         | Order u = null<br>DataManager.getInstance().deleteOrder(u)<br>-> false|testDeleteNullOrder()|

### **Class DataManager - method deleteCustomer()**

**Criteria for method deleteCustomer():**
	
 - Validity of Customer object
 - Existence of Customer Object


**Predicates for method deleteCustomer():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Customer object | Valid |
| - | NULL |
| Existence of Customer Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Customer Object | Existence of Customer Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | Customer u = (1, ...)<br>DataManager.getInstance().deleteCustomer(u)<br>-> false |testDeleteExistingCustomer()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingCustomer()|
| NULL                   | -                        | Invalid         | Customer u = null<br>DataManager.getInstance().deleteCustomer(u)<br>-> false|testDeleteNullCustomer()|

### **Class DataManager - method deleteLoyaltyCard()**


**Criteria for method deleteLoyaltyCard():**
	
 - Validity of LoyaltyCard object
 - Existence of LoyaltyCard Object


**Predicates for method deleteLoyaltyCard():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of LoyaltyCard object | Valid |
| - | NULL |
| Existence of LoyaltyCard Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of LoyaltyCard Object | Existence of LoyaltyCard Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | LoyaltyCard u = (1, ...)<br>DataManager.getInstance().deleteLoyaltyCard(u)<br>-> false |testDeleteExistingLoyaltyCard()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingLoyaltyCard()|
| NULL                   | -                        | Invalid         | LoyaltyCard u = null<br>DataManager.getInstance().deleteLoyaltyCard(u)<br>-> false|testDeleteNullLoyaltyCard()|

### **Class DataManager - method deleteSale()**


**Criteria for method deleteSale():**
	
 - Validity of Sale object
 - Existence of Sale Object


**Predicates for method deleteSale():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Sale object | Valid |
| - | NULL |
| Existence of Sale Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Sale Object | Existence of Sale Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | Sale u = (1, ...)<br>DataManager.getInstance().deleteSale(u)<br>-> false |testDeleteExistingSale()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingSale()|
| NULL                   | -                        | Invalid         | Sale u = null<br>DataManager.getInstance().deleteSale(u)<br>-> false|testDeleteNullSale()|

### **Class DataManager - method deleteReturn()**

**Criteria for method deleteReturn():**
	
 - Validity of Return object
 - Existence of Return Object


**Predicates for method deleteReturn():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Return object | Valid |
| - | NULL |
| Existence of Return Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Return Object | Existence of Return Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | Return u = (1, ...)<br>DataManager.getInstance().deleteReturn(u)<br>-> false |testDeleteExistingReturn()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingReturn()|
| NULL                   | -                        | Invalid         | Return u = null<br>DataManager.getInstance().deleteReturn(u)<br>-> false|testDeleteNullReturn()|

### **Class DataManager - method deleteDummyCredit()**

**Criteria for method deleteDummyCredit():**
	
 - Validity of DummyCredit object
 - Existence of DummyCredit Object


**Predicates for method deleteDummyCredit():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of DummyCredit object | Valid |
| - | NULL |
| Existence of DummyCredit Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of DummyCredit Object | Existence of DummyCredit Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | DummyCredit u = (1, ...)<br>DataManager.getInstance().deleteDummyCredit(u)<br>-> false |testDeleteExistingDummyCredit()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingDummyCredit()|
| NULL                   | -                        | Invalid         | DummyCredit u = null<br>DataManager.getInstance().deleteDummyCredit(u)<br>-> false|testDeleteNullDummyCredit()|


### **Class DataManager - method deleteDummyDebit()**

**Criteria for method deleteDummyDebit():**
	
 - Validity of DummyDebit object
 - Existence of DummyDebit Object


**Predicates for method deleteDummyDebit():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of DummyDebit object | Valid |
| - | NULL |
| Existence of DummyDebit Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of DummyDebit Object | Existence of DummyDebit Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | DummyDebit u = (1, ...)<br>DataManager.getInstance().deleteDummyDebit(u)<br>-> false |testDeleteExistingDummyDebit()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingDummyDebit()|
| NULL                   | -                        | Invalid         | DummyDebit u = null<br>DataManager.getInstance().deleteDummyDebit(u)<br>-> false|testDeleteNullDummyDebit()|


### **Class DataManager - method deleteBalanceTransaction()**

**Criteria for method deleteBalanceTransaction():**
	
 - Validity of BalanceTransaction object
 - Existence of BalanceTransaction Object


**Predicates for method deleteBalanceTransaction():**

| Criteria | Predicate |
| -------- | --------- |
| Validity of BalanceTransaction object | Valid |
| - | NULL |
| Existence of BalanceTransaction Object | Yes |
| - | No |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of BalanceTransaction Object | Existence of BalanceTransaction Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | BalanceTransaction u = (1, ...)<br>DataManager.getInstance().deleteBalanceTransaction(u)<br>-> false |testDeleteExistingBalanceTransaction()|
| Valid                  | NO                       | Valid           | ---- -> true |testDeleteNotExistingBalanceTransaction()|
| NULL                   | -                        | Invalid         | BalanceTransaction u = null<br>DataManager.getInstance().deleteBalanceTransaction(u)<br>-> false|testDeleteNullBalanceTransaction()|


### **Class CreditCardSystem - method isValidNumber()**

**Criteria for method isValidNumber():**
	
 - Existence of CreditCard string object
 - Emtpiness of CreditCard string
 - Contains characters too
 - Contains only numbers


**Predicates for method isValidNumber():**

| Criteria | Predicate |
| -------- | --------- |
| Existence of CreditCard string object | Valid |
| - | NULL |
| Emptiness of CreditCard string | Yes |
| - | No |
| Contains characters too | Yes |
| - | No |
| Contains Only numbers | 000000000 |
| - | 99999999 |
| - | \<valid number\> |
| - | NO |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Contains only numbers | 000000, 9999999, \<valid number\>  |


**Combination of predicates**:

| Existence of CreditCard string object | Emptiness of CreditCard string | Contains characters too | Contains Only numbers | Valid / Invalid | Description of the test case                       | JUnit test case |
|---------------------------------------|--------------------------------|-------------------------|-----------------------|-----------------|----------------------------------------------------|-----------------|
| Valid                                 | NO                             | YES                     | NO                    | Invalid         | isValidNumber("123s23xa223") -> false          | testValidCreditCardWithCharacters()                |
| Valid                                 | NO                             | NO                      | 999999999             | Invalid         | isValidNumber("9999999") -> false              | testValidCreditCardWithAll9s()                |
| Valid                                 | NO                             | NO                      | 000000000             | Valid           | isValidNumber("0000000") -> true // valid for  | testValidCreditCardWithAll0s() |
| Valid                                 | NO                             | NO                      | <\valid number\>      | Valid           | isValidNumber(<number>) -> true                | testValidCreditCardWithValidNumber()                |
| Valid                                 | YES                            | -                       | -                     | Invalid         | isValidNumber("") -> false                     | testValidCreditCardWithEmptyString()                 |
| NULL                                  | -                              | -                       | -                     | Invalid         | isValidNumber(null) -> false                   | testValidCreditCardWithNullString()               |



### **Class CreditCardSystem - method isRegistered()**

**Criteria for method isValidNumber():**
	
 - isValidNumber returns true
 - card registered

**Predicates for method isValidNumber():**

| Criteria | Predicate |
| -------- | --------- |
| isValidNumber returns true | YES |
| - | NO |
| Credit Card registered | YES |
| - | NO |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|||


**Combination of predicates**:

| isValidNumber returns true  | Credit Card registered | Valid / Invalid | Description of the test case  | JUnit test case                                   |
|-----------------------------|------------------------|-----------------|-------------------------------|---------------------------------------------------|
| YES                         | YES                    | Valid           | isRegistered(number) -> true  | testCardRegisteredWValidCreditCard()              |
| -                           | NO                     | Invalid         | isRegistered(number) -> false | testCardRegisteredWValidCreditCardNotRegistered() |
| NO                          | -                      | Invalid         | isRegistered(number) -> false | testCardRegisteredWNoValidCreditCard()            |


# White Box Unit Tests

### Test cases definition
    
    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|--|--|
| CreditCardSystem | WBCreditCardSystem |
|||
||||

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

| Unit name        | Loop rows | Number of iterations | JUnit test case                              |
|------------------|-----------|----------------------|----------------------------------------------|
| CreditCardSystem | 37 .. 43  | 0                    | testValidNumberWithCorrectInput0Loops()      |
| -                | -         | 1                    | testValidNumberWithCorrectInput1Loops()      |
| -                | -         | >= 2                 | testValidNumberWithCorrectInputALotOfLoops() |

