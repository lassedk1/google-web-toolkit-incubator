/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.gen2.event.dom.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.gen2.event.shared.AbstractEvent;
import com.google.gwt.gen2.event.shared.EventHandler;
import com.google.gwt.gen2.event.shared.HandlerManager;
import com.google.gwt.user.client.Event;

import java.util.HashMap;

/**
 * {@link DomEvent} is a subclass of AbstractEvent that provides events that map
 * to DOM Level 2 Events. It provides an additional method to access the
 * underlying native browser event object as well as a subclass of
 * AbstractEvent.Key that understands GWT event bits used by sinkEvents().
 * 
 */
public abstract class DomEvent extends AbstractEvent {

  /**
   * Key class used by BrowserEvent subclasses. Includes extra method to return
   * event bits for sinkEvents().
   * 
   * @param <EventType> event type of this key
   * @param <HandlerType> handler type of this key
   */
  public abstract static class Key<EventType extends DomEvent, HandlerType extends EventHandler>
      extends AbstractEvent.Key<EventType, HandlerType> {
    int nativeEventType;
    DomEvent cached;

    /**
     * Creates a {@link Key}. Each event should have a singleton key.
     * 
     * @param nativeEventType the native event type
     */
    public Key(int nativeEventType) {
      // All clinit activity should take place here for DomEvent.
      if (registered == null) {
        registered = new WrappedKeyMap();
      }
      this.nativeEventType = nativeEventType;
      registered.put(getType(nativeEventType), this);
    }

    /**
     * Gets the native {@link Event} integer corresponding to this key.
     * 
     * @return the native event type
     */
    public int getNativeEventType() {
      return nativeEventType;
    }

    /**
     * Wraps the native event in the key's event type.
     * 
     * @param nativeEvent
     * @return the wrapped native event
     */
    abstract EventType wrap(Event nativeEvent);
  }

  // Eventually should be replaced by JsStringMap.
  private static class WrappedKeyMap {
    private static class KeyMap extends JavaScriptObject {
      public static KeyMap create() {
        return (KeyMap) JavaScriptObject.createObject();
      }

      protected KeyMap() {
      }

      public final native DomEvent.Key get(String nativeEventType) /*-{
        return this[nativeEventType];
      }-*/;

      public final native void put(String nativeEventType, DomEvent.Key key) /*-{
        this[nativeEventType] = key;
      }-*/;
    }

    private KeyMap map;

    private HashMap<String, DomEvent.Key> javaMap;

    WrappedKeyMap() {
      if (GWT.isClient()) {
        map = KeyMap.create();
      } else {
        javaMap = new HashMap<String, DomEvent.Key>();
      }
    }

    public final DomEvent.Key get(String nativeEventType) {
      if (GWT.isClient()) {
        return map.get(nativeEventType);
      } else {
        return javaMap.get(nativeEventType);
      }
    }

    public final void put(String nativeEventType, DomEvent.Key key) {
      if (GWT.isClient()) {
        map.put(nativeEventType, key);
      } else {
        javaMap.put(nativeEventType, key);
      }
    }
  }

  private static WrappedKeyMap registered;

  /**
   * Fires the given native event on the manager.
   * 
   * @param nativeEvent the native event
   * @param manager the event manager
   */
  public static void fireNativeEvent(Event nativeEvent, HandlerManager manager) {
    if (registered != null) {
      DomEvent.Key typeKey = registered.get(nativeEvent.getType());
      if (typeKey != null && manager.isEventHandled(typeKey)) {
        if (typeKey.cached == null || typeKey.cached.isLive()) {
          typeKey.cached = typeKey.wrap(nativeEvent);
        } else {
          typeKey.cached.reset(nativeEvent);
        }
        manager.fireEvent(typeKey.cached);
      }
    }
  }

  private static String getType(int type) {
    switch (type) {
      case 0x01000:
        return "blur";
      case 0x00400:
        return "change";
      case 0x00001:
        return "click";
      case 0x00002:
        return "dblclick";
      case 0x00800:
        return "focus";
      case 0x00080:
        return "keydown";
      case 0x00100:
        return "keypress";
      case 0x00200:
        return "keyup";
      case 0x08000:
        return "load";
      case 0x02000:
        return "losecapture";
      case 0x00004:
        return "mousedown";
      case 0x00040:
        return "mousemove";
      case 0x00020:
        return "mouseout";
      case 0x00010:
        return "mouseover";
      case 0x00008:
        return "mouseup";
      case 0x04000:
        return "scroll";
      case 0x10000:
        return "error";
      case 0x20000:
        return "mousewheel";
      case 0x40000:
        return "contextmenu";
      default:
        return null;
    }
  }

  private Event nativeEvent;

  /**
   * Creates a new dom event based on the given native event.
   * 
   * @param nativeEvent the native event
   */
  protected DomEvent(Event nativeEvent) {
    this.nativeEvent = nativeEvent;
  }

  /**
   * Gets the underlying native event for this {@link DomEvent}.
   * 
   * @return gets the native event
   */
  public final Event getNativeEvent() {
    assertLive();
    return nativeEvent;
  }

  /**
   * Prevents the wrapped native event's default action.
   */
  public void preventDefault() {
    assertLive();
    nativeEvent.preventDefault();
  }

  /**
   * Stops the propagation of the underlying native event.
   */
  public void stopPropagation() {
    assertLive();
    nativeEvent.cancelBubble(true);
  }

  @Override
  public String toString() {
    return getType(getKey().getNativeEventType()) + " event";
  }

  @Override
  protected abstract DomEvent.Key getKey();

  void reset(Event event) {
    super.revive();
    nativeEvent = event;
  }
}
