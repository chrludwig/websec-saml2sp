# WebSec SAML 2.0 SSO Service Provider Demo Application

This project implements a [Spring Boot](http://projects.spring.io/spring-boot/) Web application that demonstrates
*Single Sign On* using the [SAML 2.0 WebSSO](http://docs.oasis-open.org/security/saml/v2.0/saml-profiles-2.0-os.pdf)
profile. It builds on Vincenzo De Notaris's Spring Boost-based Spring Security SAML Extension
[Sample Service](https://github.com/vdenotaris/spring-boot-security-saml-sample).

## Functional changes
* Besides SSOCircle, **saml2sp** supports a locally running
[WSO2 Identity Server](http://wso2.com/products/identity-server/) (Version >= 5.1.0) as an alternative IdP.
* Role information in the SAML credential attributes is used to determine the Spring Security
[_authorities_](http://docs.spring.io/spring-security/site/docs/3.2.7.RELEASE/apidocs/org/springframework/security/core/Authentication.html#getAuthorities())
(or roles) of the logged in user. (If there are no supported roles in the the SAML credential, then the _USER_ role is
assumed by default.)
* The various pages of the Web application have different access control requirements. The pages are accessible:
  * Without any constraints by everybody;
  * By logged in users only (no matter their roles);
  * By anonymous users only (i.e., not logged in users);
  * By users in the _USER_ and _ADMIN_ roles only, respectively;
  * By no-one.

## Build

The build of **saml2sp** requires
[Oracle JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
(To be precise, **saml2sp** can probably be built with any Java >= 7, including OpenJDK. But
WSO2's Identity Manager still requires Oracle's Java implementation.)

For building **saml2sp**, you only need to call `./mvnw clean install` in the project's top level
directory. This will produce an executable JAR file in the target directory.

## Running

The Web application is deployed in an embedded Tomcat inside the JAR file. When you start it by calling
```
java -jar saml2sp-0.0.2-localInMemoryAuth-SNAPSHOT.jar
```
then the embedded Tomcat will serve the application at http://localhost:8080/ .
