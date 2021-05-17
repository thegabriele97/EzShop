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

### **Class DataManager - method updateOrder()**

**Criteria for method updateOrder():**

- Validity of Order object
- Existence of Order Object

**Predicates for method updateOrder():**

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
| Valid                    | YES                       | Valid           | Order u = (1, ...)<br>DataManager.getInstance().updateOrder(u)<br>-> true | testUpdateExistingOrder()    |
| Valid                    | NO                        | Invalid         | ---- -> false                                                | testUpdateNotExistingOrder() |
| NULL                     | -                         | Invalid         | Order u = null<br>DataManager.getInstance().updateOrder(u)<br>-> false | testUpdateNullOrder()        |

### **Class DataManager - method updateCustomer()**

**Criteria for method updateCustomer():**

- Validity of Customer object
- Existence of Customer Object


**Predicates for method updateCustomer():**

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
| Valid                       | YES                          | Valid           | Customer u = (1, ...)<br>DataManager.getInstance().updateCustomer(u)<br>-> true | testUpdateExistingCustomer()    |
| Valid                       | NO                           | Invalid         | ---- -> false                                                | testUpdateNotExistingCustomer() |
| NULL                        | -                            | Invalid         | Customer u = null<br>DataManager.getInstance().updateCustomer(u)<br>-> false | testUpdateNullCustomer()        |

### **Class DataManager - method updateLoyaltyCard()**

**Criteria for method updateLoyaltyCard():**

- Validity of LoyaltyCard object
- Existence of LoyaltyCard Object


**Predicates for method updateLoyaltyCard():**

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
| Valid                          | YES                             | Valid           | LoyaltyCard u = (1, ...)<br>DataManager.getInstance().updateLoyaltyCard(u)<br>-> true | testUpdateExistingLoyaltyCard()    |
| Valid                          | NO                              | Invalid         | ---- -> false                                                | testUpdateNotExistingLoyaltyCard() |
| NULL                           | -                               | Invalid         | LoyaltyCard u = null<br>DataManager.getInstance().updateLoyaltyCard(u)<br>-> false | testUpdateNullLoyaltyCard()        |

### **Class DataManager - method updateSale()**


**Criteria for method updateSale():**

- Validity of Sale object
- Existence of Sale Object


**Predicates for method updateSale():**

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
| Valid                   | YES                      | Valid           | Sale u = (1, ...)<br>DataManager.getInstance().updateSale(u)<br>-> true | testUpdateExistingSale()    |
| Valid                   | NO                       | Invalid         | ---- -> false                                                | testUpdateNotExistingSale() |
| NULL                    | -                        | Invalid         | Sale u = null<br>DataManager.getInstance().updateSale(u)<br>-> false | testUpdateNullSale()        |

### **Class DataManager - method updateReturn()**

**Criteria for method updateReturn():**

- Validity of Return object
- Existence of Return Object


**Predicates for method updateReturn():**

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
| Valid                     | YES                        | Valid           | Return u = (1, ...)<br>DataManager.getInstance().updateReturn(u)<br>-> true | testUpdateExistingReturn()    |
| Valid                     | NO                         | Invalid         | ---- -> false                                                | testUpdateNotExistingReturn() |
| NULL                      | -                          | Invalid         | Return u = null<br>DataManager.getInstance().updateReturn(u)<br>-> false | testUpdateNullReturn()        |

### **Class DataManager - method updateDummyCredit()**

**Criteria for method updateDummyCredit():**

- Validity of DummyCredit object
- Existence of DummyCredit Object


**Predicates for method updateDummyCredit():**

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
| Valid                          | YES                             | Valid           | DummyCredit u = (1, ...)<br>DataManager.getInstance().updateDummyCredit(u)<br>-> true | testUpdateExistingDummyCredit()    |
| Valid                          | NO                              | Invalid         | ---- -> false                                                | testUpdateNotExistingDummyCredit() |
| NULL                           | -                               | Invalid         | DummyCredit u = null<br>DataManager.getInstance().updateDummyCredit(u)<br>-> false | testUpdateNullDummyCredit()        |


### **Class DataManager - method updateDummyDebit()**

**Criteria for method updateDummyDebit():**

- Validity of DummyDebit object
- Existence of DummyDebit Object


**Predicates for method updateDummyDebit():**

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
| Valid                         | YES                            | Valid           | DummyDebit u = (1, ...)<br>DataManager.getInstance().updateDummyDebit(u)<br>-> true | testUpdateExistingDummyDebit()    |
| Valid                         | NO                             | Invalid         | ---- -> false                                                | testUpdateNotExistingDummyDebit() |
| NULL                          | -                              | Invalid         | DummyDebit u = null<br>DataManager.getInstance().updateDummyDebit(u)<br>-> false | testUpdateNullDummyDebit()        |


### **Class DataManager - method updateBalanceTransaction()**

**Criteria for method updateBalanceTransaction():**

- Validity of BalanceTransaction object
- Existence of BalanceTransaction Object


**Predicates for method updateBalanceTransaction():**

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
| Valid                                 | YES                                    | Valid           | BalanceTransaction u = (1, ...)<br>DataManager.getInstance().updateBalanceTransaction(u)<br>-> true | testUpdateExistingBalanceTransaction()    |
| Valid                                 | NO                                     | Invalid         | ---- -> false                                                | testUpdateNotExistingBalanceTransaction() |
| NULL                                  | -                                      | Invalid         | BalanceTransaction u = null<br>DataManager.getInstance().updateBalanceTransaction(u)<br>-> false | testUpdateNullBalanceTransaction()        |

### **Class DataManager - method getUsers()**

**Criteria for method getUsers():**

- Presence of added elements in users list


**Predicates for method getUsers():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getUsers().size() == 0 --> true | testEmptyGetUsers()    |
| YES                                  | Valid         | User user = new User(1, "Gianni", "14adfea356e*", "Cashier")<br>DataManager.getInstance().insertUser(user) <br> DataManager.getInstance().getUsers().contains(user) -> true | testFilledGetUsers()        |

### **Class DataManager - method getProductTypes()**

**Criteria for method getProductTypes():**

- Presence of added elements in users list


**Predicates for method getProductTypes():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getProductTypes().size() == 0 --> true | testEmptyGetProductTypes()    |
| YES                                     | Valid         | ProductType pt = new ProductType(1, "sasseresos", "fancy thing", 12.0, 7, 0.25, "nothingtosay", null)<br/>DataManager.getInstance().insertProductType(pt)<br/> DataManager.getInstance().getProductTypes().contains(pt) -> true | testFilledGetProductTypes()        |

### **Class DataManager - method getPositions()**

**Criteria for method getPositions():**

- Presence of added elements in users list


**Predicates for method getPositions():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getPositions().size() == 0 --> true | testEmptyGetPositions()    |
| YES                                     | Valid         | Position pos = new Position(1, "a", 1, null)<br/>DataManager.getInstance().insertPosition(pos)<br/> DataManager.getInstance().getPositions().contains(pos) -> true | testFilledGetPositions()        |

### **Class DataManager - method getOrders()**

**Criteria for method getOrders():**

- Presence of added elements in users list


**Predicates for method getOrders():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getOrders().size() == 0 --> true | testEmptyGetOrders()    |
| YES                                     | Valid         | Order ord = new Order(1, 1.0, 1, new ProductType(1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1"), EOrderStatus.ISSUED)<br/> DataManager.getInstance().insertOrder(ord)<br/> DataManager.getInstance().getOrders().contains(ord) -> true | testFilledGetOrders()        |

### **Class DataManager - method getCustomers()**

**Criteria for method getCustomers():**

- Presence of added elements in users list


**Predicates for method getCustomers():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getCustomers().size() == 0 --> true | testEmptyGetCustomers()    |
| YES                                     | Valid         | Customer c = new Customer(1, "c1", null)<br/>DataManager.getInstance().insertCustomer(c)<br/>DataManager.getInstance().getCustomers().contains(c) -> true | testFilledGetCustomers()        |


### **Class DataManager - method getLoyaltyCards()**

**Criteria for method getLoyaltyCards():**

- Presence of added elements in users list


**Predicates for method getLoyaltyCards():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getLoyaltyCards().size() == 0 --> true | testEmptyGetLoyaltyCards()    |
| YES                                     | Valid         | LoyaltyCard lt = new LoyaltyCard("0001", 1, null)<br/>DataManager.getInstance().insertLoyaltyCard(lt)<br/>DataManager.getInstance().getLoyaltyCards().contains(lt) -> true | testFilledGetLoyaltyCards()        |


### **Class DataManager - method getReturns()**

**Criteria for method getReturns():**

- Presence of added elements in users list


**Predicates for method getReturns():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getReturns().size() == 0 --> true | testEmptyGetReturns()    |
| YES                                     | Valid         | CReturn ret = new CReturn(1, null)<br/>DataManager.getInstance().insertReturn(ret)<br/>DataManager.getInstance().getReturns().contains(ret) -> true | testFilledGetReturns()        |


### **Class DataManager - method getDummyCredits()**

**Criteria for method getDummyCredits():**

- Presence of added elements in users list


**Predicates for method getDummyCredits():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getDummyCredits().size() == 0 --> true | testEmptyGetDummyCredits()    |
| YES                                     | Valid         | DummyCredit dc = new DummyCredit(1, 1.0)<br/>DataManager.getInstance().insertDummyCredit(dc)<br/>DataManager.getInstance().getDummyCredits().contains(dc) -> true | testFilledGetDummyCredits()        |



### **Class DataManager - method getDummyDebits()**

**Criteria for method getDummyDebits():**

- Presence of added elements in users list


**Predicates for method getDummyDebits():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getDummyDebits().size() == 0 --> true | testEmptyGetDummyDebits()    |
| YES                                     | Valid         | DummyDebit dd = new DummyDebit(1, -1.0)<br/>DataManager.getInstance().insertDummyDebit(dd)<br/>DataManager.getInstance().getDummyDebits().contains(dd) -> true | testFilledGetDummyDebits()        |


### **Class DataManager - method getBalanceTransactions()**

**Criteria for method getBalanceTransactions():**

- Presence of added elements in users list


**Predicates for method getBalanceTransactions():**

| Criteria                               | Predicate |
| -------------------------------------- | --------- |
| Presence of added elements in users list     | Valid     |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |


**Combination of predicates**:


| Presence of added elements in users list | Valid / Invalid | Description of the test case                                 | JUnit test case                           |
| ------------------------------------- | -------------------------------------- | ------------------------------------------------------------ | ----------------------------------------- |
| NO                                 | Valid           | DataManager.getInstance().getBalanceTransactions().size() == 0 --> true | testEmptyGetBalanceTransactions()    |
| YES                                     | Valid         | CreditTransaction bt1 = new CreditTransaction(1, new Sale(1, 0.1, null))<br/>DataManager.getInstance().insertBalanceTransaction(bt1)<br/> DataManager.getInstance().getBalanceTransactions().contains(bt1) -> true | testFilledGetBalanceTransactions()        |


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

### **Class *CrediCardSystem* - method *isValidNumber***


**Criteria for method *isValidNumber*:**


- Validity of the String parameter
- Length of the String parameter
- Value of every char of the String parameter


**Predicates for method isValidNumber:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the String parameter | Valid |
| - | NULL |
| Length of the String | [13, 16] |
| - | [0,12) |
| - | (16, maxint) |
| Value of every char of the String parameter | ['0'(48), '9'(57)] |
| - | [0, 48) |
| - | (57, 127] |

**Boundaries for method isValidNumber**:

| Criterion | Boundary values |
| --------- | --------------- |
| Length of the String | 12, 13, 16, 17                |
| Value of every char of the String parameter | '/'(47), '0'(48), '9'(57), ':'(58) |



**Combination of predicates for method isValidNumber**

| Validity of the String parameter | Length of the String |  Value of every char of the String parameter | Valid / Invalid | Description of the test case | JUnit test case |
|-------                           |-------               |-------                                       |-------          |-------                       |-------          |
| Valid                            | [13, 16]             | ['0'(48), '9'(57)]                           | Valid           | isValidNumber("000000000000000") -> true<br/> isValidNumber("5569755825672968") -> true | testValidCreditCardWithAll0s()<br/>  testValidCreditCardWithValidNumber()|
| NULL                            | *          | *                         | Invalid           | isValidNumber(null) -> false | testValidCreditCardWithNullString() |
| *                            | [0,13) | *                         | Invalid           | isValidNumber("") -> false <br/> isValidNumber("142") -> false| testValidCreditCardWithShortString() <br/>  testValidCreditCardWithEmptyString() |
| *                            | (16, maxint) | *                         | Invalid           | isValidNumber("1432545245185529354022") -> false | testValidCreditCardWithLongString() |
| *                            | * | [0, 48) | Invalid           | isValidNumber("123/23//2233635") -> false | testValidCreditCardWithCharacters() |
| *                            | * | (57, 127] | Invalid           | isValidNumber("123:23::2233635") -> false | testValidCreditCardWithCharacters() |

### **Class *CrediCardSystem* - method *isRegistered***


**Criteria for method *isRegistered*:**


- Validity of the String parameter
- Length of the String parameter
- Value of every char of the String parameter 
- The string is registered in the db


**Predicates for method isRegistered:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the String parameter | Valid |
| - | NULL |
| Length of the String | [13, 16] |
| - | [0,12) |
| - | (16, maxint) |
| Value of every char of the String parameter | ['0'(48), '9'(57)] |
| - | [0, 48) |
| - | (57, 127] |
| Presence of the String parameter in the db | Registered |
| - | Unregistered |


**Boundaries for method isRegistered**:

| Criterion | Boundary values |
| --------- | --------------- |
| Length of the String | 12, 13, 16, 17                |
| Value of every char of the String parameter | '/'(47), '0'(48), '9'(57), ':'(58) |



**Combination of predicates for method isRegistered**

| Validity of the String parameter | Length of the String |  Value of every char of the String parameter | Presence of the String parameter in the db | Valid / Invalid | Description of the test case | JUnit test case |
|-------                           |-------               |-------                                       |-------          |-------                       |-------  |-------        |
| Valid                            | [13, 16]             | ['0'(48), '9'(57)]            |Registered               | Valid           | isRegistered("9254347527611304") -> true | testCardRegisteredWValidCreditCard()|
| NULL                            | *          | *              | *           | Invalid           | isRegistered(null) -> false | testCardRegisteredWNullString() |
| *                            | [0,13) | *               | *          | Invalid           | isRegistered("") -> false | testCardRegisteredWEmptyString() <br/>  testValidCreditCardWithEmptyString() |
| *                            | * | [0, 48) | * | Invalid           | isRegistered("123/23//2233635") -> false | testValidCreditCardWithCharacters() |
| *                            | * | (57, 127] | * | Invalid           | isRegistered("123:23::2233635") -> false | testValidCreditCardWithCharacters() |
| *                            | * | * | Unregistered | Invalid           | isRegistered("5569755825672968") -> false | testCardRegisteredWValidCreditCardNotRegistered() |


### **Class *CrediCardSystem* - method *hasEnoughBalance***


**Criteria for method *hasEnoughBalance*:**


- Validity of the String parameter
- Length of the String parameter
- Value of every char of the String parameter 
- The string is registered in the db
- Validity of the double parameter


**Predicates for method hasEnoughBalance:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the String parameter | Valid |
| - | NULL |
| Length of the String | [13, 16] |
| - | [0,12) |
| - | (16, maxint) |
| Value of every char of the String parameter | ['0'(48), '9'(57)] |
| - | [0, 48) |
| - | (57, 127] |
| Validity of the double parameter | Valid |
| - | NaN |
| - | POSITIVE_INFINITY |
| - | NEGATIVE_INFINITY |
| Presence of the String parameter in the db | Registered |
| - | Unregistered |

**Boundaries for method hasEnoughBalance**:

| Criterion | Boundary values |
| --------- | --------------- |
| Length of the String | 12, 13, 16, 17                |
| Value of every char of the String parameter | '/'(47), '0'(48), '9'(57), ':'(58) |



**Combination of predicates for method hasEnoughBalance**

| Validity of the String parameter | Length of the String |  Value of every char of the String parameter | Presence of the String parameter in the db | Valid / Invalid | Description of the test case | JUnit test case |
|-------                           |-------               |-------                                       |-------          |-------                       |-------  |-------        |
| Valid                            | [13, 16]             | ['0'(48), '9'(57)]                          | Registered | Valid           | hasEnoughBalance("9254347527611304", 10.0) -> true<br/> hasEnoughBalance("9254347527611304", -10.0)<br/> hasEnoughBalance("9254347527611304", 0.0) | testHasEnoughBalanceWValidCreditCard()<br/> testHasBalanceWNegativeToRemove()<br/> testHasBalanceWZeroToRemove() |
| NULL                            | *          | *                   | *      | Invalid           | hasEnoughBalance(null, 10.0) -> false | testHasBalanceWInvalidCreditCard() |
| *                            | [0,13) | *                | *         | Invalid           | hasEnoughBalance("") -> false | testHasBalanceWInvalidCreditCard()  |
| *                            | * | [0, 48) | * | Invalid           | hasEnoughBalance("123/23//2233635", 10.0) -> false | testHasBalanceWInvalidCreditCard() |
| *                            | * | (57, 127] | * | Invalid           | hasEnoughBalance("123:23::2233635", 10.0) -> false | testHasBalanceWInvalidCreditCard() |
| *                            | * | * | Unregistered | Invalid           | hasEnoughBalance("5569755825672968") -> false | testHasBalanceWUnregisteredCreditCard() |
| *                            | * | NaN | * | Invalid           | hasEnoughBalance("9254347527611304", Double.NaN) -> false | testHasBalanceWNaNToRemove() |
| *                            | * | POSITIVE_INFINITY | * |  Invalid           | hasEnoughBalance("9254347527611304", Double.POSITIVE_INFINITY) -> false | testHasBalanceWPositiveInfinityToRemove() |
| *                            | * | NEGATIVE_INFINITY | * | Invalid           | hasEnoughBalance("9254347527611304", Double.NEGATIVE_INFINITY) -> false | testHasBalanceWNegativeInfinityToRemove() |

### **Class *CrediCardSystem* - method *updateBalance***


**Criteria for method *updateBalance*:**


- Validity of the String parameter
- Length of the String parameter
- Value of every char of the String parameter 
- The string is registered in the db
- Validity of the double parameter


**Predicates for method updateBalance:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of the String parameter | Valid |
| - | NULL |
| Length of the String | [13, 16] |
| - | [0,12) |
| - | (16, maxint) |
| Value of every char of the String parameter | ['0'(48), '9'(57)] |
| - | [0, 48) |
| - | (57, 127] |
| Validity of the double parameter | Valid |
| - | NaN |
| - | POSITIVE_INFINITY |
| - | NEGATIVE_INFINITY |
| Presence of the String parameter in the db | Registered |
| - | Unregistered |

**Boundaries for method updateBalance**:

| Criterion | Boundary values |
| --------- | --------------- |
| Length of the String | 12, 13, 16, 17                |
| Value of every char of the String parameter | '/'(47), '0'(48), '9'(57), ':'(58) |



**Combination of predicates for method updateBalance**

| Validity of the String parameter | Length of the String |  Value of every char of the String parameter | Presence of the String parameter in the db | Valid / Invalid | Description of the test case | JUnit test case |
|-------                           |-------               |-------                                       |-------          |-------                       |-------  |-------        |
| Valid                            | [13, 16]             | ['0'(48), '9'(57)]                          | Registered | Valid           | updateBalance("9254347527611304", 10.0) -> true<br/> updateBalance("9254347527611304", -10.0)<br/> updateBalance("9254347527611304", 0.0) | testUpdateBalanceWValidCreditCard()<br/> testUpdateBalanceWNegativeToRemove()<br/> testUpdateBalanceWZeroToRemove() |
| NULL                            | *          | *                   | *      | Invalid           | updateBalance(null, 10.0) -> false | testUpdateBalanceWInvalidCreditCard() |
| *                            | [0,13) | *                | *         | Invalid           | updateBalance("") -> false | testUpdateBalanceWInvalidCreditCard()  |
| *                            | * | [0, 48) | * | Invalid           | updateBalance("123/23//2233635", 10.0) -> false | testUpdateBalanceWInvalidCreditCard() |
| *                            | * | (57, 127] | * | Invalid           | updateBalance("123:23::2233635", 10.0) -> false | testUpdateBalanceWInvalidCreditCard() |
| *                            | * | * | Unregistered | Invalid           | updateBalance("5569755825672968") -> false | testUpdateBalanceWUnregisteredCreditCard() |
| *                            | * | NaN | * | Invalid           | updateBalance("9254347527611304", Double.NaN) -> false | testUpdateBalanceWNaNToRemove() |
| *                            | * | POSITIVE_INFINITY | * |  Invalid           | updateBalance("9254347527611304", Double.POSITIVE_INFINITY) -> false | testUpdateBalanceWPositiveInfinityToRemove() |
| *                            | * | NEGATIVE_INFINITY | * | Invalid           | updateBalance("9254347527611304", Double.NEGATIVE_INFINITY) -> false | testUpdateBalanceWNegativeInfinityToRemove() |





# White Box Unit Tests

### Test cases definition

    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|-----------|-----------------|
| CreditCardSystem | WBCreditCardSystem |
| User | UserTest |
| | |

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

| Unit name        | Loop rows | Number of iterations | JUnit test case                              |
|------------------|-----------|----------------------|----------------------------------------------|
| CreditCardSystem | 38 .. 44  | 13                    | testValidNumberWithCorrectInput13Loops()      |
| -                | -         | 16                    | testValidNumberWithCorrectInput16Loops()      |

