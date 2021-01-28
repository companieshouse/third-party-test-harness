# Third-Party-Test-Harness

## Description:
A small Java web application that is used primarily for the use of karate testing and interacting with CHS services. The testing with this harness focuses on obtaining the correct scopes and permissions that are requested via this web application for use of Companies House Enabled API's
This is an example application of a third party app gaining an Oauth token and using it for access to APIs and displaying the correct scopes.

### Requirements:
- [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- [MongoDB](https://www.mongodb.com)

### Usage:
* In application.properties file changes need to be made to environment variables in order to get the application to work locally.
* Entrypoint is http://localhost:8090/login
* Once every 5 minutes the application attempts a User not present journey with all the users who have logged in, and uses the refresh token if required.
* For CHS usage, environment variables have already been created internally to run app locally or in team environments
* Whilst using this app it is only configured to work with HTTP

### Docker Usage:
* Ensure that Docker-chs-development is up to date 
* Required modules: ch-gov-uk, account-ch-gov-uk and mongo
* Pull image from private CH registry by running `docker pull 169942020521.dkr.ecr.eu-west-1.amazonaws.com/local/third-party-test-harness:latest` command or run the following steps to build image locally: 
  * `Make`
  * Run: `mvn compile jib:dockerBuild -Dimage=169942020521.dkr.ecr.eu-west-1.amazonaws.com/local/third-party-test-harness`
* Entrypoint is http://chs.local/login

### Configuration:
Variable                          | Description                                                          | Example                                                              
--------------------------------- | -------------------------------------------------------------------- | -------------------
THIRD_PARTY_DUMMY_PORT            | http://localhost:PORT                                                | 8090                                                                 
CLIENT_ID                         | A value you set yourself within a Mongo collection                   | THIRDPARTYCLIENT                                       
CLIENT_SECRET                     | A value you set yourself within a Mongo collection                   | CLIENTSECRET 
REDIRECT_URI                      | The redirect URI after you've finished your CHS journey              | `http://localhost:8090/redirect`                                       
TOKEN_URI                         | The token URI for CHS live                                           | `https://identity.company-information.service.gov.uk/oauth2/token`     
PROTECTED_URI                     | The protected URI for CHS live                                       | `https://api.company-information.service.gov.uk/company`               
USER_URI                          | The user URI for CHS live                                            | `https://identity.company-information.service.gov.uk/user/profile`     
AUTHORISE_URI                     | The authorise URI for CHS live                                       | `https://identity.company-information.service.gov.uk/oauth2/authorise` 
