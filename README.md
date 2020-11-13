# Third-Party-Test-Harness

## Description:
A small Java web application that is used primarily for the use of karate testing and interacting with CHS services. The testing with this harness focuses on obtaining the correct scopes and permissions that are requested via this web application for use of Companies House Enabled API's
This is an example application of a third party app gaining an Oauth token and using it for access to APIs and displaying the correct scopes.

### Requirements:
- [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- [MongoDB](https://www.mongodb.com)

### Usage:
* In application.properties file changes need to be made to environment variables in order to get the application to work locally.
* Entrypoint is http://localhost:8090/login
* Once every 5 minutes the application attempts a User not present journey with all the users who have logged in, and uses the refresh token if required.
* For CHS usage, environment variables have already been created internally to run app locally or in team environments

### Configuration:
Variable                          | Description                                                          | Example                                                              
--------------------------------- | -------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
THIRD_PARTY_DUMMY_PORT            | http://localhost:PORT                                                | 8090                                                                 
CLIENT_ID                         | A value you set yourself within a Mongo collection                   | NO DEFAULT - value must be set                                       
CLIENT_SECRET                     | A value you set yourself within a Mongo collection                   | NO DEFAULT - value must be set 
REDIRECT_URI                      | The redirect URI after you've finished your CHS journey              | http://localhost:8090/redirect                                       
TOKEN_URI                         | The token URI for CHS live                                           | https://identity.company-information.service.gov.uk/oauth2/token     
PROTECTED_URI                     | The protected URI for CHS live                                       | https://api.company-information.service.gov.uk/company               
USER_URI                          | The user URI for CHS live                                            | https://identity.company-information.service.gov.uk/user/profile     
AUTHORISE_URI                     | The authorise URI for CHS live                                       | https://identity.company-information.service.gov.uk/oauth2/authorise 