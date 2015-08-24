**ImmutableResourceBundle and CssResource have been promoted from the incubator into mainline GWT development.  The incubator code has been deprecated.  [Current documentation](http://code.google.com/p/google-web-toolkit/wiki/ClientBundle) is located in the [main GWT project](http://code.google.com/p/google-web-toolkit).**

# Introduction #

> The resources in a deployed GWT application can be roughly categorized into resources to never cache `.nocache.js`, to cache forever `.cache.html`, and everything else `myapp.css`.  The proposed ImmutableResourceBundle interface seeks to move entries in the everything-else category into the cache-forever category.

# Goals #

  * No more uncertainty if your application is getting the right contents for program resources.
  * Decrease non-determinism caused by intermediate proxy servers.
  * Enable more aggressive caching headers for program resources.
  * Eliminate mismatches between physical filenames and constants in Java code by performing consistency checks during the compile.
  * Use of 'data:' URLs, JSON bundles, or other means of embedding resources in compiled JS when browser- and size-appropriate to decrease the number of round-trips entirely.
  * Provides an extensible design for adding new implementation strategies.
  * Ensure there is no penalty for having multiple ImmutableResourceBundle resource functions refer to the same content.
  * To optionally include statistics about the number and size of resources used by your application.

# Non-Goals #

  * To provide a file-system abstraction

# Examples #

To use the ImmutableResourceBundle, add an inherits tag to your `gwt.xml` file:
```
<inherits name="com.google.gwt.libideas.ImmutableResources" />
```

Interfaces:
```
public interface MyResources extends ImmutableResourceBundle {
 public static final MyResources INSTANCE =  (MyResources) GWT.create(MyResources.class);

 /** @gwt.resource myApp.css */
 public TextResource css();

 /** @gwt.resource myConfiguration.xml */
 public TextResource initialConfiguration();

 /** @gwt.resource com/foo/bar/ownersManual.pdf */
 public DataResource ownersManual();
}
```

You can then say:
```
  Window.alert(MyResources.INSTANCE.css().getText());
  new Frame(MyResources.INSTANCE.ownersManual().getURL());
```

# I18N #

ImmutableResourceBundle is compatible with GWT's I18N module.

Suppose you defined a resource:
```
/** @gwt.resource default.txt */
public TextResource defaultText();
```

For each possible value of the `locale` deferred-binding property, the ImmutableResourceBundle generator will look for variations of the specified filename in a manner similar to that of Java's `ResourceBundle`.

Suppose the `locale` were set to `fr_FR`.  The generator would look for files in the following order:
  1. default\_fr\_FR.txt
  1. default\_fr.txt
  1. default.txt

This will work equally well with `DataResource` types, which can allow you to provide localized versions of other resources, say `ownersManual_en.pdf` versus `ownersManual_fr.pdf`.  Because the files linked by `DataResource` are renamed to strongly-named files as part of the compile process, there is no cost to proactively creating placeholder files that have identical content.

# Pluggable Resource Generation #

Each subtype of `ResourcePrototype` must define a `@gwt.resourceGenerator` annotation whose value is the name of a concrete Java class that extends `ResourceGenerator`.  The instance of the `ResourceGenerator` is responsible for accumulation (or bundling) of incoming resource data as well as a small degree of code generation to assemble the concrete implementation of the ImmutableResourceBundle class.  Implementors of `ResourceGenerator` subclasses can expect that only one `ResourceGenerator` will be created for a given type of resource within an ImmutableResourceBundle interface.

The methods on a `ResourceGenerator` are called in the following order
  1. `init` to provide the `ResourceGenerator` with a `ResourceContext`
  1. `prepare` is called for each `JMethod` the `ResourceGenerator` is expected to handle
  1. `writeFields` allows the `ResourceGenerator` to add code at the class level
  1. `writeAssignment` is called for each `JMethod`.  The generated code should be suitable for use as the right-hand side of an assignment expression.
  1. `finish` is called after all assignments should have been written.

`ResourceGenerators` are expected to make use of the `ResourceGeneratorUtil` class.

# Potential pitfalls #
  * Changing the content of the resources will change the filenames (or data: encoding), thus forcing a recompile of the GWT application. To avoid this, the inlining and renaming features can be globally toggled off in your gwt.xml file during the development phase.
  * Inlining files into the compiled JS may not make sense if those files are not always accessed by the program, thus inlining should be configurable on a per-resource or per-ImmutableResourceBundle basis.


# Automatically creating a bundle interface #

The MakeBundle tool will create a template ImmutableResourceBundle file.  Suppose you wanted to generate bundle for all of the resources in the `com.foo.client.resources` Java package named `FooBundle.java`:

```
#!/bin/sh
java -cp src/:/path/to/gwt-dev-mac.jar:/path/to/gwt-incubator.jar \
  com.google.gwt.libideas.resources.tools.MakeBundle \
  -out /tmp/eclgen -showTypes -name FooBundle.java \
  -package com.foo.client.resources
```

> When running MakeBundle, set the classpath up in the same way that you would configure the classpath when running GWTCompiler. That is, ensure that the source of your application is on the classpath.

> The tool optionally takes a list of files to include in the bundle, e.g. `com/foo/client/resources/*.png`.

It is recommended that you use interface composition to modify the generated interface in order to avoid the need to merge updates to the automatically-generated file:

```
public interface RealBundle extends GeneratedBundle {
  RealBundle IMPL = GWT.create(RealBundle.class);

  @Resource("something_else.txt")
  DataResource anExtraMethod();
}
```

# Future possibilities #
  * Automatic, distributed cache warming.  This could be especially useful for popular apps to pre-distribute resources into intermediate proxy servers.
  * Google Gears resource store integration.
  * If the GWT compiler is re-targeted at widget- or gadget-likeenvironments, they may have particular requirements for packaging and referencing external resources.


# Apache .htaccess configuration #

```
ExpiresActive on
ExpiresDefault "now plus 2 hours"
Header append Cache-Control public
SetOutputFilter DEFLATE

<Files *.cache.*>
 ExpiresDefault "now plus 1 year"
</Files>

<Files *.nocache.*>
 ExpiresDefault "now plus 2 minutes"
</Files>
```


# See also #

  * The StyleInjector makes use of ImmutableResourceBundle to substitute URLs into a CSS stylesheet.
  * [RFC 2397](http://www.ietf.org/rfc/rfc2397.txt)