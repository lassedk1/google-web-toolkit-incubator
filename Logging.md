**An updated logging system that emulates _java.util.logging_ is now present in mainline GWT development.  The incubator code has been deprecated.  [Current documentation](http://code.google.com/p/google-web-toolkit/wiki/Logging) is located in the [main GWT project](http://code.google.com/webtoolkit/doc/latest/DevGuideLogging.html).**


| Demo: | [live demo](http://collectionofdemos.appspot.com/demo/com.google.gwt.gen2.demo.logging.LoggingDemo/LoggingDemo.html) |
|:------|:---------------------------------------------------------------------------------------------------------------------|
| Java doc: | [java doc](http://collectionofdemos.appspot.com/javadoc/com/google/gwt/gen2/logging/shared/Log.html)                 |
| Sample Code: | [sample code](http://code.google.com/p/google-web-toolkit-incubator/source/browse/trunk/src-demo/com/google/gwt/gen2/demo/logging/client/LoggingDemo.java) |
| Module: | [Logging](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/gen2/logging/Logging.gwt.xml) |
| Source | [source code](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/gen2/logging/shared/Log.java) |

The logging package is designed to be a simple client side logging API. The GWT logging design draws heavily from the [Java logging API](http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html)
and the [gwt-log](http://code.google.com/p/gwt-log/) project by Fred Sauer. A special thanks to Fred Sauer for contributing his time to working on the design of this logging system.

## Design Goals ##
### Logging API ###
  * No overhead for logging messages when logging is disabled.
  * Simple lightweight API
  * Support categories
  * Support setting logging level via URL arguments
  * Familiar to Java Programmers
### Logging Handler/Appender/Publishing API ###
    * User configurable logging handlers
    * Support filtering of handlers via Level
    * Handlers should have access to category information per log message
    * The following predefined logging handlers
      1. to System.err/System.out
      1. to the server via RPC
      1. to some predefined widgets
      1. to firebug console
      1. to the GWT hosted mode console



# Design Decisions #

## Deferred binding ##
The Logging api has three compiled options(logging:enabled/disabled/production).  The other option would be to have a set of predefined levels i.e. logging(off/fine/finer/.../severe). Both are good options, so far we believe the single switch will lead to best full experience.

Advantages for multiple levels:
  1. No overhead for logging levels not used when compiled with a GWT 1.5 compiler
  1. One field can be used to deploy the correct logging levels for automated systems.


Advantages of compiler all/none/minimal switch
  1. Simple model: Intuitive easy to understand that logging level comes from runtime and logging support comes from compile time.
  1. Avoids permutation explosion.
  1. Handler Support
    1. Allows users to configure handlers based on the logging mode.
    1. Allows system defaults to be smarter. For instance, in minimal mode no handlers are added by default.
  1. Encourages what we believe is the best deployment model:
    1. One production system where either
      1. Log only severe messages
      1. Log no error messages.
    1. One "debug" system with all logging options.

## Allowing direct access to Logger instances ##
In some cases, it would be useful to directly access a Logger or Handler and print to that one alone.  However, this code would probably not be compiled out in production, so this feature is currently not supported, though the design is meant to accommodation instance loggers.

# Design overview #


## `Log` ##
Log is a static class. Its API is modeled on the java Logger API. Almost all user interactions should be done through this class to allow compile time removal of logging. It will also include some GWT-specific method.  For instance the methods below:
```
   Log.isLoggingEnabled();
   Log.isLoggingProduction();
   Log.debug("I am a message");
```


## `LogHandler` ##
A `LogHandler` is an interface which actually handles the logging messages.  It is a gwt event handler with a single method `LogHandler.onLog(LogEvent event)`

## `LogEvent` ##
A  `LogEvent` is created once the `Log` class determines that it should broadcast its messages to the registered `LogHandler` instances.

## `LogFormatter` ##
As log handlers are no longer classes, the formatting helper methods have been abstracted out into the `LogFormatter` hierarchy. We provide a default text and html log formatter.

## `Level` ##
Defines the current logging Level. Based on the java logging Level class.

## Categories ##
Categories are represented by strings rather then a Category class. This was done because strings are optimized away more often then class objects.

Categories are hierarchical using the "." notation. For instance, the following snippet of code uses categories to selectively log messages.
```
  Log.setDefaultLevel(Level.SEVERE);
  Log.setLevel("gwt", Level.INFO);
  Log.setLevel("gwt.logging", Level.FINE);

  // This message will be logged.
  Log.info("will log", "gwt.subsystem");

  // This message will not be logged.
  Log.fine("will not log", "gwt.subsystem");

  // This message will log.
  Log.fine("will log", "gwt.logging");
```

## Logging states ##
### Enabled ###
  1. Logging is fully enabled
  1. Default handler is `GWTHandler` in hosted mode, `TreeHandler` in web. We use a default widget handler in web mode to ensure the first time someone tries to use the logging system they can find their messages.
  1. Default log level is read as a URL argument parameter.  For example `http://myurl logLevel=INFO`

### Production ###
  1. Only severe error messages are enabled
  1. No default handler
### Disabled ###
No logging support
