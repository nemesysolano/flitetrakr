# Flight Tracker

This CLI application reads a flight connection table (including prices) and then answers questions about stops and prices.

## Getting Started

### Prerequisites

* JDK 1.8. The JAVA_HOME environment varariable must point to that JDK and JDK's binaries must be included in system's PATH.
* Any of these build systems:
    * Maven 3.3.9
    * Gradle 3.5
    * Ant 1.9.x
 git

### Installing

Copy flitetrakr.zip to the desired folder and unzip it. 

```
$ unzip flitetrakr.zip
```

Move to the **flitetrakr** folder just created after unzipping . 

```
$ cd flitetrakr
```

Build the application using the build system of your choice from the list in the **prerequisites** section.

#### Maven

```
$ mvn clean
$ mvn package
```

#### Gradle

```
$ gradle clean
$ gradle build
```

#### Ant

```
$ ant clean
$ ant
```

Make sure that you are connected to the Internet such that build system can resolve dependencies. The test cases executed by the build process may catch someone's attention in spite of the are not devised to illustrate application's capabilities; do not hover too much over JUNit test logs and please follow instructions in the next section in order check how the application works.  


## Playing with the Application 
The *flitetrakr/data* folder contains two files (namely *connections-1.txt* and *connections-2.txt*) that when provided as input to the application, yield the following results.
 
### First Case: connections-2.txt.
![alt text](images/connections-2.png "Graph 1: Sample Input")
This file contains the sample input and questions found in the [challenge page](https://bitbucket.org/adigsd/backend-flitetrakr). All answers match except *#7*; in this case the challenge answer is **2** whilst program output is **1**. However, if we ask the application to take into account repeated stops at the same place (by setting **-Dcom.assessment.flitetrakr.multiple=true**), we get a perfect match. Check the next two listings in order to catch the situation.

####  Listing 1: Ignoring repeated stops at the same place.
```
$ java -jar target/flitetrakr-1.0-SNAPSHOT.jar  data/connections-2.txt 
1: What is the price of the connection NUE-FRA-LHR?
1: 70

2: What is the price of the connection NUE-AMS-LHR?
2: No such connection found!

3: What is the price of the connection NUE-FRA-LHR-NUE?
3: 93

4: What is the cheapest connection from NUE to AMS?
4: NUE-FRA-AMS-60

5: What is the cheapest connection from AMS to FRA?
5: No such connection found!

6: What is the cheapest connection from LHR to LHR?
6: LHR-NUE-FRA-LHR-93

7: How many different connections with maximum 3 stops exists between NUE and FRA?
7: 1

8: How many different connections with exactly 1 stop exists between LHR and AMS?
8: 1

9: Find all connections from NUE to LHR below 170Euros!
9: NUE-FRA-LHR-70, NUE-FRA-LHR-NUE-FRA-LHR-163
$

```
####  Listing 2: Counting repeated stops at the same place.
```
$ java -Dcom.assessment.flitetrakr.multiple=true -jar target/flitetrakr-1.0-SNAPSHOT.jar  data/connections-2.txt
1: What is the price of the connection NUE-FRA-LHR?
1: 70

2: What is the price of the connection NUE-AMS-LHR?
2: No such connection found!

3: What is the price of the connection NUE-FRA-LHR-NUE?
3: 93

4: What is the cheapest connection from NUE to AMS?
4: NUE-FRA-AMS-60

5: What is the cheapest connection from AMS to FRA?
5: No such connection found!

6: What is the cheapest connection from LHR to LHR?
6: LHR-NUE-FRA-LHR-93

7: How many different connections with maximum 3 stops exists between NUE and FRA?
7: 2

8: How many different connections with exactly 1 stop exists between LHR and AMS?
8: 1

9: Find all connections from NUE to LHR below 170Euros!
9: NUE-FRA-LHR-70, NUE-FRA-LHR-NUE-FRA-LHR-163
$

```

Since all links between to airports are unidirectional (as implied by the connection format **<code-of-departure-airport>-<code-of-arrival-airport>-<price-in-euro>**), it is quite straight forward to understand why question #7 yield different answers by just taking a look at following directed graph (drawn by hand):
 
### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc


