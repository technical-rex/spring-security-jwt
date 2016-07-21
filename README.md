# Spring Security and JWT Tutorial

This code is primarily intended to supplement the [Stateless Authentication with Spring Security and JWT](http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt)
tutorial.

If you pick through the code you can also use this repository to discover strategies
for adding XSRF protection and Google OAuth2 sign up/login functionality to your Java
project.

## Requirements

If you've cloned this repo then you'll need the following software to build it:

1. Java 1.8
2. Maven 3.1 or newer

## Authenticating with Google

In order to get the Google OAuth piece working, you will also need to define
`google.client.id` and `google.client.secret` in `environment.properties` or `my.properties`. `my.properties` has the same format and exists in the same location as `environment.properties`, but won't get committed to git repos.

To create the credentials, first go to the [Google Developer Console](https://console.developers.google.com), then follow the steps below:

1. Click on "Credentials" on the left pane of the API Manager screen.
2. Enter your email and a product name on the "OAuth consent screen" tab.
3. On the "Credentials" tab:
   1. Click the "Create credentials" button.
   2. Type a name for your credentials.
   3. Add `http://localhost:8080/auth/google/response` to the "Authorized redirect URIs".
   4. Click "Save".

More info about how to get a Google Client ID and Secret can be found [here](https://developers.google.com/identity/protocols/OAuth2).
