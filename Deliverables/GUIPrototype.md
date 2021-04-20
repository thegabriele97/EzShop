# Graphical User Interface Prototype  

Authors: Baldazzi Alessandro, D'Anzi Francesco, Galota Simone, La Greca Salvatore Gabriele

Date: 20/04/2021

Version: 1.0

## Login Page
![](assets/images/LoginPage.png)

## Logged User
| Employee View             |  Owner View |
:-------------------------:|:-------------------------:
| ![Logged User (Employee)](assets/images/UserPage.png) | ![Logged User (Employee)](assets/images/AdminPage.png) |

## Accounting
![Logged User (Employee)](assets/images/Accounting.png)

## Employees List
![](assets/images/Employee-Search.png)

## Use case 1, UC1 - Adding Employees
![](assets/images/Employee-Add(UC1).png)

## Scenario 1.1 and 1.2
| Successful adding        |  Error with adding      |
:-------------------------:|:-------------------------:
| ![](assets/images/Employee-Add-Success(Scenario1.1).png) | ![](assets/images/Employee-Add-Error(Scenario1.2).png) |

## Use case 2, UC2 - Modifying Employees
![](assets/images/Employee-Modify(UC2).png)

## Scenario 2.1 and 2.2
| Successful modifying     |  Error with modifying      |
:-------------------------:|:-------------------------:
| ![](assets/images/Employee-Modify-Success(Scenario2.1).png) | ![](assets/images/Employee-Modify-Error(Scenario2.2).png) |

## Use case 3, UC3 - Deleting Employees
![](assets/images/Employee-Delete(UC3).png)

## Customers List
![](assets/images/Customer-Search(UC7).png)

## Use case 4, UC4 - Adding Customers
![](assets/images/Customer-Add(UC4).png)

## Scenario 4.1 and 4.2
| Successful adding        |  Error with adding      |
:-------------------------:|:-------------------------:
| ![](assets/images/Customer-Add-Success(Scenario4.1).png) | ![](assets/images/Customer-Add-Error(Scenario4.2).png) |

## Use case 5, UC5 - Modifying Customers
![](assets/images/Customer-Modify(UC5).png)

## Scenario 5.1 and 5.2
| Successful modifying     |  Error with modifying      |
:-------------------------:|:-------------------------:
| ![](assets/images/Customer-Modify-Success(Scenario5.1).png) | ![](assets/images/Customer-Modify-Error(Scenario5.2).png) |

## Use case 6, UC6 - Deleting Customers
![](assets/images/Customer-Delete(UC6).png)

## Use case 8, UC8 - Managing Sales 

| Today Sales             |  All Sales |
:-------------------------:|:-------------------------:
| ![Logged User (Employee)](assets/images/B-SalesToday.png) | ![Logged User (Employee)](assets/images/C-SalesEver.png) |

When the User clicks on "Sales" from the Home page, a list of previous sales is shown to give a fast look to them.

A big button "GO TO CART" is available to let the employee to start a new sale (or to restore a previous one if it was not committed).

## Scenario 8.1 (Creating a shopping cart)
![Logged User (Employee)](assets/images/D-Cart(8.1).png)

Here the employee can fill the cart with all the items to sale (they can be inserted using a Barcode Reader or manually by clicking on **Search in Inventory** button). Is possible to change the quantity for each inserted item too. 

It's also possible to link the cart to a customer, by clicking on **Link to a Customer** button. The selected customer will be shown on the right side, in the **Summary** part of the page.

In the **Summary** it's possible to see the cart's subtotal, the computed VAT and the Total. 

A button **PAY** is available to commit the cart and let the customer pay in order to conclude the sale.

## Scenario 8.2 (Creating a shopping cart and inserts a quantity of an item not available in inventory)
![Logged User (Employee)](assets/images/E-Cart(8.2).png)

In the case that a wrong quantity is inserted, an Error Box is showed, informing the employee that the desired quantity is not available.

## Use case 9, UC9 - Managing Sales (Committing shopping cart)

## Scenario 9.1 (Committing a shopping cart)
![Logged User (Employee)](assets/images/F-Checkout(9.1).png)

Here a frozen version of the Cart is shown (not possible to change quantity of an item, at most is possible to remove it). It's always possible to go back to the cart by pressing **Go back to the Cart** button.

On the right side, we always have our **Summary** with two buttons:

- Pay **With Cash**: lets the customer pay with cash through the internal Cash Register
- Pay **With POS**: activates the external POS and lets the customer pay with the supported payment type

| Payment with Cash            |  Payment with POS |
:-------------------------:|:-------------------------:
| ![Logged User (Employee)](assets/images/G-Checkout(9.1)-CASH.png) | ![Logged User (Employee)](assets/images/H-Checkout(9.1)-POS.png) |

## Scenario 9.2 - Scenario 9.3 - Scenario 9.4

| Problems with Printer            |  Problems with POS |
:-------------------------:|:-------------------------:
| ![Logged User (Employee)](assets/images/J-9.3.png) | ![Logged User (Employee)](assets/images/M-Checkout(9.4).png) |



## Use case 10, UC10 - Managing Inventory (Register new item)
![](assets/images/A.png)

## Scenario 10.1 and 10.2
![](assets/images/B.png)

| Add successfully      |  Add with error |
:-------------------------:|:-------------------------:
| ![](assets/images/D.png) | ![](assets/images/C.png) |

![](assets/images/E.png)


## Use case 11, UC11 - Managing Inventory (Modify quantity/price of an existing item)
## Scenario 11.1
| Modify view     |  View after modify |
:-------------------------:|:-------------------------:
| ![](assets/images/F.png)| ![](assets/images/G.png) |

## Use case 12, UC12 - Managing Inventory (Delete an item)
## Scenario 12.1
| Delete view     |  View after Delete |
:-------------------------:|:-------------------------:
| ![](assets/images/H.png)| ![](assets/images/I.png)|


## Use case 13, UC13 - Managing Inventory (Search an item)
## Scenario 13.1
| Search view     |  View after Search |
:-------------------------:|:-------------------------:
| ![](assets/images/L.png)| ![](assets/images/M.png)|

## Suppliers List
![](assets/images/Supplier-Search(UC17).png)

## Use case 14, UC14 - Adding Suppliers
![](assets/images/Supplier-Add(UC14).png)

## Scenario 14.1 and 14.2
| Successful adding        |  Error with adding      |
:-------------------------:|:-------------------------:
| ![](assets/images/Supplier-Add-Success(Scenario14.1).png) | ![](assets/images/Supplier-Add-Error(Scenario14.2).png) |

## Use case 15, UC15 - Modifying Suppliers
![](assets/images/Supplier-Modify(UC15).png)

## Scenario 15.1 and 15.2
| Successful modifying     |  Error with modifying      |
:-------------------------:|:-------------------------:
| ![](assets/images/Supplier-Modify-Success(Scenario15.1).png) | ![](assets/images/Supplier-Modify-Error(Scenario15.2).png) |

## Use case 16, UC16 - Deleting Suppliers
![](assets/images/Supplier-Delete(UC16).png)

## Use case 18, UC18 - Create an Order
## Scenario 18.1
| Orders list    |
:-------------------------:
| ![](assets/images/Orders_before.png)|

| Creating order |
:-------------------------:
| ![](assets/images/Order.png)|

|  Order added |
:-------------------------:
| ![](assets/images/Orders_after.png)|

## Use case 19, UC19 - Modify an Order
## Scenario 19.1
| Orders list    |
:-------------------------:
| ![](assets/images/Orders_after.png)|

| Modifying order |
:-------------------------:
| ![](assets/images/Order_mod.png)|

|  Order modified |
:-------------------------:
| ![](assets/images/Orders_after_mod.png)|

## Use case 20, UC20 - Delete an Order
## Scenario 20.1
| Orders list    |
:-------------------------:
| ![](assets/images/Orders_after.png)|

| Successful deletion |
:-------------------------:
| ![](assets/images/Orders_Del.png)|

|  Order deleted |
:-------------------------:
| ![](assets/images/Orders_before.png)|

## Use case 21, UC21 - Commit an Order
## Scenario 21.1 and 21.2
| Orders list    |
:-------------------------:
| ![](assets/images/Orders_after.png)|

| Successful commit     |  Error with commit      |
:-------------------------:|:-------------------------:
| ![](assets/images/Orders_CommitSucc.png) | ![](assets/images/Orders_CommitErr.png) |