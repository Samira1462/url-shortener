# <p align="center">URL Shortener</p>

# Assigment

## Objective

 Implement a URL shortening service using Java and Spring.

## Brief

ShortLink is a URL shortening service where you enter a URL such as https://codesubmit.io/library/react and it returns a
short URL such as http://short.est/GeAi9K.

## Use case

- Implement assignment using:
    - Language: **Java**
    - Framework: **Spring**
    - Two endpoints are required
        - /encode - Encodes a URL to a shortened URL
        - /decode - Decodes a shortened URL to its original URL.
    - Both endpoints should return JSON
    - Choose the HTTP verbs that make the most sense.
- There is no restriction on how your encode/decode algorithm should work. You just need to make sure that a URL can be
  encoded to a short URL and the short URL can be decoded to the original URL. **You do not need to persist short URLs
  to a database. Keep them in memory.**
- Provide detailed instructions on how to run your assignment in a separate markdown file
- Provide API tests for both endpoints

## Criteria

- Java & Spring best practices
- API implemented featuring a /encode and /decode endpoint
- Tests
- Implementation of the shortening algorithm (Algorithm Correctness, Requirement Fulfillment)

# Solution

## Functional and Nun-functional

Q. What is the traffic load or the compacted URLs duration?
Q. How many request per min this server(url shorter) must be handel it?

for solving URL shortener I assume that we intend to accommodate over a billion URLs, I use utilize a character set of
62 characters, including A-Z, a-z, and 0-9, to form short URLs with a length of 'n.' (In this case n=6) This grants us a
total of 62^n unique URLs. To meet our needs, we should strive for the shortest possible URL length while satisfying the
specified requirement.

## Prerequisites

The project uses the following technologies:

* [Java 17](https://www.oracle.com/java/technologies/downloads/)
* [Maven 3](https://maven.apache.org/index.html)

## Pipeline

### Clone the Repository

 ```shell 
 git clone git@github.com:Samira1462/url-shortener.git
```

### Build

```shell
mvn clean package -DskipTests=true
```

### Test

```shell
mvn test
```

### Run

```shell
 mvn spring-boot:run
```

### Access the Application

* The application will be available at [http://localhost:8080](http://localhost:8080).
* Swagger [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* You can use a tool like Postman or curl to interact with the provided endpoints.

