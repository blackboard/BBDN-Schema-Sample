# BBDN-Schema-Sample

** This project is no longer maintained, as Learn SaaS is moving away from custom database tables **

This project contains sample code for creating a custom table in the Blackboard database schema and interacting with that table using Spring annotations and a DAO. By using these tools, you can build database agnostic

### Project at a glance:
- Target: Blackboard Learn 9.1 SP 11 minimum
- Source Release: v1.0
- Release Date  2015-11-13
- Author: shurrey
- Tested on Blackboard Learn 9.1 October 2014 release

### Requirements:
- Java 7
- Gradle 1.6

### About the Code
This code uses the Spring MVC architecture and implements Data Access Objects to interact with the custom database table as defined via the schema.xml file. Gradle is configured to take a command-line argument for the deployment target:

	gradle -Dserver=localhost:9876 deployB2
	
#### bbdn.sample.schema.Properties
This is the model that corresponds to the database table. Take notice of the Spring annotations therein. You can define the tablename, column, and primary key without writing any extra code. You must also be sure to implement the getDataType method, as this is used by the DAO to identify which table you are accessing and what fields to populate. The data types must match.

#### bbdn.sample.schema.PropertiesController
This is the Spring controller class that handles interaction with the DAO and pushing of data to the view, in this case, /WEB-INF/jsp/properties.jsp. All code is written in the controller, allowing for the JSP to simply handle the user interface. Please take note of the Spring annotations in use. UserAuthorization allows you to verify the user accessing the code has permission to do so. Autowire allows you to automatically instantiate objects that qualify for auto-instantiation, in this case, our DAO object. The @RequestMapping annotation tells Spring the URL that corresponds to the method immediately below it. There are other Spring annotations that can be implemented, but this example covers the basics. For more information on the Spring Framework, check out their <a href="https://pivital.io" target="_blank">homepage</a>.

#### bbdn.sample.schema.PropertiesDAO
This is the class that interacts with the database. In our example, we have implemented a few basic methods. You can easily write custom queries (see SearchById as an example). This extends blackboard.persist.dao.impl.SimpleDAO, so you can also access any of that objects methods, as well.

#### /WEB-INF/schema/schema/schema.xml
This is the file that defines the custom table space you are implementing. This implements a single table, but you can certainly add multiple tables. We've demonstrated a primary key, a few different field types, and a foreign key. The XSD file for the schema.xml is located <a href="https://bbprepo.blackboard.com/content/repositories/releases/blackboard/platform/bb-schema-xsd/9.1.201410.160373/bb-schema-xsd-9.1.201410.160373.xsd" target="_blank">here</a>.

#### /WEB-INF/schema/schema/post_schema_update_sql/insert_default_values.sql.*
These files demonstrate the use of stored procedures within your Building Block. You will notice that there is one each for Oracle, MSSQL, and PSQL. Whenever possible, you should build your SQL statements into your DAO to avoid complications with the database type. Using schema.xml and SimpleDAO allows you to write database-agnostic code.

### Conclusion
This code is a good starting point to learn the basics of custom tables in Blackboard Learn. PLease let us know if you have any questions by contacting us at the <a href="https://community.blackboard.com/community/developers/learn" target="_blank">Blackboard Developer Community site</a>.
