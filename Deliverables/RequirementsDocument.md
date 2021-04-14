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
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
    	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)
- [Deployment diagram](#deployment-diagram)

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
| FR4.1			| Add a new customer, or modify an existing customer|
| FR4.2     	| Delete a customer|
| FR4.3			| List all Customer and Search a Customer |
| FR4.4			| List all Customer's sales|
| FR4.5			| Send an e-mail |
| FR5			| Managing inventory|
| FR5.1			| Add a new item, or modify quantity/price of an existing item|
| FR5.2     	| Delete an item|
| FR5.3			| List all itmes and quantity/prices|
| FR5.4			| Search an item|
| FR5.5			| Computing net profit|
| FR6			| Managing Supplier	|
| FR6.1			| Add a new supplier, or modify an existing one|
| FR6.2     	| Delete a supplier|
| FR6.3			| List all suppliers|
| FR6.4			| Search a supplier|
| FR7			| Managing Order|
| FR7.1			| Add supplier order|
| FR7.2			| Modify supplier order	|
| FR7.3			| Send order by email |
| FR8			| Manage accounting |


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

\<next describe here each use case in the UCD>
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

| Scenario 1.1 | Updating Employee data with not duplicated data |
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

| Scenario 1.2 | Updating Employee data with duplicated data |
| ------------- |:-------------:| 
|  Precondition     | Owner wants to modify an Employee |
|  Post condition     | Employee E not updated |
| Step#        | Description  |
|  1     | Owner selects "Modify this Employee" |  
|  2     | Owner updated the informations about the Employee |
|  3     | Owner submit the form |
|  4	 | The system validates them and see that something is wrong (i.e. inserted a duplicated email) |
|  5	 | The system notice the owner and doesn't commit the submission |

### Use case 3, UC2 - Delete an Employee
| Actors Involved        | Owner / Employee  |
| ------------- |:-------------:| 
|  Precondition     | Employee E exists |  
|  Post condition     | Employee E removed |
|  Nominal Scenario     | The owner wants to delete an eployee from the system |
|  Variants     |  |

##### Scenario 3.1 (NOMINAL scenario)

| Scenario 1.1 | Deleteing an Employee |
| ------------- |:-------------:| 
|  Precondition     | Owner wants to delete an Employee |
|  Post condition     | Employee E deleted |
| Step#        | Description  |
|  1     | Owner selects "Delete this Employee" |
|  2	 | The system deletes the employee |


# Glossary

\<use UML class diagram to define important terms, or concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>
```plantuml
class DesktopPC
DesktopPC o-- CashRegister
```
\<must be consistent with Context diagram>

# Deployment Diagram 
The software runs on one or more PC

```plantuml
node PC
artifact EZShopSoftware
EZShopSoftware -- "1..*" PC
```
\<describe here deployment diagram >
