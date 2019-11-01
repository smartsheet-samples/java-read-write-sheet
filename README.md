## java-read-write-sheet

This is a minimal Smartsheet sample that demonstrates how to
* Import a sheet
* Load a sheet
* Loop through the rows
* Check for rows that meet a criteria
* Update cell values
* Write the results back to the original sheet


This sample scans a sheet for rows where the value of the "Status" column is "Complete" and sets the "Remaining" column to zero.
This is implemented in the `evaluateRowAndBuildUpdates()` method which you should modify to meet your needs.


## Setup
* Set the system environment variable `SMARTSHEET_ACCESS_TOKEN` to the value of your access token, obtained from the Smartsheet Account button, under Personal settings
   
* Build the application
    * `mvn compile`
    * You may need to add a reference to the Maven library `com.smartsheet:smartsheet-sdk-java:2.2.5`
    
* Run the application
    * `mvn exec:java`
    * The rows marked "Complete" will have the "Remaining" value set to 0. (Note that you will have to refresh in the desktop application to see the changes)


## Logging
This sample contains a runtime Maven dependency on the Log4j logging framework as an example on how to enable post 
deployment logging for the Smartsheet Java SDK. A log4j.properties file located in /src/main/resources shows the 
details for both a console and file appender. Logging to the console and a log file are enabled by default. 

## See also
- http://smartsheet-platform.github.io/api-docs/
- https://github.com/smartsheet-platform/smartsheet-java-sdk
- https://www.smartsheet.com/

