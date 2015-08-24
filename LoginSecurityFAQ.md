This FAQ page is slightly more hands-on, but you should definitely also read [Security for GWT applications](http://groups.google.com/group/Google-Web-Toolkit/web/security-for-gwt-applications).

# Login Security #

This article describes how to do the following:
  * Create a 'login' page that is based on user/password authentication.
  * Store this data in a secure fashion on your server.
  * Allow users to 'remain logged in' for as long as you want so they don't have to enter their user name and password every time.
  * Make 'auto-complete' features of most modern browsers work with your GWT's login page.
  * Discussion on adding more security with either HTTPS or fancy hashing algorithm.

## Creating a 'login' page ##

A login page is consists really of just a [TextBox](http://code.google.com/webtoolkit/documentation/com.google.gwt.user.client.ui.TextBox.html) for the username or email address or other identifier (henceforth known as username, and a [PasswordTextBox](http://code.google.com/webtoolkit/documentation/com.google.gwt.user.client.ui.PasswordTextBox.html) for the password.

Add a 'login' button to the mix and that's all there's to it. This login button will result in an AJAX call to your server, using either [JSON](http://www.json.org/) over [RequestBuilder](http://code.google.com/webtoolkit/documentation/com.google.gwt.http.client.RequestBuilder.html) or [GWT-RPC](http://code.google.com/webtoolkit/documentation/com.google.gwt.doc.DeveloperGuide.RemoteProcedureCalls.html).

Your server will then validate this login, and return a _sessionID_ to your GWT app. The GWT app will store this _sessionID_ in a static field. For every further request your GWT app makes to your server, include this _sessionID_ in the **payload** of the request. (Either in the JSON data or the object you are transferring using GWT-RPC).

> NB: Do _NOT_ attempt to use the `Cookie` header to transfer the sessionID from GWT to the server; it is fraught with security issues that will become clear in the rest of this article. You **MUST** transfer the sessionID in the payload of the request. For an example of why this can fail, see CrossSiteRequestForgery.

## Storing user/pass info on the server ##

During account registration (or a change password operation), your user will specify a username and a password that he'd like to use. You'll need to store this information on your server somehow so that you can authenticate future login requests. However, storing the passwords without encryption on your server means that losing your database, or a compromised server, will result in your user's passwords being out on the street. The vast majority of users 're-use' their passwords, which means virtually their entire online existence is at risk. This is irresponsible, not to mention the bad press such a breach would generate.

Fortunately there are ways to 'mangle' the password so that you can still authenticate users, but without storing the password itself. This act is called **hashing**.

The basic mathematical principle behind hashing is  explained [on this wikipedia page](http://en.wikipedia.org/wiki/Cryptographic_hash_function), but all you really need to know is:

Use BCrypt. It's a very simple algorithm available for a number of platforms that takes a password and converts it into a string which can be used to ascertain that someone _knows_ the password without actually storing the password. It's specifically written for login authentication and security experts have thoroughly reviewed it.

  * [jBCrypt](http://www.mindrot.org/projects/jBCrypt/): BCrypt for Java
  * [BCrypt](http://bcrypt.sourceforge.net/): BCrypt for C/C++ (the original and official implementation)
  * [ruby-bcrypt](http://bcrypt-ruby.rubyforge.org/): BCrypt for Ruby
  * [Portable PHP Hashing Framework](http://www.openwall.com/phpass/): BCrypt for PHP
  * [py-bcrypt](http://www.mindrot.org/projects/py-bcrypt/): BCrypt for Python

So, whenever a user registers or changes a password, send the password to your server as usual, but then let your server BCrypt the content. Then, when a user logs in, simply ask the BCrypt framework to check if the password entered is validated by the hash that you stored in e.g. a database.

Add new account to database example for java using jBCrypt:
```
    String password = /*(get password from incoming JSON or GWT-RPC request)*/;
    String hash = BCrypt.hashpw(password, BCrypt.genSalt());
    //(create new user entry in db storing ONLY username and hash, *NOT* the password).
```

Check if an incoming user/pass combo is valid for java using jBCrypt:
```
    String password = /*(get password from incoming JSON or GWT-RPC request)*/;
    String hashFromDB = /*(obtain hash from user's db entry)*/;
    boolean valid = BCrypt.checkpw(password, hashFromDB);
    if ( valid ) generateSessionIDAndSendItBackToClient();
    else sendErrorToClient("Wrong Username or Password.");
```

BCrypt is similarly trivial to use for the other languages.

## How to remember logins ##

Our login system so far misses a useful feature: For now it requires users to log in again every time.

We can use Cookies to allow the user's web browser to 'remember' the login. In GWT, to set the cookie (which you'd do right after your GWT code receives the response as we did in the previous code fragment):
```
    String sessionID = /*(Get sessionID from server's response to your login request.)*/;
    final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.
    Date expires = new Date(System.currentTimeMillis() + DURATION);
    Cookies.setCookie("sid", sessionID, expires, null, "/", false);
```

Now you can run the following code right after your `!EntryPoint` begins execution:
```
    String sessionID = Cookies.getCookie("sid");
    if ( sessionID != null ) checkWithServerIfSessionIdIsStillLegal();
    else displayLoginBox();
```

**Remember** - you must never rely on the sessionID sent to your server in the cookie _header_ ; look only at the sessionID that your GWT app sends explicitly in the payload of messages to your server.

## auto-complete and GWT ##

Certain browsers offer the option to store username/password combinations so that they are automatically filled in for the user the next time they visit the page. However, usually this works only if there are two input boxes (one of type password), and only if those input boxes are loaded along with the main page, and not added later via javascript (which is what GWT does).

In order to force these input boxes to show up, stuff them in the project's HTML in an invisible div. Then, in GWT, instead of creating a TextBox and a PasswordTextBox, make the div visible and read out the values using the DOM.**library.**

TODO: Expand on this section.

## Using Acegi with GWT ##

[Acegi](http://www.acegisecurity.org/) is a security system for [Spring](http://www.springframework.org/), but it can also be used separately. Bruno Marchesson wrote an article about it: [Using Acegi with GWT](http://www.dotnetguru2.org/bmarchesson/index.php?p=678&more=1&c=1&tb=1&pb=1).

## Extra Security Discussion ##
> _Based on a chat with GWT's Bob Vawter_

See http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/59f3aafcf4951523

TODO: Explain how without https security is limited regardless, touch on bobv's proposal for running login outside of GWT over HTTPS, setting a (non-secure) cookie with a key, redirecting to a non-https GWT app which reads the cookie again, and then using a JS version of e.g. bcrypt or some other hasher to generate fresh hash values for every request, involving a counter so that the server knows which hashes have been 'used' - to protect against replay attacks. The highlights of this are discussed here