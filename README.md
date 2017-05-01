# Flight Tracker

This CLI application reads a flight connection table (including prices) and then answers questions about stops and prices.

## Implementation Notes.

### Extra Considerations About the Price List.

A valid price list contains multiple values separated by a comma followed by an optional whitespace; additionally, the line containing the price list will have the prefix 'Connections:'. Some extra assumptions besides those requirements are verified:
1) The whitespace is single occurrence of the ASCII '\x32' character.
2) There is no whitespace between the word 'Connections' and the ':' (colon) character.
3) A whitespace exist between the ':' character and the 1st value.
4) No whitespace is expected between a value and the following comma, but --as implied by requirements-- is required between the comma and the next value.
5) The price list parser is case sensitive, therefore airport codes like 'SDQ' and 'sdq' are considered different.
6) Airport codes must be alphanumeric.  
7) The prefix validation is case sensitive and stringent. Words like 'CONNECTIONS' or 'Connection' are rejected.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo


## Running the tests

Explain how to run the automated tests for this system

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


