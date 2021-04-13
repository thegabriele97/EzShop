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
| FR1.1			| Add a new employee, or modify an existing employee|
| FR1.2     	| Delete an employee |
| FR1.3			| List all employee and Search an employee|
| FR1.4			| Send an e-mail |
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


\<next describe here each use case in the UCD>
### Use case 1, UC1 - Add Employee
| Actors Involved        |  |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the UC can start> |  
|  Post condition     | \<Boolean expression, must evaluate to true after UC is finished> |
|  Nominal Scenario     | \<Textual description of actions executed by the UC> |
|  Variants     | \<other executions, ex in case of errors> |

##### Scenario 1.1 

\<describe here scenarios instances of UC1>

\<a scenario is a sequence of steps that corresponds to a particular execution of one use case>

\<a scenario is a more formal description of a story>

\<only relevant scenarios should be described>

| Scenario 1.1 | |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the scenario can start> |
|  Post condition     | \<Boolean expression, must evaluate to true after scenario is finished> |
| Step#        | Description  |
|  1     |  |  
|  2     |  |
|  ...     |  |

##### Scenario 1.2

##### Scenario 1.x

### Use case 2, UC2
..

### Use case x, UCx
..



# Glossary

\<use UML class diagram to define important terms, or concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>

\<must be consistent with Context diagram>

# Deployment Diagram 

\<describe here deployment diagram >
