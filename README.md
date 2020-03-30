# stache-lag-backend
A tech task for Rock Seven

stache-lag-backend
A technical challenge for prospective backend developers at Rock 7 / YB Tracking

Parse the JSON document; it contains positional data "moments" from one of our transatlantic yacht races. You can visualise it here: https://yb.tl/arc2017

Populate this information appropriately into a MySQL database for subsequent processing.

Decide upon an effective method to determine the number of other vessels “visible” for a given moment. e.g. for each moment, decide the number of other vessels that would have been seen by a crew member on that vessel at that position/time (make any appropriate assumptions you need to).

Output a summary table showing average number of sightings per vessel, per day. For example, it should be possible to make conclusions such as "On day 6 of the rally, a vessel is likely to see 5 other vessels during the day" and "On the day before they finish, a typical vessel is likely to see 4 other vessels".
We're looking for efficiency, readability and correctness. Extra points if you can give us some other interesting insight into the data that we don't already know!

Send us your resulting table, and your code, or better still, push it to a public git repo and send us a link. Good luck!


My abmitious goal was to build a service which in the future could possibly be integrated and once called via HTTP, produce downloadable statistics.
I have still got some important things to do with the code but I have learned a ton of new stuff thanks to this tasks. Main TODO areas are Tests and the main csv file generator method (which 
should really be no problem but because of the urgency, I would have to submit the nearly-done code).
Since I am mostly a self-taught java lonely cowboy who's got no one to review his code, I would highly appreciate a code review from more experienced java colleagues, even if I will not be your company's perfect match this feedback analysis will help my skills and therefore, my career.  

References
(The following resources were carefully studied and an infinite wisdom from them was used in the project)


Spring In Action. Walls C
https://mkyong.com/java/how-to-parse-json-with-gson/?utm_source=mkyong.com&utm_medium=referral&utm_campaign=footer-left&utm_content=link7
https://github.com/eugenp/tutorials/blob/master/persistence-modules/spring-persistence-simple/src/main/java/com/baeldung/jdbc/EmployeeDAO.java
https://github.com/AlanHohn/java-intro-course/blob/master/src/main/java/org/anvard/introtojava/jdbc/PersonDao.java
https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/testing.html#integration-testing-support-jdbc
https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/data-access.html#jdbc-statements-executing
http://tutorials.jenkov.com/java-unit-testing/index.html
https://www.springboottutorial.com/spring-boot-unit-testing-and-mocking-with-mockito-and-junit
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
https://mkyong.com/spring/spring-jdbctemplate-batchupdate-example/
https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates
https://mkyong.com/java/how-to-export-data-to-csv-file-java/
https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
https://examples.javacodegeeks.com/core-java/sql/jdbc-resultsetextractor-example/
