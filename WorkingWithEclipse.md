# Step 1 #

[Follow the GWT eclipse setup instructions](http://code.google.com/p/google-web-toolkit/source/browse/trunk/eclipse/README.txt)

Also, you probably want the [subeclipse project](http://subclipse.tigris.org/) as well.

# Step 2a #
Set the following classpath variables

#### `GWT_HOME` ####
Set to the directory containing the gwt jars you want to use.

#### `GWT_TOOLS` ####
Set to your gwt tools directory.

# Step 2b #
Set the following Linked Resources

#### `INCUBATOR_ROOT` ####
Set to your incubator trunk directory.

# Step 3 #
Import the `gwt-incubator-resources` project from the eclipse directory at the root of your subversion workspace. You'll need the one appropriate for you computer.

For example, a windows developer will choose `eclipse\incubator-resources-windows` when Eclipse prompts to "Select root directory".

# Step 4 #

Import the `gwt-incubator` project from your subversion workspace root--**not** from the eclipse directory.