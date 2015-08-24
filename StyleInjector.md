**StyleInjector and CssResource have been promoted from the incubator into mainline GWT development.  The incubator code has been deprecated.  [Current documentation](http://code.google.com/p/google-web-toolkit/wiki/ClientBundle) is located in the [main GWT project](http://code.google.com/p/google-web-toolkit).**

# Goal #

  * Apply the techniques outlined in ImmutableResourceBundle with stylesheets

# Details #

In your gwt.xml file:
```
<inherits name="com.google.gwt.libideas.ImmutableResources" />
<inherits name="com.google.gwt.libideas.StyleInjector" />
```

Define your ImmutableResourceBundle:
```
public interface Resources extends ImmutableResourceBundle {
  public static final Resources INSTANCE =
      GWT.create(Resources.class);

  @Resource("myBackground.png")
  @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
  public ImageResource backgroundFunction();

  @Resource("myCss.css")
  public CssResource css();
}
```

The CSS contents (see CssResource for more information):
```
@sprite .some .selector {
  gwt-image: 'backgroundFunction';
}

```

In your `onModuleLoad`:
```
StyleInjector.injectStylesheet(Resources.INSTANCE.css().getText());
```


You now have your stylesheet applied to the document while taking advantage of strongly-named or inlined resource URLs.  Because the standard I18N-style of resource naming is applied to the `ImageResource` instance, it is possible to provide localized CSS background images without the need for multiple, per-locale stylesheets.  It is now possible to have `myBackground_fr.png` and `myBackground_en.png` substituted based on the `locale` deferred binding property.

It is possible to update the contents of a previously-injected stylesheet using the `setContents()` method:
```
StyleElement elt = StyleInjector.injectStylesheet("CSS contents");
StyleInjector.setContents(elt, "New CSS contents");
```