# Introduction #

---

This guide is for incubator developers of gen2 widgets.

## What is a gen2 Widget? ##

A gen2 widget is the new, and improved, way of writing gwt widgets.  There are two main concepts that make a widget gen2 "compliant" and I will cover them later in this document.

# How does one author a gen2 widget? #

---

## Styles ##
Each widget needs an inner Css interface, assume the name of your widget is MyGen2Widget
```
  public static interface Css extends WidgetCss
```

This is where you'll define any style you want to inject into your widget.
You will need a default css static field for any concrete widget type and a static createCss(String baseName) method.

You will also need to define a default css file for your widget and add it to Gen2CssInjector.


### Decorator ###
Some widgets have extension points to add element padding for the purposes of supporting complex css styles, they should use decorators to define...

## Events ##


You still need to pass the events you want "sunk" to the sink object right now, this will probably change in the near future.


Handlers replace listeners, remember that.  You should replace any listener registration to a handler "addition".


Instead of:


```
if(clickListeners == null) {
  sinkEvents(Event.ON_CLICK);
  clickListeners = new ClickListenerCollection(); 
}
clickListeners.add(handler);
```

You can now use the new specialized events and do something like this:

```
public class MyGen2Widget implements HasClickHandlers {

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return super.addHandler(ClickEvent.TYPE, handler);
  }
}

```

You need to still handle onBrowseEvent by switching on the event type, but instead of sinking the event, checking if any listeners are
registered you can simply call:

```
super.fireEvent(ClickEvent.TYPE, new ClickEvent(nativeEvent));
```

# Template Methods #

---


Template methods are no longer needed, you can simply replace them by corresponding events.  Make sure you check the gen2 event's package before you go off reinventing the wheel though!