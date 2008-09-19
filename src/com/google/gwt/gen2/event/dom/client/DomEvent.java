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
   */
  public abstract static class Key<EventType extends DomEvent, HandlerType extends EventHandler>
      extends AbstractEvent.Key<EventType, HandlerType> {
    int nativeEventType;

    /**
     * Creates a {@link Key}. Each event should have a singleton key.
     */
    public Key(int nativeEventType) {
      this.nativeEventType = nativeEventType;
      registered.put(nativeEventType, this);
    }

    /**
     * Gets the native {@link Event} integer corresponding to this key.
     */
    public int getNativeEventType() {
      return nativeEventType;
    }

    /**
     * Wraps the native event in the key's event type.
     */
    public abstract EventType wrap(Event e);
  }

  // Eventually should be replaced by JsIntMap.
  private static class WrappedKeyMap {
    private KeyMap map;
    private HashMap<Integer, DomEvent.Key> javaMap;

    WrappedKeyMap() {
      if (GWT.isClient()) {
        map = KeyMap.create();
      } else {
        javaMap = new HashMap<Integer, DomEvent.Key>();
      }
    }

    public final DomEvent.Key get(int nativeEventType) {
      if (GWT.isClient()) {
        return map.get(nativeEventType);
      } else {
        return javaMap.get(nativeEventType);
      }
    }

    public final void put(int nativeEventType, DomEvent.Key key) {
      if (GWT.isClient()) {
        map.put(nativeEventType, key);
      } else {
        javaMap.put(nativeEventType, key);
      }
    }

    private static class KeyMap extends JavaScriptObject {
      public static KeyMap create() {
        return (KeyMap) JavaScriptObject.createArray();
      }

      protected KeyMap() {
      }

      public final native DomEvent.Key get(int nativeEventType) /*-{
        return this[nativeEventType];
      }-*/;

      public final native void put(int nativeEventType, DomEvent.Key key) /*-{
        this[nativeEventType] = key;
      }-*/;
    }
  }

  private static WrappedKeyMap registered = new WrappedKeyMap();

  /**
   * Fires the given native event on the manager.
   * 
   * @param nativeEvent the native event
   * @param manager the event manager
   */
  public static void fireNativeEvent(Event nativeEvent, HandlerManager manager) {
    DomEvent.Key typeKey = registered.get(nativeEvent.getTypeInt());
    if (typeKey != null && manager.isEventHandled(typeKey)) {
      manager.fireEvent(typeKey.wrap(nativeEvent));
    }
  }

  private Event nativeEvent;

  /**
   * Creates a new dom event based on the given native event.
   */
  protected DomEvent(Event nativeEvent) {
    this.nativeEvent = nativeEvent;
  }

  /**
   * Gets the underlying native event for this {@link DomEvent}.
   */
  public Event getNativeEvent() {
    return nativeEvent;
  }

  /**
   * Prevents the wrapped native event's default action.
   */
  public void preventDefault() {
    nativeEvent.preventDefault();
  }

  /**
   * Stops the propagation of the underlying native event.
   */
  public void stopPropagation() {
    nativeEvent.cancelBubble(true);
  }

}
