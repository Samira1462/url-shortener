# Thought Process

_Thought process here as described in the [Readme file](README.md)._
## Getting Started
To get started with the Backend Coding Challenge, follow these steps:

1. **Clone the Repository**
    ```sh 
   git clone git@github.com:Samira1462/url-shortener.git

2. **Access the Application**
The application will be available at `http://localhost:8080`. You can use a tool like Postman or curl to interact with the provided endpoints.
or use swagger http://localhost:8081/url-shortener.html

3. **Run the Application**
   ```
   mvn spring-boot:run
   ```

## Functional and Nun-functional
Q. What is the traffic load or the compacted URLs duration?
Q. How many request per min this server(url shorter) must be handel it?

for solving URL shortener I assume that  we intend to accommodate over a billion URLs, I use utilize a character set of 62 characters, including A-Z, a-z, and 0-9, to form short URLs with a length of 'n.' (In this case n=6) This grants us a total of 62^n unique URLs. To meet our needs, we should strive for the shortest possible URL length while satisfying the specified requirement. 

## Technology Stack
The project uses the following technologies:

- **Java 11**
- **SpringBoot**
- **lombok**





