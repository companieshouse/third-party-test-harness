# Third-Party-Test-Harness

## Description:
A small Java web application that is used primarily for the use of karate testing and interacting with CHS services. The testing with this harness focuses on obtaining the correct scopes and permissions that are requested via this web application for use of Companies House Enabled API's
This is an example application of a third party app gaining an Oauth token and using it for access and displaying,
the correct scopes for access to API's. 

### Requirements:
- [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- [MongoDB](https://www.mongodb.com)

### Usage:
* In application.properties file changes need to be made to environment variables in order to get the application to work
* Entrypoint is [HOME](http://localhost:8090/login)
* Once every 5 minutes the application attempts a User not present journey with all the users who have logged in, and uses the refresh token if required.
* User not present journey was created to suits proof of concept needs
* For CHS usage, environment variables have already been created internally to run app locally or in team environments

### Configuration:
Variable                          | Default                                                              | Description
--------------------------------- | -------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
THIRD_PARTY_DUMMY_PORT            | 8090                                                                 | [localhost](http://localhost):PORT
CLIENT_ID                         | NO DEFAULT - value must be set                                       | A value you set yourself within a Mongo collection
CLIENT_SECRET                     | NO DEFAULT - value must be set                                       | A value you set yourself within a Mongo collection
REDIRECT_URI                      |[REDIRECT](http://localhost:8090/redirect)                            | The redirect URI after you've finished your CHS journey
TOKEN_URI                         |[TOKEN](https://identity.company-information.service.gov.uk/oauth2/token)                                                             | The token URI for CHS live
PROTECTED_URI                     |[PROTECTED](https://api.company-information.service.gov.uk/company)                                                         | The protected URI for CHS live
USER_URI                          |[USER](https://identity.company-information.service.gov.uk/user/profile)                                                              | The user URI for CHS live
AUTHORISE_URI                     |[AUTHORISE](https://identity.company-information.service.gov.uk/oauth2/authorise)                                                         | The authorise URI for CHS live