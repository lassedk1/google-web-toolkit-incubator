# Introduction #
Powerful applications often end up with very large trees in them. The original GWT Tree widget was not designed to handle the sheer number of nodes that are generated in some trees. FastTree has been optimized to handle this case.

## Summary Table ##

| **Field** | **Location** |
|:----------|:-------------|
| Demo      | http://collectionofdemos.appspot.com/demo/com.google.gwt.demos.fasttree.FastTreeDemo/FastTreeDemo.html |
| Sample Code | [demo code](http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/demos/fasttree/client/FastTreeDemo.java) |
| Module    | `com.google.gwt.widgetideas.BasicWidget` |
| Source starting point | http://google-web-toolkit-incubator.googlecode.com/svn/trunk/src/com/google/gwt/widgetideas/client/FastTree.java |


# Details #
**Warning** FastTree uses css background images to represent leaf nodes. If you chose not to use style injection, either via the `FastTreeaddDefaultCSS` method or by rolling your on using StyleInjector, you **must** include an invisible `treeOpen.gif` and `treeClosed.gif` in your application. Otherwise IE will not cache the images correctly, thereby defeating the purpose of FastTree.