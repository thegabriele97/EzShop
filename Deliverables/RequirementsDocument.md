# Requirements Document 

Authors:

Date:

Version:

# Contents

- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Requirements Document](#requirements-document)
- [Contents](#contents)
- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	- [Context Diagram](#context-diagram)
	- [Interfaces](#interfaces)
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	- [Functional Requirements](#functional-requirements)
	- [Non Functional Requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	- [Use case diagram](#use-case-diagram)
		- [Use case 1, UC1 - Add an Employee](#use-case-1-uc1---add-an-employee)
				- [Scenario 1.1 (NOMINAL scenario)](#scenario-11-nominal-scenario)
				- [Scenario 1.2](#scenario-12)
		- [Use case 2, UC2 - Modify an Employee](#use-case-2-uc2---modify-an-employee)
				- [Scenario 2.1 (NOMINAL scenario)](#scenario-21-nominal-scenario)
				- [Scenario 2.2](#scenario-22)
		- [Use case 3, UC2 - Delete an Employee](#use-case-3-uc2---delete-an-employee)
				- [Scenario 3.1 (NOMINAL scenario)](#scenario-31-nominal-scenario)
		- [Use case 4, UC4 - Add a Customer](#use-case-4-uc4---add-a-customer)
				- [Scenario 4.1 (NOMINAL scenario)](#scenario-41-nominal-scenario)
				- [Scenario 4.2](#scenario-42)
		- [Use case 5, UC5 - Modify a Customer](#use-case-5-uc5---modify-a-customer)
				- [Scenario 5.1 (NOMINAL scenario)](#scenario-51-nominal-scenario)
				- [Scenario 5.2](#scenario-52)
		- [Use case 6, UC6 - Delete a Customer](#use-case-6-uc6---delete-a-customer)
				- [Scenario 3.1 (NOMINAL scenario)](#scenario-31-nominal-scenario-1)
		- [Use case 7, UC7 - Search a Customer](#use-case-7-uc7---search-a-customer)
				- [Scenario 7.1 (NOMINAL scenario)](#scenario-71-nominal-scenario)
- [Glossary](#glossary)
- [System Design](#system-design)
- [Deployment Diagram](#deployment-diagram)

# Essential description

Small shops require a simple application to support the owner or manager. A small shop (ex a food shop) occupies 50-200 square meters, sells 500-2000 different item types, has one or a few cash registers 
EZShop is a software application to:
* manage sales
* manage inventory
* manage customers
* support accounting


# Stakeholders


| Stakeholder name  | Description | 
| ----------------- |-----------|
|   Owner			| buys (and uses, and administrates) the software and he is the owner of the shop, can add or remove credentials of an employee|
|	Employee		| Uses the software for the payment of his own sale, to modify inventary   | 
|   Customer		| Registered in shop's internal DB after the first purchase |
|	Supplier		| Who supplies the items to sell			|
|	Developer		| Who develops and maintains the software application	|
|	Printer			| Used to print receipt / purchase notes	|
|	Bar code reader | Used to read the bar code of each product			|
|	POS				| Used to manage payment with credit card		|
|	Email Gateway				| Used to send the order to the supplier		|


# Context Diagram and interfaces

## Context Diagram

```plantuml
Actor Owner
Actor Employee

Actor Printer
Actor POS 
Actor BarcodeReader
Actor EmailGateway

usecase EZShop

Employee -- EZShop
Owner -left-|> Employee

EZShop --> Printer
EZShop --> EmailGateway

EZShop -- POS
EZShop -- BarcodeReader
```

## Interfaces
| Actor | Logical Interface | Physical Interface  |
| ------------- |-------------| -----|
|   Owner,Employee 	| GUI 	| keyboard, mouse and display (PC)|
|   Printer 		| GUI 	| LAN link |
|   Barcode Reader 	|  		| USB |
|   POS 			| MyPOS API https://developers.mypos.eu/en/doc/more_apis/v1_0/16-payment-initiation-service-(pis)	| Ethernet  |
|   Email Gateway 	| GUI 	| Internet  |


# Stories and personas

- Pietro is 45, is the owner of a small furniture shop and also works in it. He dreams to expand his business in the future. For this reason, he buyed the software for managing employees, sales and orders to the best.

- Silvio is 18, and he's a student at the high school. Usually he spends the summer holidays with the friends going to the beach. But he has decided to work in order to have a minimum of economical independence. He has founda three months job in a small furniture shop, thanks to his ability with PC and his willingness to carry out different tasks.

- Alessandra is 32, she is a Designer, working in Pietro's shop. She has many years of experience in furniture sector. She also has great interpersonal and communication skills, indeed, when the shop's owner is busy or absent, she is a reference for customers and suppliers. 

# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |-------------| 
| FR1     		| Managing employees  |
| FR1.1			| Add a new employee |
| FR1.2			| Modify an existing employee |
| FR1.3     	| Delete an employee |
| FR1.4			| List all employee and Search an employee|
| FR1.5			| Send an e-mail |
| FR2			| Managing permissions |
| FR3			| Managing sales |
| FR3.1			| Creating shop cart | 
| FR3.1.1  		| Add item	 | 
| FR3.1.2		| Delete item |
| FR3.1.3		| Modify item quantity |
| FR3.1.4		| Choose a Customer	|
| FR3.2			| Committing a shop cart |
| FR3.2.1       | Computing VAT |
| FR3.2.2		| Print invoice		|
| FR3.2.3		| Print sales ticket |
| FR3.2.4		| Pay the total |
| FR3.3			| Delete Cart |
| FR4			| Managing customers |
| FR4.1			| Add a new customer |
| FR4.2     	| Delete a customer|
| FR4.3			| List all Customer and Search a Customer |
| FR4.4			| List all Customer's sales|
| FR4.5			| Send an e-mail |
| FR4.6 		| Modify an existing customer |
| FR5			| Managing inventory|
| FR5.1			| Add a new item |
| FR5.2     	| Delete an item|
| FR5.3			| List all itmes and quantity/prices|
| FR5.4			| Search an item|
| FR5.6 		| Modify quantity/price of an existing item |
| FR6			| Managing Supplier	|
| FR6.1			| Add a new supplier |
| FR6.2     	| Delete a supplier|
| FR6.3			| List all suppliers|
| FR6.4			| Search a supplier|
| FR6.5			| Modify an existing one |
| FR7			| Managing Order|
| FR7.1			| Add supplier order|
| FR7.2			| Modify supplier order	|
| FR7.3			| Send order by email |
| FR8			| Manage accounting |
| FR8.1			| Computing net profit |


## Non Functional Requirements

| ID        | Type  | Description  | Refers to |
| --------- | ----- | ------------ | --------- |
|  NFR1     | Usability  | Software should be usable even for new emoplyees after at most 1 hour of training |All FR|
|  NFR2     | Performance | All operations should be comlpeted in 1s at most  | All FR |
|  NFR3     | Portability| Software should be runnable on the main OS (Windows and macOS) |All FR|
|  NFR4	    | Reliability| Computations and operations executed by the software should be correct at least in the 99% of cases| All FR, notably FR3, FR5 |
|  NFR5     | Delivery   | Software should be delivered before 2021_06_12| All FR|
|  NFR6     | Privacy    | The data of users (employee and owner), customers and suppliers should not be disclosed out of working context| All FR| 


# Use case diagram and use cases


## Use case diagram
```plantuml
Actor Owner
Actor Employee

Actor Printer
Actor POS 
Actor BarcodeReader
Actor EmailGateway

usecase "FR1 Managing Employees" as mngemploye
usecase "FR3 Managing Sales" as mngsales
usecase "FR4 Managing Customers" as mngCustomers
usecase "FR5 Managing inventory" as mnginventory
usecase "FR6 Managing Supplier" as mngsupplier
usecase "FR7 Managing Order" as mngorder
usecase "FR8 Manage Accounting" as mngaccounting

usecase "FR3.2.2 Print invoice" as printinvoce
usecase "FR3.2.3 Print sales ticket" as printicket
usecase "FR3.2.4 Pay the total" as paytotal

Owner -down-|> Employee

Owner -right- mngemploye
Owner -left- mngaccounting
Employee -- mngsales

mngsales --> printicket : <<include>>
mngsales --> printinvoce : <<include>>
mngsales --> paytotal : <<include>>
mngsales -right- BarcodeReader

paytotal -- POS

printicket --> Printer
printinvoce --> Printer

Employee -right- mngCustomers
Employee -- mngsupplier

Employee -- mnginventory
mnginventory -- BarcodeReader

Employee -left- mngorder
mngorder -left-> EmailGateway
```

```plantuml
usecase "FR1 Managing Employees" as memployee
usecase "FR1.1 Add an Employee" as addemployee
usecase "FR1.2 Modify an Employee" as modifyemployee
usecase "FR1.3 Delete an Employee" as rememployee

memployee --> addemployee : <<include>>
memployee --> modifyemployee : <<include>>
memployee --> rememployee : <<include>>


usecase "FR3 Managing Sales" as mngsales
usecase "FR3.1 Creating a shop cart" as addshopcart
usecase "FR3.2 Committing a shop cart" as confirmcart
usecase "FR3.2.2 Print Invoice" as printinvoice
usecase "FR3.2.3 Print sales ticket" as printicket
usecase "FR3.2.4 Pay the total" as paytotal

mngsales --> addshopcart : <<include>>
mngsales --> confirmcart : <<include>>

confirmcart --> printinvoice : <<include>>
confirmcart --> printicket : <<include>>
confirmcart --> paytotal : <<include>>


usecase "FR4.1 Add an Employee" as addemployee
usecase "FR4.2 Modify an Employee" as modifyemployee
usecase "FR4.3 Delete an Employee" as rememployee

```

### Use case 1, UC1 - Add an Employee
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     | Employee E doesn't exists |  
|  Post condition     | Employee E exists and registered in the system |
|  Nominal Scenario     | The owner creates a new Employee with all the required details |
|  Variants     | The owner may try to insert a duplicate Employee (identified by a duplicated email nor duplicted Fiscal Code) and will result in an error |

##### Scenario 1.1 (NOMINAL scenario)

| Scenario 1.1 | Employee E doesn't exists (i.e. email and F.C. not registered into the system) |
| ------------- |:-------------:| 
|  Precondition     | Owner wants to register a new Employee |
|  Post condition     | Employee E registered |
| Step#        | Description  |
|  1     | Owner selects "Register new Employee" |  
|  2     | Owner inserts all the required informations about the Employee |
|  3     | Owner submit the form |
|  4	 | The system validates them and see that it's not a duplicated employee |
|  5	 | The system registers the employee |

##### Scenario 1.2

| Scenario 1.2 | Employee E exists (i.e. email or F.C. registered into the system) |
| ------------- |:-------------:| 
|  Precondition     | Owner wants to register a new Employee |
|  Post condition     | System unchanged |
| Step#        | Description  |
|  1     | Owner selects "Register new Employee" |  
|  2     | Owner inserts all the required informations about the Employee |
|  3     | Owner submit the form |
|  4	 | The system validates them and see that there is something wrong |
|  5	 | The system notice the owner and doesn't commit the submission |

### Use case 2, UC2 - Modify an Employee
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     | Employee E exists |  
|  Post condition     | Employee E exists and updated |
|  Nominal Scenario     | The owner modify one or more fields of the Employee |
|  Variants     | The owner may try to insert a duplicate data (i.e. email already registered into the system) |

##### Scenario 2.1 (NOMINAL scenario)

| Scenario 2.1 | Updating Employee data with not duplicated data |
| ------------- |:-------------:| 
|  Precondition     | Owner wants to modify an Employee |
|  Post condition     | Employee E updated |
| Step#        | Description  |
|  1     | Owner selects "Modify this Employee" |  
|  2     | Owner updated the informations about the Employee |
|  3     | Owner submit the form |
|  4	 | The system validates them and see that everything is ok |
|  5	 | The system updates the employee |

##### Scenario 2.2

| Scenario 2.2 | Updating Employee data with duplicated data |
| ------------- |:-------------:| 
|  Precondition     | Owner wants to modify an Employee |
|  Post condition     | Employee E not updated |
| Step#        | Description  |
|  1     | Owner selects "Modify this Employee" |  
|  2     | Owner updated the informations about the Employee |
|  3     | Owner submit the form |
|  4	 | The system validates them and see that something is wrong (i.e. inserted a duplicated email) |
|  5	 | The system notice the owner and doesn't commit the submission |

### Use case 3, UC3 - Delete an Employee
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     | Employee E exists |  
|  Post condition     | Employee E removed |
|  Nominal Scenario     | The owner wants to delete an employee from the system |
|  Variants     |  |

##### Scenario 3.1 (NOMINAL scenario)

| Scenario 3.1 | Deleteing an Employee |
| ------------- |:-------------:| 
|  Precondition     | Owner wants to delete an Employee |
|  Post condition     | Employee E deleted |
| Step#        | Description  |
|  1     | Owner selects "Delete this Employee" |
|  2	 | The system deletes the employee |

### Use case 4, UC4 - Add a Customer
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     | Customer C doesn't exists |  
|  Post condition     | Customer C exists and registered in the system |
|  Nominal Scenario     | The employee creates a new Customer with all the required details |
|  Variants     | The employee may try to insert a duplicate Customer (identified by a duplicated email nor duplicted Fiscal Code) and will result in an error |

##### Scenario 4.1 (NOMINAL scenario)

| Scenario 4.1 | Customer C doesn't exists (i.e. email and F.C. not registered into the system) |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to register a new Customer |
|  Post condition     | Customer C registered |
| Step#        | Description  |
|  1     | Employee selects "Register new customer" |  
|  2     | Employee inserts all the required informations about the customer |
|  3     | Employee submit the form |
|  4	 | The system validates them and see that it's not a duplicated customer |
|  5	 | The system registers the customer |

##### Scenario 4.2

| Scenario 4.2 | Customer C exists (i.e. email or F.C. registered into the system) |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to register a new customer |
|  Post condition     | System unchanged |
| Step#        | Description  |
|  1     | Employee selects "Register new customer" |  
|  2     | Employee inserts all the required informations about the customer |
|  3     | Employee submit the form |
|  4	 | The system validates them and see that there is something wrong |
|  5	 | The system notice the employee and doesn't commit the submission |


### Use case 5, UC5 - Modify a Customer
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     | Customer C exists |  
|  Post condition     | Customer C exists and updated |
|  Nominal Scenario     | The employee modify one or more fields of the Customer |
|  Variants     | The employee may try to insert a duplicate data (i.e. email already registered into the system) |

##### Scenario 5.1 (NOMINAL scenario)

| Scenario 5.1 | Updating Customer data with not duplicated data |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to modify a Employee |
|  Post condition     | Customer C updated |
| Step#        | Description  |
|  1     | Employee selects "Modify this Customer" |  
|  2     | Employee update the informations about the customer |
|  3     | Employee submit the form |
|  4	 | The system validates them and see that everything is ok |
|  5	 | The system updates the customer |

##### Scenario 5.2

| Scenario 5.2 | Updating customer data with duplicated data |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to modify a customer |
|  Post condition     | Customer E not updated |
| Step#        | Description  |
|  1     | Employee selects "Modify this customer" |  
|  2     | Employee updated the informations about the customer |
|  3     | Employee submit the form |
|  4	 | The system validates them and see that something is wrong (i.e. inserted a duplicated email) |
|  5	 | The system notice the employee and doesn't commit the submission |

### Use case 6, UC6 - Delete a Customer
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     | Customer C exists |  
|  Post condition     | Customer C removed |
|  Nominal Scenario     | The employee wants to delete a customer from the system |
|  Variants     |  |

##### Scenario 6.1 (NOMINAL scenario)

| Scenario 6.1 | Deleting a Customer |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to delete a Customer |
|  Post condition     | Customer C deleted |
| Step#        | Description  |
|  1     | Employee selects "Delete this Customer" |
|  2	 | The system deletes the customer |

### Use case 7, UC7 - Search a Customer
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     |  |  
|  Post condition     | The list of customer corresponding to the search is visualized |
|  Nominal Scenario     | The employee wants to search a specific customer on the system |
|  Variants     |  |

##### Scenario 7.1 (NOMINAL scenario)

| Scenario 7.1 | Searching a Customer |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to search a Customer |
|  Post condition     | Customer C visualized |
| Step#        | Description  |
|  1     | The employee selects "Search Customer" |
|  2	 | The employee puts the customer name and surname |
|  3     | The system show the customer or customers corresponding to the search

### Use case 8, UC8 - Managing Sales (Creating a shopping cart)
| Actors Involved        | Employee / Product / Barcode Reader  |
| ------------- |:-------------:| 
|  Precondition     | There are no pending shopping carts |  
|  Post condition     | A shopping cart is created |
|  Nominal Scenario     | A shopping cart is created and its details are defined |
|  Variants     |  |

##### Scenario 8.1 (NOMINAL scenario)

| Scenario 8.1 | Creating a shopping cart |
| ------------- |:-------------:| 
|  Precondition     | Employee selects to create a shopping cart |
|  Post condition     | A shopping cart is created and its details are defined |
|  Step#        | Description  |
|  1     | Owner selects "Create a new shopping cart" |
|  2	 | The employee picks items from invetory / uses barcode |
|  3	 | ... |
|  4	 | The employee change the quantity, deletes item from cart |
|  5	 | Can link the cart to a customer |

##### Scenario 8.2

| Scenario 8.2 | Creating a shopping cart and inserts a quantity of an item not available in inventory (either using the barcode scanner) |
| ------------- |:-------------:| 
|  Precondition     | Employee selects to create a shopping cart and starts inserting items |
|  Post condition     | The quantity that exceeds is not registered in the cart |
|  Step#        | Description  |
|  1     | Owner selects "Create a new shopping cart" |
|  2	 | The employee picks items from invetory / uses barcode |
|  3	 | ... |
|  4	 | The employee change the quantity (or uses the barcode scanner to increase the quantity of an already inserted item) |
|  5	 | The system notice that the required quantity is not satisfable |
|  6	 | The system notice the employee that the quantity is not satisfable |

### Use case 9, UC9 - Managing Sales (Committing shopping cart)
| Actors Involved        | Employee / Printer / POS / Product |
| ------------- |:-------------:| 
|  Precondition     | An existing shopping cart is going to be committed |  
|  Post condition     | - A shopping cart is payed and committed - The inventory is updated |
|  Nominal Scenario     | A shopping cart is committed and the customer pays correctly |
|  Variants     | The customer decides to not pay anymore and leave the shop - There is not available paper / ink in the printer / The POS can't commit the transaction |

##### Scenario 9.1 (NOMINAL scenario)

| Scenario 9.1 | Committing a shopping cart |
| ------------- |:-------------:| 
|  Precondition     | An existing shopping cart is going to be committed, the user has enough money to pay (either in cash or POS), the printer is ok and the POS System is OK |
|  Post condition     | A shopping cart is committed and payed / the receipt is printed |
|  Step#        | Description  |
|  1     | Employee selects to checkout the cart |
|  2	 | Employee selects the payment method (cash or POS) |
|  3	 | The payment is executed |
|  4	 | The receipt is printed |
|  5 	 | (Optional) The invoice is printed |

##### Scenario 9.2

| Scenario 9.2 | Committing a shopping cart but no enough money |
| ------------- |:-------------:| 
|  Precondition     | An existing shopping cart is going to be committed, but the user hasn't enough money to pay (either in cash or POS) |
|  Post condition     | A shopping cart is in frozen state |
|  Step#        | Description  |
|  1     | Employee selects to checkout the cart |
|  2	 | Employee selects the payment method (cash or POS) |
|  3	 | The payment is not executed |
|  4	 | The operation stops and the cart is frozen (can be deleted) |

##### Scenario 9.3

| Scenario 9.3 | Committing a shopping cart but there are problems with the printer |
| ------------- |:-------------:| 
|  Precondition     | An existing shopping cart is going to be committed, the user has enough money to pay (either in cash or POS), the printer has problems |
|  Post condition     | A shopping cart is committed and payed / the receipt is printed |
|  Step#        | Description  |
|  1     | Employee selects to checkout the cart |
|  2	 | Employee selects the payment method (cash or POS) |
|  3	 | The payment is executed |
|  4	 | The system notices that the receipt can't be printed because of problems with the printer |
|  5	 | The system waits and the Employee retries after fixing the printer |
|  6 	 | (Optional) The invoice is printed [if problems, again from step #4] |

##### Scenario 9.4

| Scenario 9.4 | Committing a shopping cart but there are problems with the POS |
| ------------- |:-------------:| 
|  Precondition     | An existing shopping cart is going to be committed, the user has enough money to pay (either in cash or POS), the printer is ok, the POS returns an error |
|  Post condition     | A shopping cart is frozen |
|  Step#        | Description  |
|  1     | Employee selects to checkout the cart |
|  2	 | Employee selects the payment method (POS) |
|  3	 | The payment is blocked (problems with the POS, can be refused cart or internet problems) |
|  4	 | The system notify the Employee |
|  5	 | The employee decides to retries, select another payment method |

### Use case 10, UC10 - Managing Inventory (Register new item)
| Actors Involved        | Employee / Product / Barcode Reader |
| ------------- |:-------------:| 
|  Precondition     | Item I doesn't exists |  
|  Post condition     | Item I registered in the system |
|  Nominal Scenario     | Employee Item I register a new item with all the required details (like price) |
|  Variants     | The employee may try to insert a duplicate Item (identified by a duplicated barcode) and will result in an error |

##### Scenario 10.1 (NOMINAL scenario)

| Scenario 10.1 | Item C doesn't exists (i.e. barcode not registered into the system) |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to register a new Item |
|  Post condition     | Item I registered |
| Step#        | Description  |
|  1     | Employee selects "Register new Item" |  
|  2 	 | Employee reads the code with the barcode reader (or insert it manually) |
|  3     | Employee inserts all the required informations about the Item |
|  4     | Employee submit the form |
|  5	 | The system validates them and see that it's not a duplicated item |
|  6	 | The system registers the item |

##### Scenario 10.2

| Scenario 10.2 | Item I exists (i.e. barcode registered into the system) |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to register a new Item |
|  Post condition     | System unchanged |
| Step#        | Description  |
|  1     | Employee selects "Register new Item" |  
|  2 	 | Employee reads the code with the barcode reader (or insert it manually) |
|  3	 | The system notices that the code is already registered |
|  4	 | The system notice the employee and doesn't change the inventory DB |


### Use case 11, UC11 - Managing Inventory (Modify quantity/price of an existing item)
| Actors Involved        | Employee / Product |
| ------------- |:-------------:| 
|  Precondition     | Item C exists |  
|  Post condition     | Item C exists and updated |
|  Nominal Scenario     | The employee modify one or more fields of the Item |
|  Variants     | |

##### Scenario 11.1 (NOMINAL scenario)

| Scenario 11.1 | Updating Item informations |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to modify an Item |
|  Post condition     | Item I updated |
| Step#        | Description  |
|  1     | Employee selects "Modify this Item" |  
|  2     | Employee update the informations about the Item |
|  3     | Employee submit the form |
|  4	 | The system validates them and see that everything is ok |
|  5	 | The system updates the Item |

### Use case 12, UC12 - Managing Inventory (Delete an item)
| Actors Involved        | Employee / Product |
| ------------- |:-------------:| 
|  Precondition     | Item I exists |  
|  Post condition     | Item I removed |
|  Nominal Scenario     | The employee wants to delete an Item from the system |
|  Variants     |  |

##### Scenario 12.1 (NOMINAL scenario)

| Scenario 12.1 | Deleting an Item |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to delete an Item |
|  Post condition     | Item I deleted |
| Step#        | Description  |
|  1     | Employee selects "Delete this Item" |
|  2	 | The system deletes the Item |

### Use case 13, UC13 - Managing Inventory (Search an item)
| Actors Involved        | Employee / Product  |
| ------------- |:-------------:| 
|  Precondition     |  |  
|  Post condition     | The list of Items corresponding to the search is visualized |
|  Nominal Scenario     | The employee wants to search a specific Item on the system |
|  Variants     |  |

##### Scenario 13.1 (NOMINAL scenario)

| Scenario 13.1 | Searching an Item |
| ------------- |:-------------:| 
|  Precondition     | Employee wants to search an Item |
|  Post condition     | Item(s) I visualized |
| Step#        | Description  |
|  1     | The employee selects "Search Items" |
|  2	 | The employee puts the Item infos (i.e. barcode or name) |
|  3     | The system show the Item(s) if any relative to the search |

# Glossary

\<use UML class diagram to define important terms, or concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>

\<must be consistent with Context diagram>

# Deployment Diagram 

\<describe here deployment diagram >
