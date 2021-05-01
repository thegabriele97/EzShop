# Project Estimation  
Authors:
Date:
Version:
# Contents
- [Estimate by product decomposition]
- [Estimate by activity decomposition ]
# Estimation approach
<Consider the EZShop project as described in YOUR requirement document, assume that you are going to develop the project INDEPENDENT of the deadlines of the course>
# Estimate by product decomposition
### 
|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed   | 15 |             
|  A = Estimated average size per class, in LOC       | 1.000 | 
| S = Estimated size of project, in LOC (= NC * A)  | 15.000 |
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  | 1.500 |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 45.000 | 
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) |   10 calendar weeks                |               
# Estimate by activity decomposition
### 
|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- | 
| A1 - Project Estimation            | 40 |
| A2 - Project Requirements - Project's Context | 40 |
| A3 - Project Requirements - FR & NFR  | 100 |
| A4 - Project Requirements - Glossary & Deploy | 40 |
| A5 - GUI Prototype | 50 |
| A6 - Design - Architectural Decisions | 6 |
| A7 - Design - High Level Design | 40 |
| A8 - Design - Low Level Design | 20 |
| A9 - Design - Verification | 8 |
| A10 - Coding - GUI Coding Prototype | 120 |
| A11 - Coding - Base Functionality coding| 300 |
| A12 - Coding - Complete Functionality coding| 410 |
| A13 - Unit Testing | 120 |
| A14 - Integration Testing - | 100 |
| A15 - Acceptance Testing - | 100 |

###

```plantuml

Project starts 2021-04-26

[A1] starts 2021-04-26
[A1] ends 2021-04-27

[A2] starts 2021-04-27
[A2] ends 2021-04-28

[A3] starts 2021-04-28
[A3] ends 2021-04-30

[A4] starts 2021-05-3
[A4] ends 2021-05-4

[A5] starts 2021-05-4
[A5] ends 2021-05-5

[A6] starts 2021-05-4
[A6] ends 2021-05-5

[A7] starts 2021-05-6
[A7] ends 2021-05-7

[A8] starts 2021-05-10
[A8] ends 2021-05-11

[A9] starts 2021-05-11
[A9] ends 2021-05-12

[A10] starts 2021-05-13
[A10] ends 2021-05-18

[A11] starts 2021-05-19
[A11] ends 2021-05-28

[A12] starts 2021-05-31
[A12] ends 2021-06-10

[A13] starts 2021-06-11
[A13] ends 2021-06-18

[A14] starts 2021-06-21
[A14] ends 2021-06-25

[A15] starts 2021-06-29
[A15] ends 2021-07-02

```

