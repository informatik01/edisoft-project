There are three Java projects in the repository:
 * *EdisoftXMLProcessor* (Java SE desktop console application)
 * *EdisoftWebApp* (Java EE web application)
 * *SecurePasswordEncoder* (Java SE desktop GUI application)

----
<h3>EdisoftXMLProcessor project description</h3>

The main purpose of this application is to process XML files that represent
invoice documents and populate a database with the related records.

At the start the application first scans the predefined directory for XML files
and transforms them using the also predefined XSLT stylesheet. It then populates
the appropriate database tables with the related data. And after that, it starts
monitoring the above mentioned predefined directory, containing XML files, for changes:
creation and updates of the XML documents.


<h3>EdisoftWebApp project description</h3>

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
protocol with predefined URLs (see <code>&lt;transport-guarantee&gt;</code> in the <i>web.xml</i>).


<h3>SecurePasswordEncoder project description</h3>
This is a desktop GUI application.

It takes some password provided by a user,
generates a cryptographically strong random number for the salt,
and hashes the salted password using Secure Hash Algorithm (SHA).

Salted password hash along with the salt can be stored in a
database and used, for example, as part of a web application
login system.
