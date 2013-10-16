EdisoftXMLProcessor is a Java console application.

DESCRIPTION
-------------------------------------------------------------------------------
The main purpose of this application is to process XML files that represent
invoice documents and populate a database with the related records.

At the start the application first scans the predefined directory for XML files
and transforms them using the also predefined XSLT stylesheet. It then populates
the appropriate database tables with the related data. And after that it starts
monitoring the above mentioned predefined directory with XML files for changes:
creation and updates of the XML documents.
-------------------------------------------------------------------------------

HOW TO USE
-------------------------------------------------------------------------------
In the EdisoftXMLProcessor project folder there is a "dist" directory.
This directory contains the executable JAR file EdisoftXMLProcessor.jar along 
with the required library JAR archives in the "lib" subdirectory and the startup
script "run-app.bat" for the Windows platform.

In order to run the application follow these steps:

	1) Configure the "xml_processor.properties" file.
	This file  can be found inside the executable JAR file. Insert the correct
	paths for the directory that contains XML files for the invoice documents
	and the directory that contains XSLT stylesheet file. See the examples
	inside "xml_processor.properties" configuration file.
	
	2) Configure the "jdbc.properties" file.
	This file is located inside the executable JAR file. Insert the correct
	data for the database URL, username and password. See the example inside
	"jdbc.properties" configuration file.

	3) Use the SQL scripts that can be found inside "db" directory to create
	the appropriate database and tables in the PostgreSQL DBMS.
	File "database.sql" contains SQL script to create database with the
	name "edisoft".
	File "schema.sql" contains SQL script to create tables "header" and
	"details" (table "users" is not used with XMLProcessor, but with the web app).
	In the "resources" folder there are two properties files - "header_sql.properties"
	and "detaisl_sql.properties" that contain SQL queries used in the application.
	You can use another database (i.e. MySQL, Oracle), but check with the
	respective DBMS documentation and make the appropriate changes.
	
	4) Configure the "log4j.properties" file.
	This file is located inside the executable JAR file. Change the configuration
	for "log4j.appender.AppFile.file" by supplying the appropriate path to the
	desired location of the log file.
	Feel free to change the other settings.
	
	5) Use startup script "run-app.bat" to start the application.
	Make sure the database server is running and the appropriate database,
	along with the "header" and "details" tables, is created.
	The application will output messages about the creation and update of
	XML files inside the watched directory*.
	
	To stop the application press Control+C on your keyboard.
	This will trigger the appropriate shutdown hook and the application
	will be closed in a proper way.
	
	
	* NB! This application uses java.nio.file.WatchService interface.
	On Windows platform one UPDATE usually generates two events. According
	to the Java docs such behavior is implementation specific.
	See here for the details:
	http://docs.oracle.com/javase/7/docs/api/java/nio/file/WatchService.html
-------------------------------------------------------------------------------

NOTES
-------------------------------------------------------------------------------
In the "lib" folder inside "dist" folder contains among others "edisoft-common"
JAR library. This library contains classes used in both EdisoftXMLProcessor and
EdisoftWebApp projects. 
-------------------------------------------------------------------------------


Contact info:
Levan Kekelidze
informatik0101@gmail.com