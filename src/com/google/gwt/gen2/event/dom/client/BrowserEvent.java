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
 * BrowserEvent is a subclass of AbstractEvent that provides events that map to
 * DOM Level 2 Events. It provides an additional method to access the underlying
 * native browser event object as well as a subclass of AbstractEvent.Key that
 * understands GWT event bits used by sinkEvents().
 * 
 * TODO(ecc) Talk to Ray about intance key in these browser events.
 * 
 * @param <T> the type of handler used by the event
 */
public abstract class BrowserEvent<T extends EventHandler> extends
    AbstractEvent<T> {

  /**
   * Key class used by BrowserEvent subclasses. Includes extra method to return
   * event bits for sinkEvents().
   */
  public static class Key<T extends EventHandler> extends AbstractEvent.Key<T> {

    private int eventBits;

    // type safe constructor, unfortunately only a single event supported
    // as EnumSet is not efficient on GWT
    public Key(BrowserEvents event) {
      this.eventBits = event.getEventBits();
    }

    // old style, multi-bits
    public Key(int eventBits) {
      this.eventBits = eventBits;
    }

    public int getEventBitsToSink() {
      return eventBits;
    }
  }

  // TODO(ecc) This is convenient, but must be eventually be removed as we do not
  // want to pay for the overhead of a separate key field per event.
  private Key<T> key;

  private Event browserEvent;

  protected BrowserEvent(Key<T> key, Event browserEvent) {
    this.key = key;
    this.browserEvent = browserEvent;
  }

  public Event getBrowserEvent() {
    return browserEvent;
  }

  public void preventDefault() {
    browserEvent.preventDefault();
  }

  public void stopPropagation() {
    browserEvent.cancelBubble(true);
  }

  @Override
  protected BrowserEvent.Key<T> getKey() {
    return key;
  }
}
