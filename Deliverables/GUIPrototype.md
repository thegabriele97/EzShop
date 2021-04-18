# Graphical User Interface Prototype  

Authors:

Date:

Version:

\<Report here the GUI that you propose. You are free to organize it as you prefer. A suggested presentation matches the Use cases and scenarios defined in the Requirement document. The GUI can be shown as a sequence of graphical files (jpg, png)  >

## Logged User
| Employee View             |  Owner View |
:-------------------------:|:-------------------------:
| ![Logged User (Employee)](assets/images/AdminPage.png) | ![Logged User (Employee)](assets/images/UserPage.png) |

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
![](assets/images/10_Inventory_view.png)

## Scenario 10.1 and 10.2
![](assets/images/10.1_Add.png)

| Add successfully      |  Add with error |
:-------------------------:|:-------------------------:
| ![](assets/images/10.1_Add_ok.png) | ![](assets/images/10.2_Add_err.png) |

![](assets/images/10.4_inv_view_after_add.png)

When.......


## Use case 11, UC11 - Managing Inventory (Modify quantity/price of an existing item)
## Scenario 11.1
| Modify view     |  View after modify |
:-------------------------:|:-------------------------:
| ![](assets/images/11_Modify.png)| ![](assets/images/11.1_After_modify.png) |

## Use case 12, UC12 - Managing Inventory (Delete an item)
## Scenario 12.1
| Delete view     |  View after Delete |
:-------------------------:|:-------------------------:
| ![](assets/images/12_Delete.png)| ![](assets/images/12.1_After_delete.png)|


## Use case 13, UC13 - Managing Inventory (Search an item)
## Scenario 13.1
| Search view     |  View after Search |
:-------------------------:|:-------------------------:
| ![](assets/images/13_Search.png)| ![](assets/images/13.1_After_Search.png)|
