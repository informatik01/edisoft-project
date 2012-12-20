EdisoftWebApp is a Java Web Application.

DESCRIPTION
-------------------------------------------------------------------------------
This web application retrieves the records related to the invoice documents
contained in the appropriate database and presents them to the user.

According to the current requirements it just lists all the records,
allows sorting by column headers, and if the user clicks on some line the
related details will be presented inside a form fields (it is a task requirement).
There is also web site menu which is mostly just stubs to be utilized
if required.

The web application has a login system, although was not required, but was
implemented for the sake of completeness. The web application uses programmatic
security in order to be more portable. Plus it uses salted password hashes and
at the moment it was more intuitive and effective to use programmatic security.
The only container based security measure is configuration in the web.xml
file that controls data confidentiality and integrity by forcing use of HTTPS
protocol with predefined URLs (see <transport-guarantee> in web.xml).

In order to utilize HTTPS protocol you need to generate certificate keystore
using the Java keytool command-line utility and make the appropriate changes
in your Apache Tomcat server.xml file.
See here for the details:
http://tomcat.apache.org/tomcat-7.0-doc/ssl-howto.html
-------------------------------------------------------------------------------

HOW TO USE
-------------------------------------------------------------------------------
Just copy the WAR archive EdisoftWebApp.war contained in the "dist" folder to
the $CATALINA_HOME/webapps directory and start the Apache Tomcat server.
-------------------------------------------------------------------------------

DETAILS
-------------------------------------------------------------------------------
This application is supposed to be deployed to the Apache Tomcat server.
It uses PostgreSQL database server as a Data Layer. In order to successfully use
DataSource, make sure to Copy the Postgres JDBC jar to $CATALINA_HOME/lib.
According to the Tomcat docs "the jars need to be in this directory in order for
DBCP's Classloader to find them".
The PostgreSQL JDBC driver can be found in the WebContent/WEB-INF/lib directory.
See here for more details:
http://tomcat.apache.org/tomcat-7.0-doc/jndi-datasource-examples-howto.html#PostgreSQL

Before running this web application see README.txt file in the EdisoftXMLProcessor
project for the details related to all the preparations required to successfully
populate the database tables with the invoice documents' data.

In the EdisoftXMLProcessor project "db" folder there a "schema" SQL script.
Look for the part that creates "users" table and insert the appropriate data.
You must store not raw passwords, but salted password hashes. There is special
field for the salt used to encode the related password. You can use Secure 
Password Encoder application to encode passwords and get salted password hash and
the salt. Secure Password Encoder can be found in the SecurePasswordEncoder
project. See the "dist" folder inside the SecurePasswordEncoder for the executable
JAR file.

You may want to configure some details of this web application using properties
files found inside WAR archive in WEB-INF/Classes directory. For instance to
configure logging use log4j.properties found there and s.o.

You must supply the correct login and password to access restricted admin area.
When you log in, click in the above menu on "Manage Documents" to see the list
of all currently available invoice data. You can sort it by clicking on the column
headers, see more or less records using pagination menu. You can see the details
related to the concrete invoice by clicking on any of the table's rows.
-------------------------------------------------------------------------------


Contact info:
Levan Kekelidze
informatik0101@gmail.com
