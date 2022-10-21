## README
When launched, this program will load a web app that is accessible via localhost. It allows you to see the course sequence of all courses within a subject at TTU. This file contains information about how to clone, compile, and run the code. It also lists the file structure as well as the list of dependencies.

##
#### Back story
This program works by using Jsoup to web scrape the data. Then, it parses the data and organizes it into an array list. The data is organized by major, where it is in the course sequence, and a few other things. The visual aspect was written by my professor.

We went through an agile development cycle to make this. This included the following steps. 
1. User stories
2. UML diagrams
3. Multiple build iterations which included: Jsoup for web scraping, JUnit for testing
4. Optimization: Whenever you previously loaded a course sequence, it would save like a cookie. This allowed the next load of that course sequence to be quicker.


###
#### How to clone the code from the repository
* Place the following command in the command prompt (cmd).
````
git clone https://github.com/KChavez04/CoursePR.git
````


###
#### How to compile the program
* Place the following command in the command prompt (cmd).
````
./mvnw compile
````
* Alternatively, within IntelliJ, click the hammer button on the upper, right-hand section.
  * Configuration: CourseTest or CourseCatalogTest


###
#### How to run the program
* Place the following command in the command prompt (cmd).
```
./mvnw spring-boot:run
```

* This will launch Spring Boot to host local host.
* Next, launch local host by opening a web browser and typing the following website.
````
http://localhost:8080
````


###
#### The structure of the directories in the source
* Coding Files
  * The package is edu.tntech.csc2310
  * Starting from source, the directory is src\main\java\edu\tntech\csc2310
* Test Files
  * The package is edu.tntech.csc2310
  * Starting from source, the directory is src\test\java\edu\tntech\csc2310
  

###
#### List of dependencies
* [JUnit](https://junit.org/junit4/)
* [Jsoup](https://jsoup.org/)
* [Maven](https://maven.apache.org/index.html)
* [GSON](https://github.com/google/gson)
* [Spring Boot](https://spring.io/projects/spring-boot)


###
#### Credit
A special thanks to Jerry Gannod for writing the following files.
* Classes:
  * CoursePR.java
  * CoursePRCourse.java
  * CoursePRCourseService.java
  * CoursePRPrereqService.java
* Tests:
  * CourseTest (except where otherwise noted)
  * CourseCatalogTest (except where otherwise noted)
  * CoursePrApplicationTest
