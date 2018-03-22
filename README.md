## java-read-write-sheet

This is a minimal Smartsheet sample that demonstrates how to
* Load a sheet
* Loop through the rows
* Check for rows that meet a criteria
* Update cell values
* Write the results back to the original sheet


This sample scans a sheet for rows where the value of the "Status" column is "Complete" and sets the "Remaining" column to zero.
This is implemented in the `evaluateRowAndBuildUpdates()` method which you should modify to meet your needs.


## Setup
* Import the sample data from "Sample Sheet.xlsx" into a new sheet
* Update the rwsheet.properties file (located in src/main/resources) with these two settings:
    * An API access token, obtained from the Smartsheet Account button, under Personal settings. 
    
        Alternatively, you can specify the access token with a system environment variable named `SMARTSHEET_ACCESS_TOKEN`.
    * The Sheet Id, obtained from sheet properties 

* Build the application
    * `mvn compile`
    * You may need to add a reference to the Maven library `com.smartsheet:smartsheet-sdk-java:2.2.3`
    
* Run the application
    * `mvn exec:java`
    * The rows marked "Complete" will have the "Remaining" value set to 0. (Note that you will have to refresh in the desktop application to see the changes)


##Logging
This sample contains a runtime Maven dependency on the Log4j logging framework as an example on how to enable post 
deployment logging for the Smartsheet Java SDK. A log4j.properties file located in /src/main/resources shows the 
details for both a console and file appender. 

## See also
- http://smartsheet-platform.github.io/api-docs/
- https://github.com/smartsheet-platform/smartsheet-java-sdk
- https://www.smartsheet.com/

