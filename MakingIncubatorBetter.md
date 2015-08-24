# Making GWT Incubator Better #
This page explains how to develop with the gwt-incubator source.

As of 9 September 2009, incubator development against GWT 1.7 has ceased. Incubator code now depends upon GWT trunk. Read how to build GWT from source at http://code.google.com/webtoolkit/makinggwtbetter.html.

Critical fixes for incubator 1.7 will be accepted in its release branch, but new development should be done against GWT trunk.

## Setting up the incubator project ##

### Get the source ###
  1. Check out the following code from the gwt-incubator Subversion repository:
    1. The GWT prerequisite tools and third party library code. Use `gwt/tools` directly if you have them installed. Otherwise download them here:
> > > `~/gwt-incubator$ svn checkout http://google-web-toolkit.googlecode.com/svn/tools/ tools`
    1. Check out the Incubator source:
> > > `~/gwt-incubator$ svn checkout http://google-web-toolkit-incubator.googlecode.com/svn/trunk/ trunk-read-only `
    1. For those of you on a windows platform, we highly recommend the [tortoise svn client](http://downloads.sourceforge.net/tortoisesvn/TortoiseSVN-1.5.7.15182-win32-svn-1.5.5.msi?download). In which case you want:
```
  URL of repository: http://google-web-toolkit-incubator.googlecode.com/svn/trunk
  Checkout directory: c:\gwt-incubator\trunk (sample path)
```


### Build and test with ant ###

From the incubator directory, type ` ant -Dgwt.home=<path to your gwt jar> test `

If you are building gwt from source, the gwt.home argument will be something like ` -Dgwt.home=/home/you/src/gwt1.5/build/staging/gwt-mac-0.0.0 `

### Setup for Eclipse ###
Use the instructions from the WorkingWithEclipse wiki page.
### Setup for other IDEs ###
We would much appreciate instructions here, if you are motivated to add them, add them as a comment below and I'll pull them up to this section.

## Contributing to Incubator ##
Before deciding to contribute source code to the incubator project, you should consider the questions below
  1. Do you have a signed Google Contributors Agreement(found [here](http://code.google.com/webtoolkit/makinggwtbetter.html#clas))?
  1. Do you believe your source code should become part of the GWT code base?
  1. Can you provide sample code, unit tests, and javadoc for the code you wish to submit?
  1. Does your source code work on Internet Explorer, FireFox, Opera, and Safari?
  1. Are you willing, for the next couple of months following your code submission, to:
    * Fix all critical bugs in your code
    * Answer outstanding design questions
If the answer to all the questions above is "yes", then your next step is to email
Google-Web-Toolkit-Contributors@googlegroups.com with the subject line "Looking for Incubator sponsor for _synopsis of code_."

example:

> Looking for Incubator sponsor for DatePicker.

At this point someone who already is a committer to gwt-incubator must be willing to sponsor your code. You will work with the sponsor to get the code in shape for submittal.  You may then petition to become a committer to gwt-incubator.

### The incubator reference project ###
GWT incubator contains its own reference project. This has a copy of the gwt museum source code.

This project is right now set up directly for eclipse. To use it:
  1. Ensure your gwt-incubator project is named "gwt-incubator" and exports all of its resources.
  1. import the project defined in \reference.
> You should now see a reference project and two launch configs, the `IncubatorMuseum` config includes all visual suites in the reference project. `SingleIssue` is a config to target a single issue, edit the `SingleIssue.gwt.xml` file.