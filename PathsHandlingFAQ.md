# Introduction #

Java has the 'classpath' mechanism to find source files. However, GWT doesn't use the classpath system. Also, GWT needs to find files other than source files, such as css files, external js files, and images.

This article will explain where GWT finds the files it needs to compile your code or run hosted mode.

# Bootstrap procedure #

All GWT compilation/running begins with a `.gwt.xml` file. The exact format of this file is explained [in this GWT documentation page](http://code.google.com/webtoolkit/documentation/com.google.gwt.doc.DeveloperGuide.Fundamentals.Modules.ModuleXml.html). However, you don't usually have to fiddle with this file when you start out; the `applicationCreator` script will make this file for you.

# Finding `.gwt.xml` files #

If you look at the script files created by the `applicationCreator`, you'll find something like this:

```
java -cp "$APPDIR/bin:$APPDIR/src:/gwt/gwt-user.jar:/gwt/gwt-dev-mac.jar:/gwt-wl/gwt-widgets-0.1.3.jar"
com.google.gwt.dev.GWTCompiler -out "$APPDIR/www" "$@" com.example.SomeProject;
```

The key part of this file is `com.example.SomeProject` which points the GWT compiler to the `.gwt.xml` file it should start with. As you can see it uses dots instead of slashes. GWT will take this file, replace all dots with slashes, and append .gwt.xml. Thus, the above gets modified to:

`com/example/SomeProject.gwt.xml`

GWT will then try to find this file in the classpath. Usually, the classpath is also defined in the script, as it is above. Normally, `$APPDIR/src/com/example/SomeProject.gwt.xml` will indeed exist, already created for you by the `applicationCreator`. This is the file GWT will bootstrap.

# Finding files #

The `.gwt.xml` file contains references to other paths. This section lists how each path specification works.

## source files ##

Use the `<source>` tag to tell GWT which packages contain actual GWT code.  For example:
```
   <source path="client"/>
```

First, take the 'package name' of your .gwt.xml file that lists this definition. In our example, that would be `com.example`. Then, append this package name to it, so you get `com.example.client`. Any source files that follow BOTH of the following requirements:
  1. has `package com.example.client;` in its source file, or a sub-package of same, AND
  1. appears in the **client** subdir relative to the .gwt.xml file. (The same dir that contains the `.gwt.xml` file should contain a directory named **client** which contains source files).
will then be allowed in your GWT source files.

Note that this directory **MUST** be a sub directory of the directory that contains the `.gwt.xml`file! If you need to include another directory, see _Including other modules_.

If you have no `<source>` tags in your `.gwt.xml` file, `<source path="client"/>` is implied.

## public files ##

Use the `<public>` tag to let GWT know that a certain directory contains files that should be copied to the `www` directory (or in hosted mode, made available to the browser). For example:
```
    <public path="public"/>
```

Just like the **client** subdir in the previous section, the **public** subdir needs to be located in the same place as the `.gwt.xml`. Also, just like the `<source>` tag, this directory must be a subdirectory.

If you have no `<public>` tags in your `.gwt.xml` file, `<public path="public"/>` is implied.

## Adding servlets ##

The `<servlet>` tag is a feature that is used ONLY in Hosted Mode and simply lets you map paths to servlets. The 'path' attribute refers to a java classpath entity, like `com.example.server.SomeServlet`. Unlike the rest of GWT, GWT Hosted Mode will try and find this using the normal java classpath system.

## Including other modules ##

The `<inherits>` tag allows you to include other modules. This (and the `<servlet>` tag) is the ONLY one that allows you to refer to things not in a subdirectory of where the `.gwt.xml` file is located. It's also a useful device for separating out common code you want to use in multiple GWT projects.

First create another module, with its own `.gwt.xml` file. Then, take out the `<entry-point>` tag (a utiliy module won't have an entry point). Now make sure this `.gwt.xml` file is somewhere in the classpath, then write an `<inherits>` tag that refers to the new `.gwt.xml` file in the same way that the _Finding `.gwt.xml` files_ section.

For example, in our example, the classpath includes `/gwt-wl/gwt-widgets-0.1.3.jar`. this jar file contains a `.gwt.xml` file inside with the path `org/gwtwidgets/WidgetLibrary.gwt.xml`. We can let GWT include this module by adding the following to it:
```
    <inherits name="org.gwtwidgets.WidgetLibrary"/>
```