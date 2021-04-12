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
|	Developer		| who develop and maintain the software application	|
|	Printer			| Used to print receipt / purchase notes	|
|	Bar code reader | Used to read the bar code of each product			|
|	POS				| Used to manage payment with credit card		|


# Context Diagram and interfaces

## Context Diagram

```plantuml
Actor Owner
Actor Employee
Actor Customer
Actor Supplier

Actor Printer

usecase EZShop

Employee -right- EZShop
Owner -up-|> Employee

EZShop -right-> Customer
EZShop -right-> Supplier
EZShop -up-> Printer
```

## Interfaces
| Actor | Logical Interface | Physical Interface  |
| ------------- |-------------| -----|
|   Owner,Employee| GUI | keyboard, mouse and display (PC)|
|   Printer 	| GUI | LAN link|


# Stories and personas
\<A Persona is a realistic impersonation of an actor. Define here a few personas and describe in plain text how a persona interacts with the system>

\<Persona is-an-instance-of actor>

\<stories will be formalized later as scenarios in use cases>


# Functional and non functional requirements

## Functional Requirements

\<In the form DO SOMETHING, or VERB NOUN, describe high level capabilities of the system>

\<they match to high level use cases>

| ID        | Description  |
| ------------- |-------------| 
| FR1     		| Managing employees  |
| FR1.1			| Add a new employee, or modify an existing employee|
| FR1.2     	| Delete an employee |
| FR1.3			| List all employee and Search an employee|
| FR2			| Managing permissions |
| FR3			| Managing sales |
| FR3.1			| Creating shop cart| 
| FR3.1.1  		| Add item	 | 
| FR3.1.2		| Delete item|
| FR3.1.3		| Modify item quantity |
| FR3.1.4		| Print sales ticket |
| FR3.1.5		| Print invoice		|
| FR3.1.6       | Computing VAT|
| FR3.2			| Delete Cart|
| FR3.3			| Choose a Customer	|
| FR4			| Managing customers|
| FR4.1			| Add a new customer, or modify an existing customer|
| FR4.2     	| Delete a customer|
| FR4.3			| List all Customer and Search a Customer |
| FR4.4			| List all Customer's sales|
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


## Non Functional Requirements

\<Describe constraints on functional requirements>

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     |   |  | |
|  NFR2     | |  | |
|  NFR3     | | | |
| NFRx .. | | | | 


# Use case diagram and use cases


## Use case diagram
\<define here UML Use case diagram UCD summarizing all use cases, and their relationships>


\<next describe here each use case in the UCD>
### Use case 1, UC1
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

