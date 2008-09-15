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

import com.google.gwt.gen2.event.shared.AbstractEvent;
import com.google.gwt.gen2.event.shared.EventHandler;
import com.google.gwt.user.client.Event;

/**
 * {@link DomEvent} is a subclass of AbstractEvent that provides events that map
 * to DOM Level 2 Events. It provides an additional method to access the
 * underlying native browser event object as well as a subclass of
 * AbstractEvent.Key that understands GWT event bits used by sinkEvents().
 * 
 * @param <T> the type of handler used by the event
 */
public abstract class DomEvent<T extends EventHandler> extends AbstractEvent<T> {

  /**
   * Key class used by BrowserEvent subclasses. Includes extra method to return
   * event bits for sinkEvents().
   */
  public static class Key<T extends EventHandler> extends AbstractEvent.Key<T> {

    private int eventBits;

    // old style, multi-bits
    public Key(int eventBits) {
      this.eventBits = eventBits;
    }

    /**
     * Construct the given key using the type safe {@link NativeEventType} enum.
     */
    public Key(NativeEventType event) {
      this.eventBits = event.getEventBits();
    }

    public int getEventBitsToSink() {
      return eventBits;
    }
  }

  private Event browserEvent;

  protected DomEvent(Event browserEvent) {
    this.browserEvent = browserEvent;
  }

  public Event getNativeEvent() {
    return browserEvent;
  }

  public void preventDefault() {
    browserEvent.preventDefault();
  }

  public void stopPropagation() {
    browserEvent.cancelBubble(true);
  }

}
