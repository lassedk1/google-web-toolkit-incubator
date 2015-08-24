# Introduction #

The current listener widget event system is very easy to use and understand, which are significant advantages. However, it has certain disadvantages as well, therefore in this document we:
  * Detail the disadvantages of the current listeners system.
  * Explain the proposed event handler system.
  * Revisit the disadvantages of the current listener system and show how the handler system addresses each one.


# Disadvantages of current listener system #
  1. We cannot add extra information about an event.
    * Why: Because modifying an interface is a breaking change.
    * Example: We could not add native events to the current `Click` or `Change` listeners.
  1. We cannot add a new feature to a current listener.
    * Why: Because modifying an interface is still a breaking change.
    * Example: `TreeListener` cannot be expanded to allow more tree events.
  1. Many widgets do not have any listeners, and yet have three or four listeners fields that need to be null initialized.
    * Example: Most `Label` widgets have no events, yet have three fields that need to be null initialized.
  1. We have to sink unnecessary events on widgets.
    * Why: Because many event listeners bundle several event methods in a single listener interface.
    * Example: To listen to a mouse down event, mouse move events are also sunk.
  1. Our users end up with many empty event methods or need to extend from Adapter classes.
    * Why: Users only usually care about a subset of  listener methods defined in the interface.
    * Example: to add a mouse up listener, you end up with empty mouse down and mouse move methods as well.
  1. We repeat the same lines of boiler plate code in many different widgets to check if the listeners are instantiated and instantiate them. Therefore, no individual listener method can ever be inlined away.
    * Example:
```
  if(clickListeners == null) {
    sinkEvents(Event.ON_CLICK);
    clickListeners = new ClickListenerCollection(); 
  }
  clickListeners.add(handler);
```
  1. All event sources must be widgets.
    * Why: because interface handler methods define the source as `Widget`.
  1. All widget creators must sink their own events.


# Proposed Handler System #
## Widget event API ##
This is the API that will be used by all GWT users. It allows users to add new handlers to widgets.

### Class overview ###
  * `GwtEvent`
    * Base class for all events
    * New Event classes
      * `ClickEvent`
        * Event which wraps a native click event.
      * `KeyDownEvent`
        * Event which wraps a native key down event.
      * ...
  * `HandlerRegistration`
    * Bookkeeping class to allow handlers to be removed from widgets. Each time a handler is added to a widget, a `HandlerRegistration` instance is returned
  * `EventHandler`
    * Base interface for all handlers
    * New handler interfaces
      * `ClickHandler`
        * `onClick(ClickEvent event)`
      * `KeyDownHandler`
        * `onKeyDown(KeyDownEvent event)`
      * ...
  * For each event, we create an interface for adding event handlers..
    * `HasClickHandlers`
      * `HandlerRegistation addClickHandler(ClickHandler handler)`
        * Adds a click handler to the given widget.
    * `HasKeyDownHandlers`
      * `HandlerRegistration addKeyDownHandler(KeyDownHandler event)`
        * Adds a key down handler to the given widget.
    * ...
### How it works ###
  * Each handler has a single method with a single argument, such as `void onClick(ClickEvent event)` or `void onKeyDown(KeyDownEvent event)`.
  * Each widget supporting an event type will implement the appropriate `Has*Handlers` interface and therefore have a method of the form `HandlerRegistration addFooHandler(FooEvent)`. For example a widget supporting a click handler must implement `HasClickHandlers`.
  * To remove a handler, you must save the `HandlerRegistration` instance returned from the handler's add method and call `removeHandler()` on it.

### Sample use of a handler ###
```
 DateBox start = new DateBox();
 start.addKeyDownHandler(new KeyDownHandler() {
      public void onKeyDown(KeyDownEvent e) {
        if (e.getNativeKeyCode() == KEY_RIGHT){
           Window.alert("I have to be right");
        }
      }
    });
```

## Widget Developer Event API ##
This is the API that will be used by most widget creators who are creating and packaging their own widgets.  It allows widgets to hook up new handlers and send events to them.

### Class overview ###

  * `AbstractEvent.Type`
    * Encapsulates the meta information about an event class.
    * For each event type, we have a specific type
      * `ClickEvent.getType()`
        * The type used to register handlers on a click event.
      * `KeyDownEvent.getType()`
        * The type used to register  handlers on a key down event.
      * ...
    * Each event type has the following information
      * Unique id associated with the event type (used to store handlers)
      * How to fire the handler method
### Extra Widget methods ###
    * `protected final HandlerRegistration addHandler(GwtEvent.Key<?,HandlerType> key, HandlerType handler)`
      * Registers the handler for the event type.
    * `protected final HandlerRegistration addHandlerAndSink(DomEvent.Type<?,HandlerType> key, HandlerType handler)`
      * Registers the handler and also sinks events on it.
    * `protected final void fireEvent(GwtEvent event)`
      * Fires the event to all registered handlers

### How it works ###
  * When a handler is added to a widget, the widget calls `addHandler` with the event's type and the supplied handler.
```
 public void addKeyDownHandler(KeyDownHandler handler) {
    addHandler(handler,KeyDownEvent.getType());
 } 
```

  * To use the widget's handlers, the widget must create an event object, then call `Widget`'s `fireEvent`.
```
  @Override
  public void onBrowserEvent(Event e){
    ...
    case ON_KEYDOWN:
      fireEvent(new KeyDownEvent(e)); 
  }
```


### Sample widget event handling code ###
This code shows how to wire a `DateBox` to handle a `KeyDownHandler`. In  other words, this sample implements the `DataBox.addKeyDownEvent`  functionality used above.

```
public class DateBox extends Composite implements FiresKeyDownEvents {
...

 // implements FiresKeyDownEvents.addKeyDownHandler
 public void addKeyDownHandler(KeyDownHandler handler) {
    // Widget's addDomHandler method. 
    // Registers the current handler as a KeyDownEvent handler.
    addDomHandler(handler,KeyDownEvent.getType());
 }
```

As `Widget` now has a default `onBrowserEvent` implementation, you do not need to explicitly fire the key down event yourself.

## Advanced widget creation ##
This API is for those widget creators who need to create custom events and for those creating widgets that do not extend `Widget`

### Class Overview ###
  * `HandlerManager`
    * Manages all event handlers for widgets. `Widget` lazily creates a `HandlerManager` instance when `addHandler` is called.
    * Each `HandlerManager` is associated with a single widget. That widget is set as the source for all events passed into the manager.

### Protected methods ###
  * `AbstractEvent`
    * `abstract AbstractEvent.Type getAssociatedType()`
      * Allows event instances to specify the static event type.
  * `AbstractEvent.Type`
    * `protected abstract void fireEvent(EventHandler handler, EventType.Type type)`
      * Allows handler manager to call correct handler method on the instance.


### Steps to create a custom event ###
This is an event that is triggered when the user becomes happy.

  1. Define a new event class.  You can add arbitrary metadata in the event class. For simplicity, we will not include any here though.
```
public class HappyEvent extends GwtEvent{
   ...
}
```
  1. Define a new handler and marker interface for the event class.
```
interface HappyHandler extends EventHandler {
  public void onHappiness(HappyEvent event);
}

interface HasHappyEvents {
  public HandlerRegistration addHappyHandler(HappyHandler handler);
}
```
  1. Add a unique event type
```
class HappyEvent extends AbstractEvent{
  public static AbstractEvent.Key KEY = new AbstractEvent.Key(){...}
 
  public GwtEvent.Key getKey(){
    return KEY; 
  }
  ...
}
```
  1. Wire up the handler's `fire` method
```
class HappyEvent extends GwtEvent {
  static Key<HappyEvent,HappyHandler> KEY = new Key<HappyEvent,HappyHandler>(){
    protected void fire(HappyHandler handler, HappyEvent event) {
       handler.onHappiness(event);
    };
   ...
}
```


### Full code needed to create a `Happy` handler ###

```
public interface HappyHandler extends EventHandler {
  public void onHappiness(HappyEvent event);
}



public interface HasHappyEvents {
  public HandlerRegistration addHappyHandler(HappyHandler handler);
}

public class HappyEvent extends GwtEvent { 
  public final static Key<HappyEvent,HappyHandler> KEY = new Key<HappyEvent,HappyHandler>(){
    protected void fire(HappyHandler handler, HappyEvent event) {
       handler.onHappiness(event);
    }

  public GwtEvent.Key getKey(){
    return KEY; 
  } 
}

```

## How it actually works ##
  * Adding event handler **myHandler** as a handler for `MyEvent`
    1. In `Widget`
      * ensure `HandlerManager` **handlerManager** instance exists
      * call `handlerManager.addHandler(MyEvent.KEY, myHandler)`
    1. In `HandlerManager`
      * store **myHandler** in a map indexed by event key
      * if this is the first handler of the given type, call `source.subscribeToEvent(myEvent.KEY)' for event specific setup
  * Firing a `MyEvent` event
    1. In 'Widget'
      * create new `MyEvent` **myEvent** instance
      * call `handlerManager.fireEvent(myEvent)`
    1. In `HandlerManager`
      * set the correct source field in **myEvent**
      * get the correct event key from **myEvent**, in specific `MyEvent.KEY`
      * retrieve the list of handlers associated with `MyEvent.KEY`
      * call  `myEvent.fireEvent(handler)` on each handler in the list.
    1. In `MyEvent`
      * cast the current handler as a `MyEventHandler`
      * call correct handler method on the current handler, passing in the current event.


## How the handler system solves problems ##

|Listener Problem | Handler solution |
|:----------------|:-----------------|
|(1) Cannot add extra information about an event | Can add metadata to event |
|(2) Cannot add extra features | Each handler represents a single method, so more can always be added |
|(3) Excess unused listener fields | A single manager field is used |
|(4) Excess events are sunk | Only the events that are needed are sunk |
|(5) Extra blank methods | No extra methods needed |
|(6) Boiler plate code | Between `Widget` enhancements and `HandlerManager` classes, most boiler plate code removed. Remaining boiler plate code will be inlined |
| (7) All event sources must be widgets | sources simply must implement the `Fires*` interface |
| (8) All widgets must manage sinking of events | All standard widgets have events sunk automatically |

## Migration Path ##
Removing all the listener methods within a single iteration of a library would be very disruptive. Instead, the listeners methods will be deprecated.  In order to avoid supporting both sets of machinery within each widget, we will introduce wrapper classes to convert listeners to handlers.
## Default `OnBrowserEvent` implementation ##
We already have a significant amount of boiler plate code in the libraries code to fire native dom events to listeners. The conversion of listeners --> handlers makes this boiler plate code event larger, which would lead to more code overall in GWT applications, a prospect we would rather avoid.

Additionally, by adding `addHandlerAndSink()` to `Widget`, we have successfully hidden half the complexity of dealing with native browser events. If the default `onBrowserEvent` just did the right thing, then it would be much easier for beginning GWT developers to create widgets directly, which leads to tighter, smaller code.

### Solution ###
We wire up `DomEvent` so it can fire a native event directly on the handler manager.

We register dom event keys lazily, so that unused event types (i.e. ones where no handler is ever constructed) are still dead-stripped in the compiled output.

### How it works ###
#### In `Widget` ####
```
 public void onBrowserEvent(Event e){
     DomEvent.fireEvent(e,getHandlerManager());
 }
```

#### In `DomEvent` ####
We introduce a specialized `Type` subclass with the following information:
  * A single native event type.
  * A global mapping which registers each type based on the native event type.

For instance, here is the `DomEvent.Key` for click events:
```
  /**
   * Event Key for Click.
   */
  public static final Key<ClickEvent, ClickHandler> KEY = new Key<ClickEvent, ClickHandler>(
      Event.ONCLICK) {
    @Override
    protected void fire(ClickHandler handler, ClickEvent event) {
      handler.onClick(event);
    }

    @Override
    public ClickEvent wrap(Event nativeEvent) {
      return new ClickEvent(nativeEvent);
    }
  };
```

In `DomEvent`, then we have the fire event method.

```
   public static void fireEvent(Event e, HandlerManager m) {
     DomEvent.Key eventKey = registeredDomKeys.get(e.getEventType());
     if(m.hasHandlersFor(eventKey)) {
        m.fireEvent(eventKey, eventKey.wrap(e));
     }
  }
```
