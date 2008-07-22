Eclipse 3.3.X instructions

These instructions are intended for contributors to the GWT incubator who
want to run the Eclipse IDE. It describes how to configure Eclipse for the
correct coding styles and how to setup a project for debugging the GWT
incubator code.

First, you should follow the instructions from setting up GWT for
Eclipse in the eclipse/README.txt file from a trunk checkout.

== Configure Eclipse Environment==

  All relative paths are relative to the GWT incubator's source repository's
  'trunk/eclipse' folder. For best results, launch Eclipse from the
  trunk/eclipse folder from the command line.

---------- Required GWT variables ---------

Window->Preferences->General->Workspace->Linked Resources
Create a variable named "GWT_INCUBATOR" pointing to the directory where
you checked out the "trunk" folder.

== Importing the GWT incubator project ==

1) Import the 'gwt-incubator' projects

  File->Import->General->Existing Projects into Workspace->Next
  Browse to the 'trunk/eclipse' folder and select it

  Then press the Finish button.

  Non-windows users: By default, gwt-incubator depends on gwt-dev-windows,
  which you will not have imported.  You must update the gwt-user project
  configuration to depend on gwt-dev-linux or gwt-dev-mac (whichever one you
  imported).  This can be done by editing gwt-incubator's .classpath file
  directly, or through the IDE  under Project->Properties->Java Build
  Path->Projects.

2) Dismiss the welcome tab if you are setting up an Eclipse workspace
  for the first time.

  If they did not compile, recheck the setting of

     - GWT_ROOT
     - GWT_TOOLS
     - GWT_INCUBATOR
     - JDK_HOME

  Then refresh each project.

