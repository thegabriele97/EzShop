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
| Valid                  | YES                      | Valid         | User u = (1, "we", "mbare", "Administrator")<br>DataManager.getInstance().deleteUser(u)<br>-> true |testDeleteExistingUser()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingUser()|
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
| Valid                  | YES                      | Valid         | ProductType u = (1, ...)<br>DataManager.getInstance().deleteProductType(u)<br>-> true |testDeleteExistingProductType()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingProductType()|
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
| Valid                  | YES                      | Valid         | Position u = (1, ...)<br>DataManager.getInstance().deletePosition(u)<br>-> true |testDeleteExistingPosition()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingPosition()|
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
| Valid                  | YES                      | Valid         | Order u = (1, ...)<br>DataManager.getInstance().deleteOrder(u)<br>-> true |testDeleteExistingOrder()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingOrder()|
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
| Valid                  | YES                      | Valid         | Customer u = (1, ...)<br>DataManager.getInstance().deleteCustomer(u)<br>-> true |testDeleteExistingCustomer()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingCustomer()|
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
| Valid                  | YES                      | Valid         | LoyaltyCard u = (1, ...)<br>DataManager.getInstance().deleteLoyaltyCard(u)<br>-> true |testDeleteExistingLoyaltyCard()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingLoyaltyCard()|
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
| Valid                  | YES                      | Valid         | Sale u = (1, ...)<br>DataManager.getInstance().deleteSale(u)<br>-> true |testDeleteExistingSale()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingSale()|
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
| Valid                  | YES                      | Valid         | Return u = (1, ...)<br>DataManager.getInstance().deleteReturn(u)<br>-> true |testDeleteExistingReturn()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingReturn()|
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
| Valid                  | YES                      | Valid         | DummyCredit u = (1, ...)<br>DataManager.getInstance().deleteDummyCredit(u)<br>-> true |testDeleteExistingDummyCredit()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingDummyCredit()|
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
| Valid                  | YES                      | Valid         | DummyDebit u = (1, ...)<br>DataManager.getInstance().deleteDummyDebit(u)<br>-> true |testDeleteExistingDummyDebit()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingDummyDebit()|
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
| Valid                  | YES                      | Valid         | BalanceTransaction u = (1, ...)<br>DataManager.getInstance().deleteBalanceTransaction(u)<br>-> true |testDeleteExistingBalanceTransaction()|
| Valid                  | NO                       | Invalid           | ---- -> false |testDeleteNotExistingBalanceTransaction()|
| NULL                   | -                        | Invalid         | BalanceTransaction u = null<br>DataManager.getInstance().deleteBalanceTransaction(u)<br>-> false|testDeleteNullBalanceTransaction()|




### **Class *DataManger* - method *insertUser***



**Criteria for method *insertUser*:**


- Validity of User object
- Existence of User Object


**Predicates for method insertUser:**

| Criterion                | Predicate |
| ------------------------ | --------- |
| Validity of User object  | Valid     |
| -                        | NULL      |
|Existence of User Object | Yes |
| - | No |



**Boundaries for method insertUser**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertUser**


| Validity of User Object | Existence of User Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | User u1 = {1, "u1", "a", "Administrator"};<br />insertUser(u1);<br />insertUser(u1);<br /><br />-> false |testInsertExistingUser()|
| Valid                  | No                       | Valid           | User u1 = {1, "u1", "a", "Administrator"};<br />insertUser(u1);<br /><br />->  true | testInsertUser() |
| NULL                   | -                        | Invalid         | insertUser(null);<br /><br />-> false | testInsertNullUser() |



### **Class *DataManger* - method *insertProductType***



**Criteria for method *insertProductType*:**


- Validity of ProductType object
- Existence of ProductType Object


**Predicates for method insertProductType:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of ProductType object | Valid |
| - | NULL |
| Existence of ProductType Object | Yes |
| - | No |




**Boundaries for method insertProductType**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertProductType**


| Validity of ProductType Object | Existence of ProductType Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | ProductType p1 = {1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1"};<br />insertProductType(p1);<br />insertProductType(p1);<br /><br />-> false | testInsertExistingProductType() |
| Valid                  | No                       | Valid           | ProductType p1 = {1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1"};<br />insertProductType(p1);<br /><br />-> true | testInsertProductType() |
| NULL                   | -                        | Invalid         | insertProductType(null);<br /><br />->  false | testInsertNullProductType() |


### **Class *DataManger* - method *insertPosition***



**Criteria for method *insertPosition*:**


- Validity of Position object
- Existence of Position Object


**Predicates for method insertPosition:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Position object | Valid |
| - | NULL |
| Existence of Position Object | Yes |
| - | No |





**Boundaries for method insertPosition**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertPosition**

| Validity of Position Object | Existence of Position Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                   | Invalid         | Position p1 = {1, "a", 1, null};<br />insertPosition(p1);<br />insertPosition(p1);<br /><br />-> false | testInsertExistingPosition() |
| Valid                  | No                    | Valid           | Position p1 = {1, "a", 1, null};<br />insertPosition(p1);<br /><br />-> true | testInsertPosition() |
| NULL                   | -                        | Invalid         | insertPosition(null);<br /><br />->  false | testInsertNullPosition() |


### **Class *DataManger* - method *insertOrder***



**Criteria for method *insertOrder*:**


- Validity of Order object
- Existence of Order Object


**Predicates for method insertOrder:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Order object | Valid |
| - | NULL |
| Existence of Order Object | Yes |
| - | No |




**Boundaries for method insertOrder**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertOrder**

| Validity of Order Object | Existence of Order Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | Order o1 = {1, 1.0, 1, null, EOrderStatus.ISSUED};<br />insertOrder(o1);<br />insertOrder(o1);<br /><br />-> false | testInsertExistingOrder() |
| Valid                  | No                       | Valid           | Order o1 = {1, 1.0, 1, null, EOrderStatus.ISSUED};<br />insertOrder(o1);<br /><br />-> true | testInsertOrder() |
| NULL                   | -                        | Invalid         | insertOrder(null);<br /><br />-> false| testInsertNullOrder() |


### **Class *DataManger* - method *insertCustomer***



**Criteria for method *insertCustomer*:**


- Validity of Customer object
- Existence of Customer Object


**Predicates for method insertCustomer:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Customer object | Valid |
| - | NULL |
| Existence of Customer Object | Yes |
| - | No |


**Boundaries for method insertCustomer**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertCustomer**

| Validity of Customer Object | Existence of Customer Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | Customer c1 = {1, "c1", null};<br />insertCustomer(c1);<br />insertCustomer(c1);<br /><br />-> false | testInsertExistingCustomer() |
| Valid                  | No                       | Valid           | Customer c1 = {1, "c1", null};<br />insertCustomer(c1);<br /><br />-> true | testInsertCustomer() |
| NULL                   | -                        | Invalid         | insertCustomer(null);<br /><br />-> false| testInsertNullCustomer() |


### **Class *DataManger* - method *insertLoyaltyCard***



**Criteria for method *insertLoyaltyCard*:**


- Validity of LoyaltyCard object
- Existence of LoyaltyCard Object


**Predicates for method insertLoyaltyCard:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of LoyaltyCard object | Valid |
| - | NULL |
| Existence of LoyaltyCard Object | Yes |
| - | No |



**Boundaries for method insertLoyaltyCard**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertLoyaltyCard**

| Validity of LoyaltyCard Object | Existence of LoyaltyCard Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | LoyaltyCard lc1 = {"c1", 1, null};<br />insertLoyaltyCard(lc1);<br />insertLoyaltyCard(lc1);<br /><br />-> false | testInsertExistingLoyaltyCard() |
| Valid                  | No                       | Valid           | LoyaltyCard lc1 = {"c1", 1, null};<br />insertLoyaltyCard(lc1);<br /><br />-> true | testInsertLoyaltyCard() |
| NULL                   | -                        | Invalid         | insertLoyaltyCard(null);<br /><br />-> false| testInsertNullLoyaltyCard() |


### **Class *DataManger* - method *insertSale***



**Criteria for method *insertSale*:**


- Validity of Sale object
- Existence of Sale Object



**Predicates for method insertSale:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Sale object | Valid |
| - | NULL |
| Existence of Sale Object | Yes |
| - | No |




**Boundaries for method insertSale**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertSale**

| Validity of Sale Object | Existence of Sale Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | Sale s1 = {1, 0.1, null};<br />insertSale(s1);<br /><br />-> false | testInsertExistingSale() |
| Valid                  | No                       | Valid           | Sale s1 = {1, 0.1, null};<br />insertSale(s1);<br /><br />-> true | testInsertSale() |
| NULL                   | -                        | Invalid         | insertSale(null);<br /><br />-> false| testInsertNullSale() |


### **Class *DataManger* - method *insertReturn***



**Criteria for method *insertReturn*:**


- Validity of Return object
- Existence of Return Object


**Predicates for method insertReturn:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of Return object | Valid |
| - | NULL |
| Existence of Return Object | Yes |
| - | No |



**Boundaries for method insertReturn**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertReturn**

| Validity of Return Object | Existence of Return Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | CReturn r1 = {1, null};<br />insertReturn(r1);<br />insertReturn(r1);<br /><br />-> false | testInsertExistingReturn() |
| Valid                  | No                       | Valid           | CReturn r1 = {1, null};<br />insertReturn(r1);<br /><br />-> true | testInsertReturn() |
| NULL                   | -                        | Invalid         | insertReturn(null);<br /><br />-> false| testInsertNullReturn() |


### **Class *DataManger* - method *insertDummyCredit***



**Criteria for method *insertDummyCredit*:**


- Validity of DummyCredit object
- Existence of DummyCredit Object


**Predicates for method insertDummyCredit:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of DummyCredit object | Valid |
| - | NULL |
| Existence of DummyCredit Object | Yes |
| - | No |


**Boundaries for method insertDummyCredit**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertDummyCredit**



| Validity of DummyCredit object | Valid/Invalid | Description of the test case: example of input and output    | JUnit test case |
| ----------------------- | ------------- | ------------------------------------------------------------ | --------------- |
| Valid                   |  Valid        | DummyCredit dc1 = {1, 1.0};<br />insertDummyCredit(dc1);<br /><br />-> true | testInsertDummyCredit() |
| NULL                    | Invalid       | insertDummyCredit(null);<br /><br />-> false | testInsertNullDummyCredit() |

| Validity of DummyCredit Object | Existence of DummyCredit Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | Yes                      | Invalid         | DummyCredit dc1 = {1, 1.0};<br />insertDummyCredit(dc1);<br />insertDummyCredit(dc1);<br /><br />-> false | testInsertExistingDummyCredit() |
| Valid                  | No                       | Valid           | DummyCredit dc1 = {1, 1.0};<br />insertDummyCredit(dc1);<br /><br />-> true | testInsertDummyCredit() |
| NULL                   | -                        | Invalid         | insertDummyCredit(null);<br /><br />-> false | testInsertNullDummyCredit() |


### **Class *DataManger* - method *insertDummyDebit***



**Criteria for method *insertDummyDebit*:**


- Validity of DummyDebit object
- Existence of DummyDebit Object


**Predicates for method insertDummyDebit:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of DummyDebit object | Valid |
| - | NULL |
| Existence of DummyDebit Object | Yes |
| - | No |


**Boundaries for method insertDummyDebit**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertDummyDebit**

| Validity of DummyDebit Object | Existence of DummyDebit Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | DummyDebit dd1 = {1, 1.0};<br />insertDummyDebit(dd1);<br />insertDummyDebit(dd1);<br /><br />-> false | testInsertExistingDummyDebit() |
| Valid                  | NO                       | Valid           | DummyDebit dd1 = {1, 1.0};<br />insertDummyDebit(dd1);<br /><br />-> true | testInsertDummyDebit() |
| NULL                   | -                        | Invalid         | insertDummyDebit(null);<br /><br />->  false | testInsertNullDummyDebit() |



### **Class *DataManger* - method *insertBalanceTransaction***



**Criteria for method *insertBalanceTransaction*:**


- Validity of BalanceTransaction object
- Existence of BalanceTransaction Object


**Predicates for method insertBalanceTransaction:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of BalanceTransaction object | Valid |
| - | NULL |
| Existence of BalanceTransaction Object | Yes |
| - | No |


**Boundaries for method insertBalanceTransaction**:

| Criterion | Boundary values |
| --------- | --------------- |
|           |                 |



**Combination of predicates for method insertBalanceTransaction**

| Validity of BalanceTransaction Object | Existence of BalanceTransaction Object | Valid / Invalid | Description of the test case | JUnit test case |
|-------                 |-------                   |-------          |-------                       |-------|
| Valid                  | YES                      | Invalid         | BalanceTransaction bt1 = {1, 1.0};<br />insertBalanceTransaction(bt1);<br />insertBalanceTransaction(bt1);<br /><br />-> false | testInsertExistingBalanceTransaction() |
| Valid                  | NO                       | Valid           | BalanceTransaction bt1 = {1, 1.0};<br />insertBalanceTransaction(bt1);<br /><br />-> true | testInsertBalanceTransaction() |
| NULL                   | -                        | Invalid         | insertBalanceTransaction(null);<br /><br />-> false | testNullInsertBalanceTransaction() |

### **Class DataManager - method updateUser()**



**Criteria for method updateUser():**

- Validity of User Object
- Existence of User Object

**Predicates for method updateUser():**

| Criteria                 | Predicate |
| ------------------------ | --------- |
| Validity of User object  | Valid     |
| -                        | NULL      |
| Existence of User Object | Yes       |
| -                        | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |

**Combination of predicates**:


| Validity of User Object | Existence of User Object | Valid / Invalid | Description of the test case                                 | JUnit test case             |
| ----------------------- | ------------------------ | --------------- | ------------------------------------------------------------ | --------------------------- |
| Valid                   | YES                      | Valid           | User u = (1, "we", "mbare", "Administrator")<br>DataManager.getInstance().updateUser(u)<br>-> true | testUpdateExistingUser()    |
| Valid                   | NO                       | Invalid         | ---- -> false                                                | testUpdateNotExistingUser() |
| NULL                    | -                        | Invalid         | User u = null<br>DataManager.getInstance().updateUser(u)<br>-> false | testUpdateNullUser()        |

### **Class DataManager - updateProductType()**

**Criteria for method updateProductType():**

- Validity of ProductType object
- Existence of ProductType Object

**Predicates for method updateProductType():**

| Criteria                        | Predicate |
| ------------------------------- | --------- |
| Validity of ProductType object  | Valid     |
| -                               | NULL      |
| Existence of ProductType Object | Yes       |
| -                               | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |

**Combination of predicates**:


| Validity of ProductType Object | Existence of ProductType Object | Valid / Invalid | Description of the test case                                 | JUnit test case                    |
| ------------------------------ | ------------------------------- | --------------- | ------------------------------------------------------------ | ---------------------------------- |
| Valid                          | YES                             | Valid           | ProductType u = (1, ...)<br>DataManager.getInstance().updateProductType(u)<br>-> true | testUpdateExistingProductType()    |
| Valid                          | NO                              | Invalid         | ---- -> false                                                | testUpdateNotExistingProductType() |
| NULL                           | -                               | Invalid         | ProductType u = null<br>DataManager.getInstance().updateProductType(u)<br>-> false | testUpdateNullProductType()        |

### **Class DataManager - method updatePosition()**

**Criteria for method updatePosition():**

- Validity of Position object
- Existence of Position Object

**Predicates for method updatePosition():**

| Criteria                     | Predicate |
| ---------------------------- | --------- |
| Validity of Position object  | Valid     |
| -                            | NULL      |
| Existence of Position Object | Yes       |
| -                            | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Position Object | Existence of Position Object | Valid / Invalid | Description of the test case                                 | JUnit test case                 |
| --------------------------- | ---------------------------- | --------------- | ------------------------------------------------------------ | ------------------------------- |
| Valid                       | YES                          | Valid           | Position u = (1, ...)<br>DataManager.getInstance().updatePosition(u)<br>-> true | testUpdateExistingPosition()    |
| Valid                       | NO                           | Invalid         | ---- -> false                                                | testUpdateNotExistingPosition() |
| NULL                        | -                            | Invalid         | Position u = null<br>DataManager.getInstance().updatePosition(u)<br>-> false | testUpdateNullPosition()        |

### **Class DataManager - method deleteOrder()**

**Criteria for method deleteOrder():**

- Validity of Order object
- Existence of Order Object

**Predicates for method deleteOrder():**

| Criteria                  | Predicate |
| ------------------------- | --------- |
| Validity of Order object  | Valid     |
| -                         | NULL      |
| Existence of Order Object | Yes       |
| -                         | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Order Object | Existence of Order Object | Valid / Invalid | Description of the test case                                 | JUnit test case              |
| ------------------------ | ------------------------- | --------------- | ------------------------------------------------------------ | ---------------------------- |
| Valid                    | YES                       | Valid           | Order u = (1, ...)<br>DataManager.getInstance().deleteOrder(u)<br>-> true | testDeleteExistingOrder()    |
| Valid                    | NO                        | Invalid         | ---- -> false                                                | testDeleteNotExistingOrder() |
| NULL                     | -                         | Invalid         | Order u = null<br>DataManager.getInstance().deleteOrder(u)<br>-> false | testDeleteNullOrder()        |

### **Class DataManager - method deleteCustomer()**

**Criteria for method deleteCustomer():**

- Validity of Customer object
- Existence of Customer Object


**Predicates for method deleteCustomer():**

| Criteria                     | Predicate |
| ---------------------------- | --------- |
| Validity of Customer object  | Valid     |
| -                            | NULL      |
| Existence of Customer Object | Yes       |
| -                            | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |

**Combination of predicates**:


| Validity of Customer Object | Existence of Customer Object | Valid / Invalid | Description of the test case                                 | JUnit test case                 |
| --------------------------- | ---------------------------- | --------------- | ------------------------------------------------------------ | ------------------------------- |
| Valid                       | YES                          | Valid           | Customer u = (1, ...)<br>DataManager.getInstance().deleteCustomer(u)<br>-> true | testDeleteExistingCustomer()    |
| Valid                       | NO                           | Invalid         | ---- -> false                                                | testDeleteNotExistingCustomer() |
| NULL                        | -                            | Invalid         | Customer u = null<br>DataManager.getInstance().deleteCustomer(u)<br>-> false | testDeleteNullCustomer()        |

### **Class DataManager - method deleteLoyaltyCard()**

**Criteria for method deleteLoyaltyCard():**

- Validity of LoyaltyCard object
- Existence of LoyaltyCard Object


**Predicates for method deleteLoyaltyCard():**

| Criteria                        | Predicate |
| ------------------------------- | --------- |
| Validity of LoyaltyCard object  | Valid     |
| -                               | NULL      |
| Existence of LoyaltyCard Object | Yes       |
| -                               | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of LoyaltyCard Object | Existence of LoyaltyCard Object | Valid / Invalid | Description of the test case                                 | JUnit test case                    |
| ------------------------------ | ------------------------------- | --------------- | ------------------------------------------------------------ | ---------------------------------- |
| Valid                          | YES                             | Valid           | LoyaltyCard u = (1, ...)<br>DataManager.getInstance().deleteLoyaltyCard(u)<br>-> true | testDeleteExistingLoyaltyCard()    |
| Valid                          | NO                              | Invalid         | ---- -> false                                                | testDeleteNotExistingLoyaltyCard() |
| NULL                           | -                               | Invalid         | LoyaltyCard u = null<br>DataManager.getInstance().deleteLoyaltyCard(u)<br>-> false | testDeleteNullLoyaltyCard()        |

### **Class DataManager - method deleteSale()**


**Criteria for method deleteSale():**

- Validity of Sale object
- Existence of Sale Object


**Predicates for method deleteSale():**

| Criteria                 | Predicate |
| ------------------------ | --------- |
| Validity of Sale object  | Valid     |
| -                        | NULL      |
| Existence of Sale Object | Yes       |
| -                        | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Sale Object | Existence of Sale Object | Valid / Invalid | Description of the test case                                 | JUnit test case             |
| ----------------------- | ------------------------ | --------------- | ------------------------------------------------------------ | --------------------------- |
| Valid                   | YES                      | Valid           | Sale u = (1, ...)<br>DataManager.getInstance().deleteSale(u)<br>-> true | testDeleteExistingSale()    |
| Valid                   | NO                       | Invalid         | ---- -> false                                                | testDeleteNotExistingSale() |
| NULL                    | -                        | Invalid         | Sale u = null<br>DataManager.getInstance().deleteSale(u)<br>-> false | testDeleteNullSale()        |

### **Class DataManager - method deleteReturn()**

**Criteria for method deleteReturn():**

- Validity of Return object
- Existence of Return Object


**Predicates for method deleteReturn():**

| Criteria                   | Predicate |
| -------------------------- | --------- |
| Validity of Return object  | Valid     |
| -                          | NULL      |
| Existence of Return Object | Yes       |
| -                          | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of Return Object | Existence of Return Object | Valid / Invalid | Description of the test case                                 | JUnit test case               |
| ------------------------- | -------------------------- | --------------- | ------------------------------------------------------------ | ----------------------------- |
| Valid                     | YES                        | Valid           | Return u = (1, ...)<br>DataManager.getInstance().deleteReturn(u)<br>-> true | testDeleteExistingReturn()    |
| Valid                     | NO                         | Invalid         | ---- -> false                                                | testDeleteNotExistingReturn() |
| NULL                      | -                          | Invalid         | Return u = null<br>DataManager.getInstance().deleteReturn(u)<br>-> false | testDeleteNullReturn()        |

### **Class DataManager - method deleteDummyCredit()**

**Criteria for method deleteDummyCredit():**

- Validity of DummyCredit object
- Existence of DummyCredit Object


**Predicates for method deleteDummyCredit():**

| Criteria                        | Predicate |
| ------------------------------- | --------- |
| Validity of DummyCredit object  | Valid     |
| -                               | NULL      |
| Existence of DummyCredit Object | Yes       |
| -                               | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of DummyCredit Object | Existence of DummyCredit Object | Valid / Invalid | Description of the test case                                 | JUnit test case                    |
| ------------------------------ | ------------------------------- | --------------- | ------------------------------------------------------------ | ---------------------------------- |
| Valid                          | YES                             | Valid           | DummyCredit u = (1, ...)<br>DataManager.getInstance().deleteDummyCredit(u)<br>-> true | testDeleteExistingDummyCredit()    |
| Valid                          | NO                              | Invalid         | ---- -> false                                                | testDeleteNotExistingDummyCredit() |
| NULL                           | -                               | Invalid         | DummyCredit u = null<br>DataManager.getInstance().deleteDummyCredit(u)<br>-> false | testDeleteNullDummyCredit()        |


### **Class DataManager - method deleteDummyDebit()**

**Criteria for method deleteDummyDebit():**

- Validity of DummyDebit object
- Existence of DummyDebit Object


**Predicates for method deleteDummyDebit():**

| Criteria                       | Predicate |
| ------------------------------ | --------- |
| Validity of DummyDebit object  | Valid     |
| -                              | NULL      |
| Existence of DummyDebit Object | Yes       |
| -                              | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of DummyDebit Object | Existence of DummyDebit Object | Valid / Invalid | Description of the test case                                 | JUnit test case                   |
| ----------------------------- | ------------------------------ | --------------- | ------------------------------------------------------------ | --------------------------------- |
| Valid                         | YES                            | Valid           | DummyDebit u = (1, ...)<br>DataManager.getInstance().deleteDummyDebit(u)<br>-> true | testDeleteExistingDummyDebit()    |
| Valid                         | NO                             | Invalid         | ---- -> false                                                | testDeleteNotExistingDummyDebit() |
| NULL                          | -                              | Invalid         | DummyDebit u = null<br>DataManager.getInstance().deleteDummyDebit(u)<br>-> false | testDeleteNullDummyDebit()        |


### **Class DataManager - method deleteBalanceTransaction()**

**Criteria for method deleteBalanceTransaction():**

- Validity of BalanceTransaction object
- Existence of BalanceTransaction Object


**Predicates for method deleteBalanceTransaction():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Validity of BalanceTransaction object  | Valid     |
| -                                      | NULL      |
| Existence of BalanceTransaction Object | Yes       |
| -                                      | No        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |


**Combination of predicates**:


| Validity of BalanceTransaction Object | Existence of BalanceTransaction Object | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | --------------- | ------------------------------------------------------------ | ----------------------------------------- |
| Valid                                 | YES                                    | Valid           | BalanceTransaction u = (1, ...)<br>DataManager.getInstance().deleteBalanceTransaction(u)<br>-> true | testDeleteExistingBalanceTransaction()    |
| Valid                                 | NO                                     | Invalid         | ---- -> false                                                | testDeleteNotExistingBalanceTransaction() |
| NULL                                  | -                                      | Invalid         | BalanceTransaction u = null<br>DataManager.getInstance().deleteBalanceTransaction(u)<br>-> false | testDeleteNullBalanceTransaction()        |



### **Class *EZShop* - method *isValidBarcode***



**Criteria for method *isValidBarcode*:**


- Validity of the String parameter
- Length of the String


**Predicates for method isValidBarcode:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the String parameter | Valid |
| - | NULL |
| Length of the String | [12, 14] |
| - | [0,12) |
| - | (14, maxint) |
| Value of every char of the String parameter | ['0'(48), '9'(57)] |
| - | [0, 48) |
| - | (57, 127] |

**Boundaries for method isValidBarcode**:

| Criterion | Boundary values |
| --------- | --------------- |
| Length of the String | 11, 12, 14, 15                |
| Value of every char of the String parameter | '/'(47), '0'(48), '9'(57), ':'(58) |



**Combination of predicates for method isValidBarcode**

| Validity of the String parameter | Length of the String |  Value of every char of the String parameter | Valid / Invalid | Description of the test case | JUnit test case |
|-------                           |-------               |-------                                       |-------          |-------                       |-------          |
| Valid                            | [12, 14]             | ['0'(48), '9'(57)]                           | Valid           | isValidBarcode("000000000000") -> true<br/>isValidBarcode("00000000000000") -> true<br/>isValidBarcode("999999999993") -> true<br/>isValidBarcode("99999999999997") -> true | testIsValidBarcode() |
| NULL                            | *          | *                         | Invalid           | isValidBarcode(null) -> false | testIsValidNullBarcode() |
| *                            | [0,12) | *                         | Invalid           | isValidBarcode("00000000000") -> false | testIsValidShorterBarcode() |
| *                            | (14, maxint) | *                         | Invalid           | isValidBarcode("000000000000000") -> false | testIsValidLongerBarcode() |
| *                            | * | [0, 48) | Invalid           | isValidBarcode("000000000000/") -> false | testIsValidBarcodeWithNotNumericChar() |
| *                            | * | (57, 127] | Invalid           | isValidBarcode("000000000000:") -> false | testIsValidBarcodeWithNotNumericChar() |





# White Box Unit Tests

### Test cases definition

    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|-----------|-----------------|
| CreditCardSystem | WBCreditCardSystem |
| | |
| | |

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

