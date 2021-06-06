# Project Dashboard

The dashboard collects the key measures about the project.
To be used to control the project, and compare with others. These measures will not be used to grade the project. <br>
We consider two phases: <br>
-New development: From project start (april 5) to delivery of version 0 (release 0, may 28) <br>
-Corrective Maintenance: fix of defects (if any)  (may 28 to june 4)   <br>
Report effort figures from the timesheet or timesheetCR document, compute size from the source code.

## New development (release 0  -- april 5 to may 28)
| Measure| Value |
|---|---|
|effort E (report here effort in person hours, for New development, from timesheet)  | 234 |
|size S (report here size in LOC of all code written, excluding test cases)  | 600 |
|productivity = S/E | 2.6 |
|defects before release D_before (number of defects found and fixed before may 28) | 40 |




## Corrective Maintenance (release 1 -- may 28 to june 4)

| Measure | Value|
|---|---|
| effort for non-quality ENQ (effort for release 1, or effort to fix defects found when running official acceptance tests) | 0.5 |
| effort for non quality, relative = ENQ / E | 0.002 |
| defects after release D (number of defects found running official acceptance tests and  fixed in release 1) | 2 |
| defects before release vs defects after release = D/D_before | 0.05 |
|defect density = D/S | 0.0033 |
|overall productivity = S/(E + ENQ) | 2.6 |
