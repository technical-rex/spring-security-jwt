# Spring Security and JWT Tutorial

This code is primarily intended to supplement the [Stateless Authentication with Spring Security and JWT](http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt/)
tutorial.

If you pick through the code you can also use this repository to discover strategies
for adding XSRF protection and Google OAuth2 sign up/login functionality to your Java
project.

## Requirements

If you've cloned this repo then you'll need the following software to build it:

1. Java 1.8
2. Maven 3.1 or newer

In order to get the Google OAuth piece working, you will also need to define
`google.client.id` and `google.client.secret` in either environment.properties.
More info about how to get a Google Client ID and Secret can be found
[here](https://developers.google.com/identity/protocols/OAuth2).
