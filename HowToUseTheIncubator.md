# How to Use the GWT Incubator #

Using the GWT incubator widgets and libraries is as simple as following the four steps below. Give them a try and your project should be ready to roll with the GWT incubator.

  1. Get the incubator jar:
    * Download http://google-web-toolkit-incubator.googlecode.com/files/gwt-incubator.jar.
    * Follow the instructions in [Making GWT Incubator Better](MakingIncubatorBetter.md) to create your own incubator.jar.
  1. Add the incubator.jar to your project's classpath.
  1. Add the following lines to your module file:
```
   <inherits name='com.google.gwt.widgetideas.WidgetIdeas' />	
   <inherits name='com.google.gwt.libideas.LibIdeas' />
```
  1. Report bugs and enhancement requests  [here](http://code.google.com/p/google-web-toolkit-incubator/issues/entry).

Have fun!