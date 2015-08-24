# Introduction #
This is a draft for styling conventions that should be used in GWT incubator. This is an interim solution, however should be followed by all gen2 incubator widgets.

# Users #
All of these answers are relevant only to "new style" incubator widgets. In specific, all widgets deriving from the class `IncubatorWidget`

## How do I get styles on the new incubator widgets? ##
They come by default, simply use your widget and see your styles.

## How to I use my own styles instead? ##
All incubator widget styles are grouped per widget, look up in `com.google.gwt.widgetideas.theme.public` to find the correct style sheet, then create your own style sheet in the exact same place, and make sure yours appears first in the class path.

## How do I debug my styles? ##
Include the `DebugCss` module. You should be able to edit your styles freely without additional compilation or reloads.

## How do I say the heck with it and remove all default styling? ##
Include the `DisableDefaultCss` module.

## How do I get obfuscated, pruned, and sprited widget styling? ##
You create your own custom resource bundle. Then you can either directly pass the bundle into your widget or you can override the `CssInfo` class associated with your widget to use your resources by default.

Note, if you do this, you cannot use `setStyleName` with your widget.

# Widget Authors #
## I have a widget, now where do I put the style sheet? ##
In order to only support one default style sheet,and so they are easy to find by our users, all style sheets need to go into the `com.google.gwt.widgetideas.theme.public` directory.

Each widget should have its own style sheet, but don't worry, unless the user specifically defeats us, all such style sheets and stripped of comments and white spaces, then mushed together.

## What can I put in my style sheet? ##
You cannot, unfortunately use any of the cool features of css resources in your default style sheet, as the same style sheet needs to work just like a normal css file. Developing the style sheet in `DebugCss` mode is usually the best way to do this.

## Why does each widget need to have a `Css` interface? ##
In order to support obfuscated css bundles, widgets must read their style names from interfaces.

## Why does each widget need to have a `CssAdaptor` interface? ##
In order to allow `setStyleName` to work by default, all widgets must have a `CssAdaptor`.  Then, for each style name, the adapter returns the base widget name + "-" the dependent style name.

It would be relatively trivial to write a generator to create the `CssAdaptor` classes from the corresponding `Css` classes. However, until that is actually done, you are stuck doing it manually.

## Ok, I have my style sheet, `Css`, and `CssAdaptor` classes done, what is next? ##
First of all, make sure you have added your style sheet to the `DebugCss` module.
Then, for each package there should be a corresponding `CssInfo` class. If your widget's name is 'MyWidget' you should add a method that looks something like this
```

  public MyWidget.Css forMyWidget() {
    if (useCssResources()) {
      inject(bundle().MyWidgetCss());
    }
    MyWidget.CssAdaptor adaptor = new MyWidget.CssAdaptor();
    adaptor.setBaseName("gwt-MyWidget");
    return adaptor;
  }
```

## This seems like a lot of work, is it worth it? ##
Well, that is what we want you guys to tell us. As the `CssResources` paradigm is a very powerful one and allows us to create minified and efficient css.

But, and this is a big but, we do not have a smooth and easy way to integrate it yet into our widget system. The design here is known to have significant compromises.  We're hoping refinements and improvements to it will fall out over time.