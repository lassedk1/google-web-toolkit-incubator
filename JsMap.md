# Design Principles #
Here are the design principles for the js Map library in order of importance.
  * Must be as fast and have the smallest memory footprint as possible, even if that leads to an uglier API.
  * Code size should be kept small.
  * Many "puts" in sequence should be optimized, if possible, to allow for fast program setup.
  * If we need to use a different implementation in hosted mode, that decision should be transparent to the users.
  * Should have well understood constraints for key/value values.
  * API should be easy to use.
  * Should run outside of GWT.
# Use cases #
Use cases are listed in order of importance.
## Need faster map access ##
Your application is very `put`/`get` intensive.  So the speed performance for Java maps is slowing your application down.
## Need smaller memory profile ##
  * The GWT hash map implementation ends up interning all string map keys, this is normally not a problem, but can become a memory hog if there are a large number of keys churning through the system.
  * Your application has a lot of small maps associated with value objects. You would prefer have those maps stored as efficiently as possible.

## Need smaller code size ##
  * You are trying to squeeze as much size as possible, therefore want to use lighter weight collections.

# Benchmarks #
Unfortunately, the memory allocation from one test interferes with the running time of the  next. Therefore, we profile each map separately.

Each benchmark has X puts + X gets per test. So if the size is 1000, then means the time measured is the time taken for a 1000 puts and then a 1000 gets.
## Baseline Hash Table ##
![http://google-web-toolkit-incubator.googlecode.com/svn/trunk/doc/wiki-support/images/JsMap-hash-benchmark.png](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/doc/wiki-support/images/JsMap-hash-benchmark.png)
## Production JS Map ##
![http://google-web-toolkit-incubator.googlecode.com/svn/trunk/doc/wiki-support/images/JsMap-js-production.png](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/doc/wiki-support/images/JsMap-js-production.png)
## Wrapped JS Map ##
![http://google-web-toolkit-incubator.googlecode.com/svn/trunk/doc/wiki-support/images/JsMap-js-wrapped.png](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/doc/wiki-support/images/JsMap-js-wrapped.png)

# Code Branch #
The current code branch lives at http://google-web-toolkit-incubator.googlecode.com/svn/trunk