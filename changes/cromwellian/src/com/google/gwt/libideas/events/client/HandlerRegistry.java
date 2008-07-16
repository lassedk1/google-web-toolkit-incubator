package com.google.gwt.libideas.events.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.libideas.events.client.AbstractEvent.Key;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Package protected utility interface to allow flexible implementations of our
 * Handler Manager's handler registry.
 * 
 * 
 */
abstract interface HandlerRegistry {
  void addHandler(Key eventKey, EventHandler handler);

  EventHandler getHandler(Key eventKey, int index);

  int getHandlerCount(Key eventKey);

  void removeHandler(Key eventKey, EventHandler handler);

}

class JavaHandlerRegistry extends HashMap<Key, ArrayList<EventHandler>>
    implements HandlerRegistry {

  public void addHandler(Key eventKey, EventHandler handler) {
    ArrayList<EventHandler> l = super.get(eventKey);
    if (l == null) {
      l = new ArrayList<EventHandler>();
      super.put(eventKey, l);
    }
    l.add(handler);

  }

  public EventHandler getHandler(Key eventKey, int index) {
    assert (index < getHandlerCount(eventKey));
    ArrayList<EventHandler> l = super.get(eventKey);
    return l.get(index);

  }

  public int getHandlerCount(Key eventKey) {
    ArrayList<EventHandler> l = super.get(eventKey);
    if (l == null) {
      return 0;
    } else {
      return l.size();
    }
  }

  public void removeHandler(Key eventKey, EventHandler handler) {
    ArrayList<EventHandler> l = super.get(eventKey);
    if (l != null) {
      l.remove(handler);
    }
  }
}

// Must create wrapper object to implement interface. I argue that the
// cleanliness of the design outweighs the cost of having to create an extra
// object.
class JSHandlerRegistry implements HandlerRegistry {
  // In this code I pass transcript in everywhere to avoid refactoring problems
  // later. In "real" code transcript should be referred to via JSNI calls.
  private JavaScriptObject transcript = JavaScriptObject.createObject();

  public void addHandler(Key eventKey, EventHandler handler) {
    int base = eventKey.hashCode();
    int count = getCount(transcript, base);
    if (count < Key.EXPECTED_MAX_HANDLERS_PER_WIDGET) {
      ++count;
      int slot = base + count;
      set(transcript, slot, handler);
      setCount(transcript, base, count);
    } else {
      throw new IllegalStateException("Else case not implemented yet");
    }
  }

  public EventHandler getHandler(Key eventKey, int index) {
    assert (index < getHandlerCount(eventKey));
    int findAt = eventKey.hashCode() + index + 1;
    return get(transcript, findAt);
  }

  public int getHandlerCount(Key eventKey) {
    int index = eventKey.hashCode();
    return getCount(transcript, index);
  }

  public void removeHandler(Key eventKey, EventHandler handler) {
    remove(transcript, eventKey.hashCode(), handler);
  }

  private native EventHandler get(JavaScriptObject transcript, int index) /*-{
    return transcript[index];
  }-*/;

  private native int getCount(JavaScriptObject transcript, int index) /*-{
    var count = transcript[index];
    if(count == null){
      return 0;
    } else{
    	return count;
    }
  }-*/;

  private native void remove(JavaScriptObject transcript, int base,
      EventHandler handler)/*-{
    var count = transcript[base];
    var index = index + 1;
    var last = index + count - 1;

     for(;index <= last;index++){ 
    	if(transcript[index].equals(handler)){
    		break;
    	}
    }

    for(; index < last; index++){
    	transcript[index] = transcript[index+1]
    }
    transcript[last] = null;
    --transcript[base];
  }-*/;

  private native void set(JavaScriptObject transcript, int slot,
      EventHandler handler) /*-{
    transcript[slot] = handler;
  }-*/;

  private native void setCount(JavaScriptObject transcript, int index, int count) /*-{
    transcript[index] = count;
  }-*/;

}