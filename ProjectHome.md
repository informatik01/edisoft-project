## Java EE and Java SE projects for [Edisoft Estonia OÜ](http://www.edisoft.ee/eng/). ##

There are three Java projects in the repository:
  * **EdisoftXMLProcessor** (Java SE desktop console application)
  * **EdisoftWebApp** (Java EE web application)
  * **SecurePasswordEncoder** (Java SE desktop GUI application)

Each project contains README.txt file with the appropriate descriptions and details.<br>
The original description of the test exercise can be found in the <a href='http://code.google.com/p/edisoft-project/downloads/list'>Downloads</a> section.<br>
<hr />
<h3>EdisoftXMLProcessor project description</h3>
The main purpose of this application is to process XML files that represent<br>
invoice documents and populate a database with the related records.<br>
<br>
At the start the application first scans the predefined directory for XML files<br>
and transforms them using the also predefined XSLT stylesheet. It then populates<br>
the appropriate database tables with the related data. And after that, it starts<br>
monitoring the above mentioned predefined directory, containing XML files, for changes:<br>
creation and updates of the XML documents.<br>
<br>
<p align='center'><code>**********</code>
<h3>EdisoftWebApp project description</h3>
This web application retrieves the records related to the invoice documents<br>
contained in the appropriate database and presents them to the user.<br>
<br>
According to the current requirements it just lists all the records,<br>
allows sorting by column headers, and if the user clicks on some line the<br>
related details will be presented inside a form fields (it is a task requirement).<br>
There is also web site menu which is mostly just stubs to be utilized<br>
if required.<br>
<br>
The web application has a login system, although was not required, but was<br>
implemented for the sake of completeness. The web application uses programmatic<br>
security in order to be more portable. Plus it uses salted password hashes and<br>
at the moment it was more intuitive and effective to use programmatic security.<br>
The only container based security measure is configuration in the web.xml<br>
file that controls data confidentiality and integrity by forcing use of HTTPS<br>
protocol with predefined URLs (see <code>&lt;transport-guarantee&gt;</code> in web.xml).<br>
<br>
<p align='center'><code>**********</code>
<h3>SecurePasswordEncoder project description</h3>
This is a desktop GUI application.<br>
<br>
It takes some password provided by a user,<br>
generates a cryptographically strong random number for the salt,<br>
and hashes the salted password using Secure Hash Algorithm (SHA).<br>
<br>
Salted password hash along with the salt can be stored in a<br>
database and used, for example, as part of a web application<br>
login system.<br>
<br>
<hr />
<h3>Languages, technologies, frameworks and other solutions used:</h3>
<a href='http://docs.oracle.com/javase/'>Java SE 7</a>, <a href='http://docs.oracle.com/javaee/'>Java EE 6</a> (<a href='http://jcp.org/en/jsr/detail?id=315'>Servlet 3.0</a>, <a href='http://jcp.org/en/jsr/detail?id=245'>JSP 2.2</a>, <a href='http://jcp.org/en/jsr/detail?id=52'>JSTL 1.2</a>, <a href='http://download.oracle.com/otndocs/jcp/expression_language-2.2-mrel-eval-oth-JSpec/'>Expression Language 2.2</a>), <a href='http://www.iso.org/iso/home/store/catalogue_tc/catalogue_detail.htm?csnumber=53681'>SQL</a>, <a href='http://jcp.org/aboutJava/communityprocess/mrel/jsr221/index.html'>JDBC 4.1</a>, <a href='http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html'>Tomcat JDBC Connection Pool</a>, <a href='http://www.jdom.org/'>JDOM 2.0.4</a>, <a href='http://jcp.org/en/jsr/detail?id=222'>JAXB 2.2</a>, <a href='http://www.eclipse.org/eclipselink/moxy.php'>EclipseLink MOXy 2.4.0</a>, <a href='http://logging.apache.org/log4j/1.2/'>Apache log4j 1.2</a>, <a href='http://ant.apache.org/'>Apache Ant 1.8.4</a>, <a href='http://www.w3.org/TR/xml/'>XML 1.0</a>, <a href='http://www.whatwg.org/specs/web-apps/current-work/multipage/'>HTML 5</a>, <a href='http://www.w3.org/TR/2011/REC-CSS2-20110607/'>CSS 2.1</a>, <a href='http://www.ecma-international.org/publications/standards/Ecma-262.htm'>JavaScript 1.8.5</a>, <a href='http://jquery.com/'>jQuery 1.8.3</a>, <a href='http://tablesorter.com/docs/'>tablesorter 2.0.5</a>.<br>
<br>
<h3>Servers used:</h3>
<a href='http://tomcat.apache.org/'>Apache Tomcat 7</a>, <a href='http://www.postgresql.org/'>PostgreSQL 9.1</a>.